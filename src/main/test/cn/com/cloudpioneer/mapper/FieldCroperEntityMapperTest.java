package cn.com.cloudpioneer.mapper;

import cn.com.cloudpioneer.dao.FieldCroperEntityDao;
import cn.com.cloudpioneer.entity.FieldCroperEntity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * <类详细说明:插入规则单元测试>
 *
 * @Author： Huanghai
 * @Version: 2016-11-28
 **/
public class FieldCroperEntityMapperTest {
    @Test
    public void testInsertRules()   {
        FieldCroperEntityDao fieldCroperEntityDao = new FieldCroperEntityDao();
        FieldCroperEntity fieldCroperEntity = new FieldCroperEntity();
        fieldCroperEntity.setTid("019c531802d4200e52586dc01677cd64");
        //构造过滤规则
        JSONObject jsonObject =new JSONObject();
        JSONObject rules =new JSONObject();
        rules.put("preffix","责任编辑：");
        jsonObject.put("author",rules);
        fieldCroperEntity.setCropRule(JSONArray.toJSONString(jsonObject));
        System.out.println(fieldCroperEntityDao.insertRules(fieldCroperEntity));
    }

    @Test
    public void testFindById()  {
        FieldCroperEntityDao fieldCroperEntityDao = new FieldCroperEntityDao();
        FieldCroperEntity fieldCroperEntity = fieldCroperEntityDao.findById("019c531802d4200e52586dc01677cd64");
        System.out.println(JSONObject.toJSONString(fieldCroperEntity));
    }
}
