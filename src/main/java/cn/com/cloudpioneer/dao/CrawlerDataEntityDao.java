package cn.com.cloudpioneer.dao;

import cn.com.cloudpioneer.entity.CrawlerDataEntity;
import cn.com.cloudpioneer.entity.TaskEntity;
import cn.com.cloudpioneer.mapper.CrawlerDataEntityMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Created by Tijun on 2016/9/21.
 */
public class CrawlerDataEntityDao
{

    private static Reader reader;
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    public List<CrawlerDataEntity> findByPage(long start,int size,String taskId){
       SqlSession sqlSession= sqlSessionFactory.openSession();
        CrawlerDataEntityMapper crawlerDataEntityMapper=sqlSession.getMapper(CrawlerDataEntityMapper.class);
      List<CrawlerDataEntity> crawlerDataEntities=  crawlerDataEntityMapper.findByPage(start, size,taskId);
        sqlSession.commit(true);
        return crawlerDataEntities;
    }

    public TaskEntity findTaskEntity(String taskId){
        SqlSession sqlSession= sqlSessionFactory.openSession();
        CrawlerDataEntityMapper crawlerDataEntityMapper=sqlSession.getMapper(CrawlerDataEntityMapper.class);
        return crawlerDataEntityMapper.findTaskEntity(taskId);
    }
    public void updateTaskEntity(TaskEntity entity){
        SqlSession sqlSession= sqlSessionFactory.openSession();
        CrawlerDataEntityMapper crawlerDataEntityMapper=sqlSession.getMapper(CrawlerDataEntityMapper.class);
        crawlerDataEntityMapper.updateTaskEntity(entity);
        sqlSession.commit();
    }

}