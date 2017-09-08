package NewsPusherModule.service;

import NewsPusherModule.entity.Article;
import NewsPusherModule.entity.DuocaiInfo;
import NewsPusherModule.util.HandleXml;
import NewsPusherModule.util.NewsPushUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by TijunWang on 2016/11/1.
 * @author TijunWang
 */
@Service
public class NewsPusherService {
    private static final Logger LOG= LoggerFactory.getLogger(NewsPusherService.class);
    public String  login(String loginUrl,String userName,String password) throws Exception {
        Map<String,String> loginParams = new HashMap<>();
        loginParams.put("userName",userName);
        loginParams.put("password",password);
        String response = NewsPushUtil.excutePost(loginUrl,loginParams);
        LOG.info("调用登录多彩接口 response："+response);
        return response;
    }

    public String postNews(String postUrl,String newsXML,/*String loginResponse*/DuocaiInfo duocaiInfo ) throws Exception {

        Map<String,String> params = new HashMap<>();

//        JSONObject jsonObject = (JSONObject) JSONObject.parse(loginResponse);
//        String api_token = (String) jsonObject.getJSONObject("result").get("token");
//        String seed = (String) jsonObject.getJSONObject("result").get("seed");
        String api_token =duocaiInfo.getApi_token();
        String seed = duocaiInfo.getSeed();
        String check_sum = NewsPushUtil.generateMD5(newsXML+seed);

        params.put("news",newsXML);
        params.put("api_token",api_token);
        params.put("check_sum",check_sum);
        String response = NewsPushUtil.excutePost(postUrl,params);
        LOG.info("调用推送多彩接口 response："+response);
        return response;
    }
    public String exportNewsToDuocai(String xml,/*String param*/DuocaiInfo duocaiInfo) throws Exception {
        Properties duocai = NewsPushUtil.readResourceAsProperties("/duocai.properties");
        String postUrl = duocai.getProperty("newsExportUrl");

        return this.postNews(postUrl,xml,duocaiInfo);
    }

    public String loginDuocai() throws Exception {
        Properties duocai = NewsPushUtil.readResourceAsProperties("/duocai.properties");
        String loginUrl = duocai.getProperty("loginUrl");
        String userName = duocai.getProperty("userName");
        String password = duocai.getProperty("password");
       return this.login(loginUrl,userName,password);
    }
    public String getXmlTemplate(DuocaiInfo duocaiInfo){

        String xml = new HandleXml().readXml("/news.xml");
        xml = xml.replace("$initEditor",duocaiInfo.getInitEditor());

        return xml;
    }

    /**
     * 将article中的属性填入标准的xml模板中
     * @param article
     * @param xml
     * @return
     */
    public String articleToXml(Article article, String xml){
        xml = new String(xml);
        Assert.notNull(xml,"xml can't be null");
        Assert.notNull(article,"article cannot be null");
        if (article.getTitle()==null||article.getContent()==null||article.getTitle().equals("")||article.getContent().equals("")){
            try {
                throw new IllegalArgumentException("title and content can not be null or empty");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        xml=xml.replace("$content",article.getContent() == null ? "" : article.getContent()  );
        xml=xml.replace("$title",article.getTitle() == null ? "" : article.getTitle());
        xml = xml.replace("$keywords4",article.getAuthor() == null ? "" : article.getAuthor());
        xml = xml.replace("$sourceName",article.getSourceName()== null ? "" : article.getSourceName());
        xml = xml.replace("$templateId",article.getTemplateId());
        xml = xml.replace("$channelId",article.getChannelId());
        return xml;
    }

}
