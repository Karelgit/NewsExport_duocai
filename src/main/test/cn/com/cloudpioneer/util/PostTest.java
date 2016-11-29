package cn.com.cloudpioneer.util;

import cn.com.cloudpioneer.ApplicationNewsExport;
import cn.com.cloudpioneer.push.NewsPusher;
import cn.com.cloudpioneer.service.CrawlerDataEntityService;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <类详细说明:Post方法测试类>
 *
 * @Author： Huanghai
 * @Version: 2016-09-21
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationNewsExport.class)
@WebAppConfiguration
public class PostTest {
    /**
     * 多彩贵州北方网系统推送接口
     * @throws Exception
     */
    String taskId="acbrocdldrtfkauj9ertt29d67";
    @Autowired
    private CrawlerDataEntityService service;

    @Test
    public void pushNews()  throws Exception{

       /* CrawlerDataEntityService dataEntityService=new CrawlerDataEntityService();
        List<String> datas=dataEntityService.crawlerDataEntityXml(40,taskId);*/
        String loginResponse = loginParam();
        HandleXml handleXml= new HandleXml();
        testPostMethod(XmlFormatter.format(handleXml.readXml("/newsTest.xml")),loginResponse);

    }

    //    @Test
    public void testPostMethod(String newsXML,String loginResponse ) throws Exception {
        String url = "http://work.gog.cn:9001/pub/cms_api_60/Api!impNews.do";
        Map<String,String> params = new HashMap<>();
      //  String loginResponse = loginParam();

        JSONObject jsonObject = (JSONObject) JSONObject.parse(loginResponse);
        String api_token = (String) jsonObject.getJSONObject("result").get("token");
        String seed = (String) jsonObject.getJSONObject("result").get("seed");

//        String check_sum = getMD5_32bit("3cd2ab9a142ecac9e8f442bc7b1993fc",newsXML);
        String check_sum = getMD5_32bit(seed,newsXML);
       // newsXML = new HandleXml().readXml("/newsTest.xml");
        System.out.println("newXML:" +"\n" + newsXML);
        params.put("news",newsXML);
        params.put("api_token",api_token);
//        params.put("api_token","00000100000122000001479807767383b7810c8177fc1d7d76b671858d6b3fa2");

        params.put("check_sum",check_sum);
        String response = PostUtil.postMethod(url,params);
        System.out.println("news"+response);
        String projectPath = System.getProperty("user.dir");
        String xmlPath = projectPath+"/src/main/resources/response.log";
        new HandleXml().writeResponseToLocal(response+"\n",xmlPath);
    }

    @Test
    public void test()  {
        String str = loginParam();
        System.out.println(str);
    }
    public String  loginParam()  {
        String loginUrl = "http://work.gog.cn:9001/pub/cms_api_60/Api!login.do";
        Map<String,String> loginParams = new HashMap<>();

        /*String userName = "testgengyun";
        String password = "12345678;";*/

        /*String userName = "test_hl";
        String password = "123123123;"*/

        /*String userName = "zhangchao001";
        String password = "1234567;";*/

        String userName = "libei001";
        String password = "1234567;";

        loginParams.put("userName",userName);
        loginParams.put("password",password);

        String response = PostUtil.postMethod(loginUrl,loginParams);
        return response;
    }

    public String getMD5_32bit(String seed,String xml)  throws Exception{
       // String newsXML = new HandleXml().readXml("/newsTest.xml");
        String str = xml+seed;
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
    @Test
    public void   loginParam1()  {
        String loginUrl = "http://work.gog.cn:9443/pub/auth/LoginAction!loginBegin.do";
        Map<String,String> loginParams = new HashMap<>();

        String userName = "testgengyun";
        String password = "Tes@t123%A2jhc23";
        loginParams.put("screenHight","1080");
        loginParams.put("y","17");
        loginParams.put("screenWidth","1920");
        loginParams.put("x","68");
        loginParams.put("vo.token","b8ab66");
        loginParams.put("vo.userName",userName);
        loginParams.put("vo.password",password);


        String response = PostUtil.postMethod(loginUrl,loginParams);
        System.out.println(response);
    }

    @Test
    public void teste() throws Exception {
        cn.com.cloudpioneer.service.NewsPusher pusher=new cn.com.cloudpioneer.service.NewsPusher();
        pusher.pushNews();
    }
    @Test
    public void testNewsExport() throws Exception {
        List<String> xmls = service.crawlerDataEntityXml(250,"019c531802d4200e52586dc01677cd64");
        NewsPusher pusher = new NewsPusher();

        //登陆获得返回数据
        String param = pusher.loginDuocai();
        for (String xml:xmls){
            //String xml = NewsPushUtil.readResourceAsXml("/newsTest.xml");
           //推送文章
            pusher.exportNewsToDuocai(xml,param);
        }

    }

}
