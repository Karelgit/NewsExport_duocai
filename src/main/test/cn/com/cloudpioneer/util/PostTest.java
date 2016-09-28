/*
package cn.com.cloudpioneer.util;

import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * <类详细说明:Post方法测试类>
 *
 * @Author： Huanghai
 * @Version: 2016-09-21
 **//*

public class PostTest {
    @Test
    public void testPostMethod()  {
        String url = "http://work.gog.cn:9001/pub/cms_api_60/Api!impNews.do";
        Map<String,String> params = new HashMap<>();
        String api_token = "0000010000008100000147450954410705948a3acb70de0b1ab8fa794f1f5f83";
        String check_sum = "2e328d7561d60bf581ec88807878c574";
        String newsXML = HandleXml.readXml("newsTest.xml");
        params.put("news",newsXML);
        params.put("api_token",api_token);
        params.put("check_sum",check_sum);
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
        String newsXML = HandleXml.readXml("newsTest.xml");
        String str = newsXML+"5b43e6a24156660d2f1f39545306d383";
        System.out.println(MD5(str));
    }


    public String MD5_64bit(String readyEncryptStr) throws Exception{
        MessageDigest md = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(md.digest(readyEncryptStr.getBytes("UTF-8")));
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
*/
