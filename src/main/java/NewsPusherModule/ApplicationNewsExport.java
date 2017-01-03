package NewsPusherModule;

import NewsPusherModule.service.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * <ApplicationNewsExport,启动Springboot提供新闻推送服务>
 * Created by Tijun on 2016/10/10.
 * @author TijunWang
 */
@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class ApplicationNewsExport {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationNewsExport.class);

        //新闻推送socket客户端逻辑
        ResourceBundle resourceBundle = ResourceBundle.getBundle("conf");
        String serverIp = resourceBundle.getString("SERVER_IP");
        int port = Integer.parseInt(resourceBundle.getString("SERVER_PORT"));
        Client client = new Client(serverIp,port,args[0],args[1]);
        client.addActionMap(Object.class,new Client.HandShakerObjectAction());
        try {
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
