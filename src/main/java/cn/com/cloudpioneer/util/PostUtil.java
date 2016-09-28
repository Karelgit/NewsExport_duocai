/*
package cn.com.cloudpioneer.util;


import java.io.IOException;
import java.util.Map;

*/
/**
 * @Author：Huanghai
 * @CreateTime：2016-09-20 22:18:38
 * @Description：Post方法工具类
 *//*

public class PostUtil {

    public static String postMethod(String url, Map<String, String> params) {
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
            e.printStackTrace();
        } finally {
            if (postMethod != null)
                postMethod.releaseConnection();
        }
        return response;
		
    }
}*/
