package cn.com.cloudpioneer.service;

import cn.com.cloudpioneer.dao.CrawlerDataEntityDao;
import cn.com.cloudpioneer.entity.CrawlerDataEntity;
import cn.com.cloudpioneer.entity.TaskEntity;
import cn.com.cloudpioneer.util.HandleXml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tijun on 2016/9/21.
 */
public class CrawlerDataEntityService
{
    CrawlerDataEntityDao dataEntityDao=new CrawlerDataEntityDao();

    private String entityToXml(CrawlerDataEntity entity,String xml){
        String domianPrefix = "http://www.qnz.com.cn";
        //为content里面的img标签变成绝对路径
        String content_imgabsolute = entity.getText().replace("src=\"","src=\""+domianPrefix);
        xml=xml.replace("$content",content_imgabsolute);
        xml=xml.replace("$title",entity.getTitle());
        return xml;
    }


    public List<String> crawlerDataEntityXml(int size,String taskId) throws IOException
    {
        TaskEntity taskEntity=dataEntityDao.findTaskEntity(taskId);
      int startPostion=taskEntity.getPosition();/*this.getPosition();*/
       List<CrawlerDataEntity> crawlerDataEntities= dataEntityDao.findByPage(startPostion, size,taskId);

        String xml= new HandleXml().readXml("news.xml");
        List<String> datas=new ArrayList<>();
        for (CrawlerDataEntity entity:crawlerDataEntities){
            if(entity.getText() != null)    {
                datas.add(this.entityToXml(entity, xml));
            }
        }
        taskEntity.setPosition(startPostion+crawlerDataEntities.size());
        dataEntityDao.updateTaskEntity(taskEntity);
        return datas;
    }


}
