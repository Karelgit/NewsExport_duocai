package cn.com.cloudpioneer.service;

import cn.com.cloudpioneer.util.NewsPushUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by TijunWang on 2016/11/1.
 * @author TijunWang
 */
@Service
public class NewsPusherService {

    public String  login(String loginUrl,String userName,String password)  {
        Map<String,String> loginParams = new HashMap<>();
        loginParams.put("userName",userName);
        loginParams.put("password",password);
        String response = NewsPushUtil.excutePost(loginUrl,loginParams);
        return response;
    }

    public String postNews(String postUrl,String newsXML,String loginResponse ) throws Exception {

        Map<String,String> params = new HashMap<>();

        JSONObject jsonObject = (JSONObject) JSONObject.parse(loginResponse);
        String api_token = (String) jsonObject.getJSONObject("result").get("token");
        String seed = (String) jsonObject.getJSONObject("result").get("seed");
        String check_sum = NewsPushUtil.generateMD5(newsXML+seed);

        params.put("news",newsXML);
        params.put("api_token",api_token);
        params.put("check_sum",check_sum);
        String respones = NewsPushUtil.excutePost(postUrl,params);
        return respones;
    }
    public String exportNewsToDuocai(String xml,String param) throws Exception {
        Properties duocai = NewsPushUtil.readResourceAsProperties("/duocai.properties");
        String postUrl = duocai.getProperty("newsExportUrl");
        return this.postNews(postUrl,xml,param);
    }

    public String loginDuocai(){
        Properties duocai = NewsPushUtil.readResourceAsProperties("/duocai.properties");
        String loginUrl = duocai.getProperty("loginUrl");
        String userName = duocai.getProperty("userName");
        String password = duocai.getProperty("password");
       return this.login(loginUrl,userName,password);
    }

}
