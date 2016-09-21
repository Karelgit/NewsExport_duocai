package cn.com.cloudpioneer.http;

import cn.com.cloudpioneer.utils.ResourceReader;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Tijun on 2016/9/21.
 */
public class HttpClientTest
{

    //测试连接到多彩
    @Test
    public void testRequest4duocai() throws IOException
    {
       org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
        String url="http://test.enorth.com.cn:8080/pub/cms_api_60/Api!impNews.do";
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
