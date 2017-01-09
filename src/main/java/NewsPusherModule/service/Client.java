package NewsPusherModule.service;

import NewsPusherModule.controller.ExportDuocaiController;
import NewsPusherModule.entity.HandShaker;
import NewsPusherModule.entity.KeepAlive;
import NewsPusherModule.entity.PushInfo;
import NewsPusherModule.util.FileWriterUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息推送客户端逻辑，可以随时向服务端发送消息。
 * <p>
 * 创建时间：2017-01-03 13:54:12
 *
 * @author huanghai
 * @since 1.0
 */
public class Client {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("conf");

    /**
     * 处理服务端发回的对象，可实现该接口。
     */
    private static final Logger LOG= LoggerFactory.getLogger(Client.class);
    public static interface ObjectAction {
        void doAction(Object obj, Client client);
    }

    public static final class DefaultObjectAction implements ObjectAction {
        public void doAction(Object obj, Client client) {
            try {
                FileWriterUtil.writeLog("处理：\t" + obj.toString()+"\n");
                LOG.info("处理：\t" + obj.toString()+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static final class HandShakerObjectAction implements ObjectAction {
        private Socket socket;
        private List<HandShaker> handShakerList = new ArrayList<HandShaker>();

        public void doAction(Object obj, Client client) {
            HandShaker handShaker = ((HandShaker) obj);
            handShaker.setClientSocket(socket);
            handShakerList.add(handShaker);
            System.out.println("客户端获得handShaker的列表" + JSON.toJSONString(handShakerList));
        }

    }

    private String serverIp;
    private int port;
    private Socket socket;
    private boolean running = false;
    private long lastSendTime;
    private ConcurrentHashMap<Class, ObjectAction> actionMapping = new ConcurrentHashMap<Class, ObjectAction>();

    public Client(String serverIp, int port) {
        this.serverIp = serverIp;
        this.port = port;
    }

    public void start() throws UnknownHostException, IOException {
        if (running) return;
        while (socket == null)  {
            try {
                socket = new Socket(serverIp, port);
            } catch (IOException e) {
                try {
                    Thread.sleep(1000*10);
                    LOG.info("客户端初始连接失败，每隔10秒钟再次连接！");
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                e.printStackTrace();
            }
        }

        LOG.info("本地端口：" + socket.getLocalPort());
        lastSendTime = System.currentTimeMillis();
        running = true;

        new Thread(new KeepAliveWatchDog()).start();
        new Thread(new ReceiveWatchDog()).start();
        new Thread(new SendHandShaker()).start();

    }

    public void stop() {
        if (running) running = false;
    }

    /**
     * 添加接收对象的处理对象。
     *
     * @param cls 待处理的对象，其所属的类。
     * @param action 处理过程对象。
     */
    public void addActionMap(Class<Object> cls, ObjectAction action) {
        actionMapping.put(cls, action);
    }

    public void sendObject(Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(obj);
        oos.flush();
    }

    class SendHandShaker implements Runnable {
        public void run() {
            String companyId = resourceBundle.getString("companyId");
            //客户端发送HanndShaker信息，包括用户名，密码
            HandShaker handShaker = new HandShaker();
            handShaker.setCompanyId(companyId);
            LOG.info("客户端发送handShaker信息是：\t" + JSON.toJSONString(handShaker));
            try {
                sendObject(handShaker);
            } catch (IOException e) {
                LOG.info("客户端发送handShaker信息时发生错误！");
                e.printStackTrace();
            }
        }
    }

    class KeepAliveWatchDog implements Runnable {
        long checkDelay = 1000;
        long keepAliveDelay = 1000*15;

        public void run() {
            while (running) {
                if (System.currentTimeMillis() - lastSendTime > keepAliveDelay) {
                    try {
                        Client.this.sendObject(new KeepAlive());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Client.this.stop();
                        //启动重连服务端
                        new Thread(new ReconnectSocket()).start();

                    }
                    lastSendTime = System.currentTimeMillis();
                } else {
                    try {
                        Thread.sleep(checkDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Client.this.stop();
                    }
                }
            }
        }
    }

    class ReceiveWatchDog implements Runnable {
        public void run() {
            while (running) {
                try {
                    InputStream in = socket.getInputStream();
                    if (in.available() > 0) {
                        ObjectInputStream ois = new ObjectInputStream(in);
                        Object obj = ois.readObject();
                        FileWriterUtil.writeLog("接收：\t" + obj);
                        LOG.info("接收：\t" + obj);
//                        System.out.println("接收：\t" + obj);
                        ObjectAction oa = actionMapping.get(obj.getClass());
                        if (obj instanceof PushInfo) {
                            Long timeBefore = System.currentTimeMillis();
                            LOG.info("收到的推送新闻的信息：" + JSON.toJSONString(obj)+"\n");
                            //推送到多彩贵州
                            Object resultObj =new ExportDuocaiController().exportArticles(((PushInfo) obj).getArticlesJSON(),((PushInfo) obj).getCustomerInfoJSON());
                            LOG.info("推送结果："+JSON.toJSONString(resultObj));
                            Long timeAfter = System.currentTimeMillis();
                            LOG.info("此次推送耗时(time elapsed): " + (timeAfter - timeBefore) / 1000 + "seconds");

                        }
                        oa = oa == null ? new DefaultObjectAction() : oa;
                        oa.doAction(obj, Client.this);
                    } else {
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Client.this.stop();
                }
            }
        }
    }

    class ReconnectSocket implements Runnable {
        public void run() {
            String serverIp = resourceBundle.getString("SERVER_IP");
            int port = Integer.parseInt(resourceBundle.getString("SERVER_PORT"));
            Client client = new Client(serverIp, port);
            client.addActionMap(Object.class, new HandShakerObjectAction());
            try {
                client.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}