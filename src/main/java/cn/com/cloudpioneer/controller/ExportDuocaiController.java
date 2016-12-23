package cn.com.cloudpioneer.controller;

import cn.com.cloudpioneer.entity.Article;
import cn.com.cloudpioneer.entity.DuocaiInfo;
import cn.com.cloudpioneer.service.CrawlerDataEntityService;
import cn.com.cloudpioneer.service.NewsPusherService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by Administrator on 2016/12/23.
 */
@RestController
public class ExportDuocaiController {
  private String loginUrl = PropertyResourceBundle.getBundle("duocai").getString("loginUrl");
    @Autowired
    private NewsPusherService newsPusherService;

    @Autowired
    private CrawlerDataEntityService crawlerDataEntityService;
    //

    /**
     * @apiParam {String} content
     *
     * {
        "duocaiInfo": {
        "channelId": "3000000000000000",
        "initEditor": "100000125",
        "password": "1234567;",
        "userName": "zhangcao111",
        "templateId":"100000330"
        },
        "articles": [
        {
            "author": "杨昌鼎",
            "content": "多彩贵州网讯(本网记者 杨昌鼎)12月22日，贵州省农业委员会与中合三农集团有限公司签订框架协议，将在贵州省建设1—3个大型肉牛养殖、深加工综合产业园区，300个肉牛养殖单元(合作社)，共同携手推动贵州畜牧产业发展。",
            "sourceName": "多彩贵州网",
            "title": "贵州将打造肉牛养殖深加工综合产业园 3年引进10万头青年母牛"
        },
        {
            "author": "杨昌鼎",
            "content": "多彩贵州网讯(本网记者 杨昌鼎)12月22日，共同携手推动贵州畜牧产业发展。",
            "sourceName": "多彩贵州网",
            "title": "贵州将打造肉牛养殖深加工综合产业园 "
        }
        ]
    }
     * @param content
     * @return
     */
    @PostMapping("news/export")
    public Object login(String content){
        JSONObject map = JSON.parseObject(content);
        DuocaiInfo duocaiInfo =JSON.parseObject(map.getString("duocaiInfo"),DuocaiInfo.class);
        List<Article> articles = JSON.parseArray(map.getString("articles"),Article.class);
       //登陆
        String responseStr = newsPusherService.login(loginUrl,duocaiInfo.getUserName(),duocaiInfo.getPassword());
       //格式化
        String xml = newsPusherService.getXmlTemplate(duocaiInfo);
        Map<String,Object> resultMap = new HashMap<>();
        try {
            for (Article article:articles){
                xml = newsPusherService.articleToXml(article,xml);
             //文章推送
                newsPusherService.exportNewsToDuocai(xml,responseStr);
            }
        } catch (Exception e) {
            resultMap.put("code",400);
            resultMap.put("info",e.getCause().getCause());
            return map;
        }
        map.put("code",200);
        map.put("info","articles has export to duocai");
        return map;
    }
}
