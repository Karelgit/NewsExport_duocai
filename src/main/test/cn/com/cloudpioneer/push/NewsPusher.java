package cn.com.cloudpioneer.push;

import cn.com.cloudpioneer.util.HandleXml;
import cn.com.cloudpioneer.util.NewsPushUtil;
import cn.com.cloudpioneer.util.PostUtil;
import com.alibaba.fastjson.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by TijunWang on 2016/11/1.
 * @author TijunWang
 */
public class NewsPusher {

    public String  login(String loginUrl,String userName,String password)  {
        Map<String,String> loginParams = new HashMap<>();
        loginParams.put("userName",userName);
        loginParams.put("password",password);
        String response = NewsPushUtil.excutePost(loginUrl,loginParams);
        return response;
    }

    public void postNews(String postUrl,String newsXML,String loginResponse ) throws Exception {

        Map<String,String> params = new HashMap<>();

        JSONObject jsonObject = (JSONObject) JSONObject.parse(loginResponse);
        String api_token = (String) jsonObject.getJSONObject("result").get("token");
        String seed = (String) jsonObject.getJSONObject("result").get("seed");
        String check_sum = NewsPushUtil.generateMD5(newsXML+seed);

        params.put("news",newsXML);
        params.put("api_token",api_token);
        params.put("check_sum",check_sum);
        String respones = NewsPushUtil.excutePost(postUrl,params);
        System.out.println(respones);
    }
    public void exportNewsToDuocai(String xml,String param) throws Exception {
        Properties duocai = NewsPushUtil.readResourceAsProperties("/duocai.properties");
        String postUrl = duocai.getProperty("newsExportUrl");
        this.postNews(postUrl,xml,param);
    }

    public String loginDuocai(){
        Properties duocai = NewsPushUtil.readResourceAsProperties("/duocai.properties");
        String loginUrl = duocai.getProperty("loginUrl");
        String userName = duocai.getProperty("userName");
        String password = duocai.getProperty("password");
       return this.login(loginUrl,userName,password);
    }

}
