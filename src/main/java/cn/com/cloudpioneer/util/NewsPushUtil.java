package cn.com.cloudpioneer.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

/**
 *
 */
public class NewsPushUtil {


     public static String generateMD5(String md5) {
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

    public static Properties readResourceAsProperties(String name){
       InputStream is= NewsPushUtil.class.getResourceAsStream(name);
        Properties properties=new Properties();
        try {
            properties.load(new InputStreamReader(is,"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }



    public static String excutePost(String url, Map<String, String> params) throws Exception {

        HttpClient httpClient = new HttpClient();

        PostMethod postMethod = new PostMethod(url);

        String response = null;

        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        for (Map.Entry<String, String> entry : params.entrySet()) {

            postMethod.addParameter(entry.getKey(), entry.getValue());

        }
        try {

            int status = httpClient.executeMethod(postMethod);
            response = postMethod.getResponseBodyAsString();
        } catch (IOException e) {
            throw new Exception(e);
        } finally {
            if (postMethod != null)
                postMethod.releaseConnection();
        }
        return response;

    }
    public static String readResourceAsXml(String fileName){
        InputStream is = NewsPushUtil.class.getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer stringBuffer = new StringBuffer();
        String str;
        try {
            while ((str = reader.readLine()) !=null)    {
                stringBuffer.append(str+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                if (reader != null) {
                    reader.close();
                }
                if (is!=null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }
}
