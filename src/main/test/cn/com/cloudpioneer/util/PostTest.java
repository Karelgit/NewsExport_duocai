package cn.com.cloudpioneer.util;

import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * <类详细说明:Post方法测试类>
 *
 * @Author： Huanghai
 * @Version: 2016-09-21
 **/
public class PostTest {
    @Test
    public void testPostMethod()  {
        String url = "http://work.gog.cn:9001/pub/cms_api_60/Api!impNews.do";
        Map<String,String> params = new HashMap<>();
//        String api_token = "000001000000810000014744482397210c0cb83bb61404dab95dde71aa8620c3";
//        String check_sum = "0ck+qRh85lLCjAnxrDC6Jg==";
        String newsXML = HandleXml.readXml("news.xml");
        params.put("news",newsXML);
//        params.put("api_token",api_token);
//        params.put("check_sum",check_sum);
        String response = PostUtil.postMethod(url,params);
        System.out.println(response);
    }

    @Test
    public void testLogin()  {
        String loginUrl = "http://work.gog.cn:9001/pub/cms_api_60/Api!login.do";
        Map<String,String> loginParams = new HashMap<>();

        String userName = "testgengyun";
        String password = "Tes@t123%A2jhc23";
        loginParams.put("userName",userName);
        loginParams.put("password",password);

        String response = PostUtil.postMethod(loginUrl,loginParams);
        System.out.println(response);
    }

    @Test
    public void getMD5_64bit()  throws Exception{
        String str = "news7b21ff0e0e06d47d81f8e6d12af845c7";
        System.out.println(MD5_64bit(str));
    }
    public String MD5_64bit(String readyEncryptStr) throws Exception{
        MessageDigest md = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(md.digest(readyEncryptStr.getBytes("UTF-8")));
    }


}
