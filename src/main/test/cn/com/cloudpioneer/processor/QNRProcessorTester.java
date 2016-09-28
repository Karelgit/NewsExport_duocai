package cn.com.cloudpioneer.processor;

import cn.com.cloudpioneer.downloader.SeleniumDownloader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tijun on 2016/9/26.
 */
public class QNRProcessorTester {

    @Test
    public void testRegex(){
        Pattern QNR_ARTICLE_REGEX=Pattern.compile("http://www.qnz.com.cn/news/newsshow-\\d*.shtml");
        Matcher matcher=QNR_ARTICLE_REGEX.matcher("http://www.qnz.com.cn/news/newsshow-29887.shtml");
        System.out.println(matcher.matches());
    }

    @Test
    public void testQNRProcessor(){
       QNRPageProcessor qnrPageProcessor=new  QNRPageProcessor("http://www.qnz.com.cn/news/newslist-0-12.shtml");
        SeleniumDownloader downloader=new SeleniumDownloader();
       // downloader.setSleepTime(10);
       Spider spider= Spider.create(qnrPageProcessor);
        spider.setDownloader(new cn.com.cloudpioneer.downloader.HttpClientDownloader());

                spider.addPipeline(new JsonFilePipeline("D:\\testData\\SpiderData\\"));
        spider.thread(5);
        spider.run();
    }
    @Test
    public void getArticle() throws IOException {
        String url="http://www.qnz.com.cn/news/newslist-0-12.shtml";
        HttpClient client=new DefaultHttpClient();
        HttpGet get=new HttpGet(url);
        HttpResponse response=client.execute(get);
     String content=   EntityUtils.toString(response.getEntity());
        System.out.println(content);
    }

}
