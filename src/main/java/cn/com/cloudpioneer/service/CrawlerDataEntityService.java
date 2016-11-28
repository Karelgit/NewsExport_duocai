package cn.com.cloudpioneer.service;

import cn.com.cloudpioneer.dao.CrawlerDataEntityDao;
import cn.com.cloudpioneer.dao.FieldCroperEntityDao;
import cn.com.cloudpioneer.entity.CrawlerDataEntity;
import cn.com.cloudpioneer.entity.FieldCroperEntity;
import cn.com.cloudpioneer.entity.TaskPositionEntity;
import cn.com.cloudpioneer.util.HandleXml;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tijun on 2016/9/21.
 */
@Service
public class CrawlerDataEntityService {
    @Autowired
    private FieldCroperEntityDao fieldCroperEntityDao;
    @Autowired
    private  CrawlerDataEntityDao dataEntityDao;

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
        TaskPositionEntity taskPositionEntity =dataEntityDao.findTaskEntity(taskId);
        int startPostion= taskPositionEntity.getPosition();
        List<CrawlerDataEntity> crawlerDataEntities= dataEntityDao.findByPage(startPostion, size,taskId);

        //加载字段精确裁剪规则croper
        FieldCroperEntity croper = fieldCroperEntityDao.findById(taskId);

        String xml= new HandleXml().readXml("/news.xml");
        List<String> datas=new ArrayList<>();
        for (CrawlerDataEntity entity:crawlerDataEntities){
            if (entity.getParsedData()!=null){
                JSONObject map=JSON.parseObject(entity.getParsedData());

                if(croper == null)  {
                    //已经精确提取
                    entity.setTitle(map.getString("title"));
                    entity.setAuthor(map.getString("author"));
                    entity.setSourceName(map.getString("sourceName"));
                    entity.setText(map.getString("content"));

                }else if(croper !=null) {
                    //有字段冗余信息的裁剪规则
                    if(map.getString("author")!=null)   {
                        map.get("author").toString().split("\\s+");
//                        entity.setAuthor(map.get("author"));
                    }

                }
                entity.setTitle(map.getString("title"));

                //infoBar字段中是否有值
                boolean hasInfoBar =(map.getString("infoBar")!=null);

                //作者字段
                /*if(map.getString("author")!=null&& croper.getCropRule())   {
                    entity.setAuthor(map.getString("author").substring(3));
                }else if(hasInfoBar)  {
                    String infoBar= map.getString("infoBar");
                    String [] arr = infoBar.split("\\s+");
                    for (String s : arr) {
                        if (s.startsWith("编辑：")) {
                            entity.setAuthor(s.substring(3));
                        }
                    }
                }*/

                //来源字段
                if(map.getString("sourceName")!=null)   {
                    entity.setSourceName(map.getString("sourceName").substring(3));
                } else if(hasInfoBar)  {
                    String infoBar= map.getString("infoBar");
                    String [] arr = infoBar.split("\\s+");
                    for (String s : arr) {
                        if (s.startsWith("来源：")) {
                            entity.setSourceName(s.substring(3));
                        }
                    }
                }
                entity.setText(map.getString("content"));
            }

            if(entity.getText() != null)    {
                datas.add(this.entityToXml(entity, xml));
            }
        }
        taskPositionEntity.setPosition(startPostion+crawlerDataEntities.size());
        dataEntityDao.updateTaskEntity(taskPositionEntity);
        return datas;
    }

    //对字典的提取测试
    public static void main(String[] args) {
        String str = "作者：当代贵州评论员 编辑：杨刚 来源：《当代贵州》2016年第39期 发布时间：2016-10-20 20:19:48";
        String[] arr = str.split("\\s+");
        for (String s : arr) {
            if (s.startsWith("来源：")) {
                System.out.println(s.substring(3));
            }
        }
    }

}
