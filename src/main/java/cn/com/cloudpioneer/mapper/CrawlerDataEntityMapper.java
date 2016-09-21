package cn.com.cloudpioneer.mapper;

import cn.com.cloudpioneer.entity.CrawlerDataEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Tijun on 2016/9/21.
 */
public interface CrawlerDataEntityMapper
{
    @Select("SELECT seqeueID,tid,url,statusCode,pass,type,rootUrl,fromUrl,text,html,startTime,crawlTime," +
            "publishTime,depthfromSeed,title,count,tag,fetched FROM crawlerdata ORDER BY seqeueID limit #{start},#{size}")
   List<CrawlerDataEntity> findByPage(@Param("start")long start,@Param("size")int size);
}
