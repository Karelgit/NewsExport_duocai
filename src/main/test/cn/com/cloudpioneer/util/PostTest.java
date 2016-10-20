package cn.com.cloudpioneer.util;

import cn.com.cloudpioneer.service.CrawlerDataEntityService;
import com.alibaba.fastjson.JSONObject;
import org.jdom2.output.support.SAXOutputProcessor;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <类详细说明:Post方法测试类>
 *
 * @Author： Huanghai
 * @Version: 2016-09-21
 **/
public class PostTest {
    /**
     * 多彩贵州北方网系统推送接口
     * @throws Exception
     */
    String taskId="acbrocdldrtfkauj9ertt29d67";

    @Test
    public void pushNews()  throws Exception{
        CrawlerDataEntityService dataEntityService=new CrawlerDataEntityService();
        List<String> datas=dataEntityService.crawlerDataEntityXml(30,taskId);
        String loginResponse = "";
        loginResponse = loginParam();
        System.out.println(datas.size());
//        for(int i=0; i<datas.size(); i++)   {
            /*HandleXml handleXml= new HandleXml();
            handleXml.writeXml(datas.get(0),"/newsTest.xml");
            String s=   handleXml.readXml("/newsTest.xml");
            System.out.println(s);*/
            testPostMethod(/*s,*/loginResponse);
//        }
    }

        @Test
    public void testPostMethod(/*String newsXML,*/String loginResponse) throws Exception {
        String url = "http://work.gog.cn:9001/pub/cms_api_60/Api!impNews.do";
        Map<String,String> params = new HashMap<>();
//        String loginResponse = loginParam();

        JSONObject jsonObject = (JSONObject) JSONObject.parse(loginResponse);
        String api_token = (String) jsonObject.getJSONObject("result").get("token");
//        String api_token = "000001000001050000014748503594953f9fced5c736cd82750c55274fc4c06d";
        String seed = (String) jsonObject.getJSONObject("result").get("seed");

        String check_sum = getMD5_32bit(seed);
        String newsXML = new HandleXml().readXml("/newsTest.xml");
        System.out.println("newXML:" +"\n" + newsXML);
        params.put("news",newsXML);
        params.put("api_token",api_token);
        params.put("check_sum",check_sum);
        String response = PostUtil.postMethod(url,params);
        System.out.println("push response: " + response);

        String projectPath = System.getProperty("user.dir");
        String xmlPath = projectPath+"/src/main/resources/response.log";
        new HandleXml().writeResponseToLocal(response+"\n",xmlPath);
    }

    public String loginParam()  {
        String loginUrl = "http://work.gog.cn:9001/pub/cms_api_60/Api!login.do";
        Map<String,String> loginParams = new HashMap<>();

        /*String userName = "testgengyun";
        String password = "Tes@t123%A2jhc23";*/
        String userName = "test_hl";
        String password = "123123123";
        loginParams.put("userName",userName);
        loginParams.put("password",password);

        String response = PostUtil.postMethod(loginUrl,loginParams);
        return response;
    }

    public String getMD5_32bit(String seed)  throws Exception{
        String newsXML = new HandleXml().readXml("/newsTest.xml");
        String str = newsXML+seed;
        System.out.println("newsXML+seed: " + str);
        return MD5(str);
    }

    public String MD5(String md5) {
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
