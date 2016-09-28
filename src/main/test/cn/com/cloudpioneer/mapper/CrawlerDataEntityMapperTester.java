package cn.com.cloudpioneer.mapper;

import cn.com.cloudpioneer.dao.CrawlerDataEntityDao;
import cn.com.cloudpioneer.entity.CrawlerDataEntity;
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
        CrawlerDataEntityDao dataEntityDao=new CrawlerDataEntityDao();
        List<CrawlerDataEntity> crawlerDataEntities= dataEntityDao.findByPage(0, 10);

        CrawlerDataEntityService dataEntityService=new CrawlerDataEntityService();
  //    List<String>  datas=dataEntityService.crawlerDataEntityXml(50);
        for (CrawlerDataEntity entity :crawlerDataEntities){
            System.out.println(entity.getText());
        }



    }
}
