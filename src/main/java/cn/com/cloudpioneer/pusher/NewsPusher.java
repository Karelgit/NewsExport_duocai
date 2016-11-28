package cn.com.cloudpioneer.pusher;

import cn.com.cloudpioneer.dao.CrawlerDataEntityDao;
import cn.com.cloudpioneer.entity.TaskPositionEntity;
import cn.com.cloudpioneer.service.CrawlerDataEntityService;
import cn.com.cloudpioneer.util.HandleXml;
import cn.com.cloudpioneer.util.PostUtil;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Tijun on 2016/10/9.
 * @author TijunWang
 */
public class NewsPusher {

    /**
     * 多彩贵州北方网系统推送接口
     * @throws Exception
     */
    String taskId="acbrocdldrtfkauj9ertt29d67";

    private String loginUrl="";

    public void pushNews()  throws Exception{

        CrawlerDataEntityDao dao=new CrawlerDataEntityDao();
        CrawlerDataEntityService dataEntityService=new CrawlerDataEntityService();
        TaskPositionEntity entity=dao.findTaskEntity(taskId);
       int number=dao.count()-entity.getPosition();
        List<String> datas=null;
        System.out.println("number---"+number);
        if (number>0){
            datas=dataEntityService.crawlerDataEntityXml(number,taskId);
        }else {
            System.out.println("所有数据推送完毕！");
            System.out.println(new SimpleDateFormat().format(new Date()));
            return;
        }


        String loginResponse = login();
        System.out.println(datas.size());
        for(int i=0; i<datas.size(); i++)   {
            HandleXml handleXml= new HandleXml();
            handleXml.writeXml(datas.get(i),"/newsTest.xml");
            String s=   handleXml.readXml("/newsTest.xml");
            testPostMethod(s,loginResponse);
        }
    }



    //    @Test
    private void testPostMethod(String newsXML,String loginResponse ) throws Exception {
        String url = "http://work.gog.cn:9001/pub/cms_api_60/Api!impNews.do";
        Map<String,String> params = new HashMap<>();
        //  String loginResponse = loginParam();

        JSONObject jsonObject = (JSONObject) JSONObject.parse(loginResponse);
        String api_token = (String) jsonObject.getJSONObject("result").get("token");
        String seed = (String) jsonObject.getJSONObject("result").get("seed");

        String check_sum = getMD5_32bit(seed,newsXML);
        newsXML = new HandleXml().readXml("/newsTest.xml");
        System.out.println("newXML:" +"\n" + newsXML);
        params.put("news",newsXML);
        params.put("api_token",api_token);
        params.put("check_sum",check_sum);
        String response = PostUtil.postMethod(url,params);
        System.out.println("news"+response);
        String projectPath = System.getProperty("user.dir");
        String xmlPath = projectPath+"/src/main/resources/response.log";
        new HandleXml().writeResponseToLocal(response+"\n",xmlPath);
    }

    private String  login()  {
        String loginUrl = "http://work.gog.cn:9001/pub/cms_api_60/Api!login.do";
        Map<String,String> loginParams = new HashMap<>();
        String userName = "testgengyun";
        String password = "Tes@t123%A2jhc23";
        loginParams.put("userName",userName);
        loginParams.put("password",password);
        String response = PostUtil.postMethod(loginUrl,loginParams);
        return response;
    }

    /**
     *
     * @param seed
     * @param xml
     * @return
     * @throws Exception
     */
    private String getMD5_32bit(String seed,String xml)  throws Exception{
        String str = xml+seed;
        return MD5(str);
    }


    private String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}
