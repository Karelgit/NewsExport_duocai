/*
package cn.com.cloudpioneer.http;

import cn.com.cloudpioneer.utils.ResourceReader;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import java.io.IOException;




public class HttpClientTest
{

    //测试连接到多彩
    @Test
    public void testRequest4duocai() throws IOException
    {
       org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
        String url="http://work.gog.cn:9001/pub/cms_api_60/Api!impNews.do";
        String loginUrl="http://work.gog.cn:9001/pub/cms_api_60/Api!login.do";
        PostMethod loginPost=new PostMethod(loginUrl);
        loginPost.addParameter("userName","testgengyun");
        loginPost.addParameter("password", "Tes@t123%A2jhc23");
       int status1= httpClient.executeMethod(loginPost);
       String jsonResult=loginPost.getResponseBodyAsString();
      JSONObject object= JSON.parseObject(jsonResult);
        JSONObject info=object.getJSONObject("result");
        String seed=info.getString("seed");
        String token=info.getString("token");
       // String api_token=

        PostMethod postMethod = new PostMethod(url);
        String response = null;
        String xml= null;
        try
        {
            xml = ResourceReader.readResource("/news.xml");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        String check_sum= new String(DigestUtils.md5("news"+seed));
        postMethod.addParameter("api_token",token);
        postMethod.addParameter("check_sum",check_sum);
        postMethod.addParameter("news",xml);
        httpClient.getParams().setSoTimeout(30*1000);

        try
        {
            int status = httpClient.executeMethod(postMethod);
            System.out.println(status);
        } catch (IOException e)
            {
            e.printStackTrace();
        }
        response = postMethod.getResponseBodyAsString();
            System.out.println("response : "+response);
        }


}
*/
