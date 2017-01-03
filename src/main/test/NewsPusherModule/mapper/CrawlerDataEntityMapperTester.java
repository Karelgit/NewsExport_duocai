package NewsPusherModule.mapper;

import NewsPusherModule.entity.CrawlerDataEntity;
import NewsPusherModule.entity.TaskPositionEntity;
import NewsPusherModule.service.CrawlerDataEntityService;
import NewsPusherModule.util.JSONUtil;
import NewsPusherModule.dao.CrawlerDataEntityDao;
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
        List<CrawlerDataEntity> list= dao.findByPage(0,100,"019c531802d4200e52586dc01677cd64");
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
