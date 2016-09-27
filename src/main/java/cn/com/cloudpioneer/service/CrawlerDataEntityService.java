package cn.com.cloudpioneer.service;

import cn.com.cloudpioneer.dao.CrawlerDataEntityDao;
import cn.com.cloudpioneer.entity.CrawlerDataEntity;
import cn.com.cloudpioneer.utils.ResourceReader;

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
    private long dataPostion;

    CrawlerDataEntityDao dataEntityDao=new CrawlerDataEntityDao();

    private String entityToXml(CrawlerDataEntity entity,String xml){
        String domianPrefix = "http://www.qnz.com.cn";
        //为content里面的img标签变成绝对路径
        String content_imgabsolute = entity.getText().replace("src=\"","src=\""+domianPrefix);
        xml=xml.replace("$content",content_imgabsolute);
        xml=xml.replace("$title",entity.getTitle());
      /* *//* SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date="";
        if(entity.getPublishTime()!=null){
            date=sdf.format(entity.getPublishTime());
        }*//*
        xml=xml.replace("$pubDate",date);*/
//        xml=xml.replaceAll("\\r\\n","");
        return xml;
    }


    public List<String> crawlerDataEntityXml(int size) throws IOException
    {
      long startPostion=this.getPosition();

       List<CrawlerDataEntity> crawlerDataEntities= dataEntityDao.findByPage(startPostion, size);
        String xml=ResourceReader.readResource("/news.xml");
        List<String> datas=new ArrayList<>();
        for (CrawlerDataEntity entity:crawlerDataEntities){
            if(entity.getText() != null)    {
                datas.add(this.entityToXml(entity, xml));
            }
        }

        this.writeNumToProperties(startPostion+datas.size());

        return datas;

    }
    private long getPosition(){
        InputStream is= this.getClass().getResourceAsStream("/dataPosition.properties");
        Properties properties=new Properties();
        try
        {
            properties.load(is);
            String num=(String)properties.get("position");
         return    Long.parseLong(num);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    private void writeNumToProperties(long num){
      String path=  this.getClass().getResource("/dataPosition.properties").getPath();
       String positionInfo="position="+num;
        FileOutputStream fileOutputStream=null;
        try
        {
             fileOutputStream= new FileOutputStream(path);
            try
            {
                fileOutputStream.write(positionInfo.getBytes());
                fileOutputStream.flush();

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }finally
        {
            if(fileOutputStream!=null){
                try
                {
                    fileOutputStream.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

        }
    }


}
