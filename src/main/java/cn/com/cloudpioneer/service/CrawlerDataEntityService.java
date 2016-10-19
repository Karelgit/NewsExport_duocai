package cn.com.cloudpioneer.service;

import cn.com.cloudpioneer.dao.CrawlerDataEntityDao;
import cn.com.cloudpioneer.entity.CrawlerDataEntity;
import cn.com.cloudpioneer.entity.TaskEntity;
import cn.com.cloudpioneer.util.HandleXml;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tijun on 2016/9/21.
 */
public class CrawlerDataEntityService
{

    CrawlerDataEntityDao dataEntityDao=new CrawlerDataEntityDao();

    private String entityToXml(CrawlerDataEntity entity,String xml){
        xml=xml.replace("$content",entity.getText());
        xml=xml.replace("$title",entity.getTitle());

        if (entity.getAuthor()!=null){
            xml=xml.replace("$keywords4",entity.getAuthor());
        }else {
           xml= xml.replace("$keywords4","");
        }

        if(entity.getSourceName()!=null){
            xml=xml.replace("$sourceName",entity.getSourceName());
        }else {
            xml=xml.replace("$sourceName","");
        }

        return xml;
    }


    public List<String> crawlerDataEntityXml(int size,String taskId) throws IOException
    {
        TaskEntity taskEntity=dataEntityDao.findTaskEntity(taskId);
      int startPostion=taskEntity.getPosition();
       List<CrawlerDataEntity> crawlerDataEntities= dataEntityDao.findByPage(startPostion+1, size,taskId);

        String xml= new HandleXml().readXml("/news.xml");
        List<String> datas=new ArrayList<>();
        for (CrawlerDataEntity entity:crawlerDataEntities){
            if (entity.getJsonData()!=null){
                JSONObject map=JSON.parseObject(entity.getJsonData());
                entity.setTitle(map.getString("title"));
                entity.setSourceName(map.getString("sourceName"));
                Pattern pattern=Pattern.compile("<!--\\w+.*-->");
                Matcher matcher=pattern.matcher(map.getString("content"));
                while (matcher.find()){

                }
                entity.setText("");
            }

            if(entity.getText() != null)    {
                datas.add(this.entityToXml(entity, xml));
            }
        }
        taskEntity.setPosition(startPostion+crawlerDataEntities.size());
        dataEntityDao.updateTaskEntity(taskEntity);
        return datas;
    }



}
