package cn.com.cloudpioneer.mapper;

import cn.com.cloudpioneer.entity.CrawlerDataEntity;
import cn.com.cloudpioneer.entity.TaskPositionEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by Tijun on 2016/9/21.
 */
public interface CrawlerDataEntityMapper
{
    @Select("SELECT parsedData from crawlerdata_duocai WHERE tid=#{taskId} limit #{start},#{size}")
    List<CrawlerDataEntity> findByPage(@Param("start")long start,@Param("size")int size,@Param("taskId")String taskId);

    @Select("SELECT id,taskId,position FROM pos_duocai_export WHERE taskId=#{taskId}")
    TaskPositionEntity findTaskEntity(@Param("taskId") String taskId);

    @Update("UPDATE pos_duocai_export set position=#{position} WHERE id=#{id}")
    void updateTaskEntity(TaskPositionEntity entity);

    @Select("SELECT position FROM pos_duocai_export WHERE taskid =#{taskId}")
    int getPosFromTaskEntity(String taskId);

    @Select("SELECT COUNT(*) FROM zk_test")
    int count();
}
