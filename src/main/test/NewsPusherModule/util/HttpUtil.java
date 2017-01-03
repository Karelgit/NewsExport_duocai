/*
package cn.com.cloudpioneer.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

*/
/**
 * Created by tijun on 2016/7/29.
 * @author tijun
 *//*

public class HttpUtil
{
    */
/**
     * 得到Url返回的所有header信息
     * @param requestBase 参数为HttpGet/HttpPost
     * @return
     * @throws IOException
     *//*

        public static Header[] getHeadersByHttpRequest(HttpRequestBase requestBase) throws IOException
        {

            CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(null).build();

            HttpResponse response=client.execute(requestBase);
            return response.getAllHeaders();
        }

        public static String getCookieByHeaders(Header[] headers){
            for (Header header:headers){
                if (header.getName().equals("Set-Cookie")){
                    String value=header.getValue();
                    if (value.contains("JSESSIONID")){
                        return value;
                    }

                }
            }
            return null;
        }
    public static String getAllCookie(Header[]headers){
        String cookie="";
        for (Header header:headers){
            if (header.getName().equals("Set-Cookie")){
                String value=header.getValue();
                cookie+=value;
                */
/*if (value.contains("JSESSIONID")){
                    return value;
                }*//*


            }
        }
        return cookie;
    }

    */
/**
     * 给post请求拼装请求参数
     * @param url
     * @param params
     * @return HttpPost
     *//*

    public static HttpPost postForm(String url, Map<String, String> params){

        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }

        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return httpost;

    }

    */
/**
     *
     * @param params
     * @param url
     * @return HttpPost
     *//*

    public  static HttpPost postByJSON(String url, Map<String,String> params) throws UnsupportedEncodingException {
        HttpPost post=new HttpPost(url);
        String param=JSONObject.toJSONString(params);
        StringEntity entity = new StringEntity(param, Charset.forName("UTF-8"));//解决中文乱码问题
        entity.setContentType("application/json");
        post.setEntity(entity);
        post.addHeader("Content-type", "application/json; charset=utf-8");
       post.setHeader("Accept", "application/json");
        return post;
    }
    public static CookieStore getCookieStore(HttpRequestBase httpMethod) throws IOException
    {

        CookieStore cookies=new BasicCookieStore();
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookies).build();

        HttpResponse response=client.execute(httpMethod);
        return cookies;


    }

    */
/**
     * httpGet
     * @param url
     * @return
     *//*

    public  static HttpGet getMethd(String url){
        HttpGet getMethod=new HttpGet(url);
        return getMethod;
    }

    public static String searchByRequests(List<? extends HttpRequestBase> httpRequests) throws IOException
    {
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(null).build();
        String responseString=null;
        try {

            for(HttpRequestBase base:httpRequests){

                HttpResponse respone= client.execute(base);
                HttpEntity entity = respone.getEntity();

                if (entity != null) {

                    responseString = EntityUtils.toString(entity).replace("\r\n", "");

                }
            }

        } catch (IOException e) {

            e.printStackTrace();
        }finally
        {
            if(client!=null){
                client.close();
            }
        }
        return responseString;
    }
    //
    public static String searchByRequest(HttpRequestBase requestBase) throws IOException
    {
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(null).build();
        String responseString=null;
        try {



            HttpResponse respone= client.execute(requestBase);
            if(respone==null){
                return null;
            }
            HttpEntity entity = respone.getEntity();

            if (entity != null) {

                responseString = EntityUtils.toString(entity).replace("\r\n", "");

            }


        } catch (IOException e) {

            e.printStackTrace();
        }finally
        {
            if(client!=null){
                client.close();
            }
        }
        return responseString;
    }



}
*/
