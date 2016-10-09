package cn.com.cloudpioneer.mapper;

import cn.com.cloudpioneer.entity.CrawlerDataEntity;
import cn.com.cloudpioneer.entity.TaskEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by Tijun on 2016/9/21.
 */
public interface CrawlerDataEntityMapper
{
    @Select("SELECT seqeueID,tid,url,statusCode,pass,type,rootUrl,fromUrl,text,html,startTime,crawlTime," +
            "publishTime,depthfromSeed,title,count,tag,fetched,author,sourceName FROM crawlerdata_zktest " +
            "WHERE tid=#{taskId} ORDER BY seqeueID limit #{start},#{size}")
    List<CrawlerDataEntity> findByPage(@Param("start")long start,@Param("size")int size,@Param("taskId")String taskId);

    @Select("SELECT id,taskId,position FROM task WHERE taskId=#{taskId}")
    TaskEntity findTaskEntity(@Param("taskId") String taskId);

    @Update("UPDATE task set position=#{position} WHERE id=#{id}")
    void updateTaskEntity(TaskEntity entity);
}
