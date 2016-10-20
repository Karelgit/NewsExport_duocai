package cn.com.cloudpioneer.mapper;

import cn.com.cloudpioneer.dao.CrawlerDataEntityDao;
import cn.com.cloudpioneer.service.CrawlerDataEntityService;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by Tijun on 2016/9/21.
 */
public class CrawlerDataEntityMapperTester
{
    @Test
    public void testGainData() throws IOException
    {
        CrawlerDataEntityService dataEntityService=new CrawlerDataEntityService();
      List<String>  datas=dataEntityService.crawlerDataEntityXml(70,"");
        for (String s:datas){
            System.out.println(s);
        }
    }

    @Test
    public void testCount(){
        CrawlerDataEntityDao dao=new CrawlerDataEntityDao();
        System.out.println(dao.count());
    }
}
