package cn.com.cloudpioneer.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Tijun on 2016/9/26.
 */
public class QNRPageProcessor implements PageProcessor {
    //县市和州市新闻的url匹配
    private static final String QNR_ARTICLE_REGEX="http://www.qnz.com.cn/news/newsshow-\\d*.shtml";

    private Site site;

    public QNRPageProcessor(String url){
        site=Site.me();
        site.addStartUrl(url);
    }
    @Override
    public void process(Page page) {

        //match the url list

        List<String> urls=page.getHtml().xpath("//a/@href").all();
        for (String url:urls){
            if (url.matches(QNR_ARTICLE_REGEX)){
                System.out.println(url);
                page.addTargetRequest(url);
            }
        }
        if (page.getUrl().regex(QNR_ARTICLE_REGEX).match()){
            String title=page.getHtml().xpath("//div[@class='Title_h1']/h1/text()").toString();
            String contentHtml=page.getHtml().xpath("//div[@id='MyContent']//tr[1]/td/div").toString();
            System.out.println("title:"+title);
            System.out.println("content:"+contentHtml);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }


}
