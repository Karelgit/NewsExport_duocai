package NewsPusherModule.controller;

import NewsPusherModule.entity.Article;
import NewsPusherModule.entity.DuocaiInfo;
import NewsPusherModule.service.NewsPusherService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2016/12/23.
 */
@RestController
@RequestMapping("duocai")
@Scope("prototype")
public class ExportDuocaiController {
    private Logger logger = LoggerFactory.getLogger(ExportDuocaiController.class);
    private String loginUrl = PropertyResourceBundle.getBundle("duocai").getString("loginUrl");
    private NewsPusherService newsPusherService =new NewsPusherService();
    /**
     * @apiParam {String} content
     *
     *
        "articles": [
        {
            "author": "杨昌鼎",
            "content": "多彩贵州网讯(本网记者 杨昌鼎)12月22日，贵州省农业委员会与中合三农集团有限公司签订框架协议，将在贵州省建设1—3个大型肉牛养殖、深加工综合产业园区，300个肉牛养殖单元(合作社)，共同携手推动贵州畜牧产业发展。",
            "sourceName": "多彩贵州网",
            "title": "贵州将打造肉牛养殖深加工综合产业园 3年引进10万头青年母牛"

        },
        {
            "author": "杨昌",
            "content": "共同携手推动贵州畜牧产业发展。",
            "sourceName": "多彩贵州网",
            "title": "贵州将打造肉牛养殖深加工综合产业园 "
        }
        ]

     * @return
     */
    @PostMapping("/export/articles")
    public Object exportArticles(String articles,String duocaiInfo){
        JSONArray array = JSON.parseArray(articles);
        Map<String,Object> map = new HashMap<>();
        DuocaiInfo duocai = JSON.parseObject(duocaiInfo,DuocaiInfo.class);
        String xml = null;
        String token = null;
        try {
            //token = this.getToken(duocai);
            xml = this.getXml(duocai);
            System.out.println("duocai的信息："+duocai);
        } catch (Exception e) {
            System.out.println("异常"+e);
            map.put("code",500);
            map.put("info",e.getMessage());
        }
        final String xmlTemplate = xml;
        for (int i=0;i<array.size();i++){
            Article article = JSON.parseObject(array.getString(i),Article.class);
            xml = newsPusherService.articleToXml(article,xmlTemplate);
            logger.info("待推送的xml:"+xml);
            try {
                String result = newsPusherService.exportNewsToDuocai(xml,duocai);
            } catch (Exception e) {
                map.put("code",500);
                map.put("info",e.getMessage());
                return map;
            }
        }
        map.put("code",200);
        return map;
    }


    /**
     *
     *  推送单篇文章
     * @param article
     * @return
     */
    @PostMapping("/export/article")
    public Object exportNewSingle(String article,String duocaiInfo){
        Map<String,Object> map = new HashMap<>();
        DuocaiInfo duocai = JSON.parseObject(duocaiInfo,DuocaiInfo.class);
        String xml = null;
//        String token = null;
        try {
//            token = this.getToken(duocai);
            xml = this.getXml(duocai);
        } catch (Exception e) {
            map.put("code",500);
            map.put("info",e.getMessage());
        }


//        if (xml==null||token==null){
//            map.put("code",600);
//            map.put("info","duocai的用户登陆过期");
//            return map;
//        }
        xml = newsPusherService.articleToXml(JSON.parseObject(article,Article.class),xml);
        try {
            newsPusherService.exportNewsToDuocai(xml,duocai);
        } catch (Exception e) {
           map.put("code",500);
            map.put("info",e.getCause().getMessage());
            return map;
        }
        return map;

    }


    /**
     * localhost:8080/duocai/login
     * 需要测试responseStr
     * @param
    *"duocaiInfo": {
        "channelId": "3000000000000000",
        "initEditor": "100000125",
        "password": "1234567;",
        "userName": "zhangcao111",
        "templateId":"100000330"
    }
     * @return
     */
    @PostMapping("/login")
    public Object login(String duocaiInfo, HttpSession session){
        DuocaiInfo duocai = JSON.parseObject(duocaiInfo,DuocaiInfo.class);
        Map<String,Object> map1 = new HashMap<>();
        String responseStr = null;
        try {
            responseStr = newsPusherService.login(loginUrl,duocai.getUserName(),duocai.getPassword());
            JSONObject obj = JSON.parseObject(responseStr);
           int code =  obj.getIntValue("code");
            if (code==-20000){
                map1.put("code",code);
                map1.put("info","登陆失败");
                return map1;
            }
            logger.info("token:"+responseStr);
        } catch (Exception e) {
            map1.put("code",500);
            map1.put("info",e.getMessage());
            logger.info("error",e.getCause().getMessage());
            return map1;
        }

        String xml = newsPusherService.getXmlTemplate(duocai);
        session.setAttribute("xml",xml);
        session.setAttribute("token",responseStr);
        map1.put("code",200);
        return map1;
    }


    private String getToken(DuocaiInfo duocai) throws Exception {

        return newsPusherService.login(loginUrl,duocai.getUserName(),duocai.getPassword());
    }
    private String getXml(DuocaiInfo duocai ){
        return  newsPusherService.getXmlTemplate(duocai);
    }

}
