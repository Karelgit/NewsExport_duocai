package cn.com.cloudpioneer.service;

import cn.com.cloudpioneer.dao.CrawlerDataEntityDao;
import cn.com.cloudpioneer.entity.TaskEntity;
import cn.com.cloudpioneer.util.HandleXml;
import cn.com.cloudpioneer.util.PostUtil;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/9.
 */
public class NewsPusher {

    /**
     * 多彩贵州北方网系统推送接口
     * @throws Exception
     */
    String taskId="acbrocdldrtfkauj9ertt29d67";


    public void pushNews()  throws Exception{

        CrawlerDataEntityDao dao=new CrawlerDataEntityDao();
        CrawlerDataEntityService dataEntityService=new CrawlerDataEntityService();
        TaskEntity entity=dao.findTaskEntity(taskId);
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


        String loginResponse = loginParam();
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

    private String  loginParam()  {
        String loginUrl = "http://work.gog.cn:9001/pub/cms_api_60/Api!login.do";
        Map<String,String> loginParams = new HashMap<>();

        String userName = "testgengyun";
        String password = "Tes@t123%A2jhc23";
        loginParams.put("userName",userName);
        loginParams.put("password",password);

        String response = PostUtil.postMethod(loginUrl,loginParams);
        return response;
    }

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
