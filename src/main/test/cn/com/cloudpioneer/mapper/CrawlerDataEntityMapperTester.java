package cn.com.cloudpioneer.mapper;

import cn.com.cloudpioneer.dao.CrawlerDataEntityDao;
import cn.com.cloudpioneer.entity.CrawlerDataEntity;
import cn.com.cloudpioneer.entity.TaskPositionEntity;
import cn.com.cloudpioneer.service.CrawlerDataEntityService;
import cn.com.cloudpioneer.util.JSONUtil;
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

    @Test
    public void testGetPos() {
        CrawlerDataEntityDao dao=new CrawlerDataEntityDao();
        System.out.println(dao.getPosFromTaskEntity("acbrocdldrtfkauj9ertt29d67"));
    }

    @Test
    public void testFindByPage()    {
        CrawlerDataEntityDao dao=new CrawlerDataEntityDao();
        List<CrawlerDataEntity> list= dao.findByPage(1,101,"2ebb2984228fd024bfac23dbcb375a9e");
        /*for (CrawlerDataEntity crawlerDataEntity : list) {
            System.out.println(JSONUtil.object2JacksonString(crawlerDataEntity));
        }*/

        /*TaskPositionEntity taskPositionEntity = new TaskPositionEntity();
        taskPositionEntity.setPosition(170+list.size());
        dao.updateTaskEntity(taskPositionEntity);*/
        System.out.println(list.size());
    }

    @Test
    public void findTaskEntity()    {
        CrawlerDataEntityDao dao=new CrawlerDataEntityDao();
        TaskPositionEntity taskPositionEntity = dao.findTaskEntity("acbrocdldrtfkauj9ertt29d67");
        System.out.println(JSONUtil.object2JacksonString(taskPositionEntity));
    }
}
