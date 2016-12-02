package cn.com.cloudpioneer.service;

import cn.com.cloudpioneer.dao.CrawlerDataEntityDao;
import cn.com.cloudpioneer.dao.FieldCroperEntityDao;
import cn.com.cloudpioneer.entity.CrawlerDataEntity;
import cn.com.cloudpioneer.entity.FieldCroperEntity;
import cn.com.cloudpioneer.entity.TaskPositionEntity;
import cn.com.cloudpioneer.util.HandleXml;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
    private  CrawlerDataEntityDao crawlerDataEntityDao;

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
        TaskPositionEntity taskPositionEntity =crawlerDataEntityDao.findTaskEntity(taskId);
        int startPostion= taskPositionEntity.getPosition();
        List<CrawlerDataEntity> crawlerDataEntities= crawlerDataEntityDao.findByPage(startPostion, size,taskId);

        //加载字段精确裁剪规则croper
        FieldCroperEntity croper = fieldCroperEntityDao.findById(taskId);

        String xml= new HandleXml().readXml("/news.xml");
        List<String> datas=new ArrayList<>();
        for (CrawlerDataEntity entity:crawlerDataEntities){
            if (entity.getParsedData()!=null){
                JSONObject map=JSON.parseObject(entity.getParsedData());

                if(croper == null)  {
                    //通过Xpath已经精确提取
                    entity.setTitle(map.getString("title"));
                    entity.setAuthor(map.getString("author"));
                    entity.setSourceName(map.getString("sourceName"));
                    entity.setText(map.getString("content"));

                }else if(croper !=null) {
                    JSONObject cropObject = (JSONObject) JSON.parse(croper.getCropRule());
                    //不同字段的名称前缀信息拼接字段
                    List<String> fieldString = new ArrayList<>();
                    for(String key : cropObject.keySet())   {
                        JSONObject ruleObject = (JSONObject)JSON.parse(cropObject.getString(key));
                        fieldString.add(ruleObject.getString("preffix"));
                    }

                    //有字段冗余信息的裁剪规则
                    String author = getCropFieldValue("author", croper, map, fieldString);
                    if(author!=null)    {
                        entity.setAuthor(author);
                    }else {
                        entity.setAuthor(map.getString("author"));
                    }

                    String sourceName = getCropFieldValue("sourceName", croper, map, fieldString);
                    if(sourceName !=null)   {
                        entity.setSourceName(sourceName);
                    }else {
                        entity.setSourceName(map.getString("sourceName"));
                    }

                    entity.setTitle(map.getString("title"));
                    entity.setText(map.getString("content"));
                }
            }

            if(entity.getText() != null)    {
                datas.add(this.entityToXml(entity, xml));
            }
        }
        taskPositionEntity.setPosition(startPostion+crawlerDataEntities.size());
        crawlerDataEntityDao.updateTaskEntity(taskPositionEntity);
        return datas;
    }

    /**
     * 具体字段的裁剪
     * @param field_name    字段名称，如author, title，sourceName
     * @param croper    来自数据库配置的字段裁剪规则
     * @param parsedDataJSON    来自parsedData
     * @return 取得的精确字段值
     */
    public String getCropFieldValue(String field_name, FieldCroperEntity croper, JSONObject parsedDataJSON, List<String> fieldString) {
        //fieldName是数据库中字段的规则
        String cropRules = JSONObject.parseObject(croper.getCropRule()).getString(field_name);
        //特征值前缀
        String fieldName_preffixs = "";
        //特征值后缀
        String fieldName_suffixs = "";

        String cropFieldValue = "";
        //是否为空值
        boolean nullField = false;

        if (cropRules != null) {
            fieldName_preffixs = JSONObject.parseObject(cropRules).getString("preffix");
            String[] fieldPres = fieldName_preffixs.split("\\|");
            fieldName_suffixs = JSONObject.parseObject(cropRules).getString("suffix");
            String[] fieldSufs = fieldName_suffixs.split("\\|");

            // parsedDataJSON.getString(fieldName)用于保证就算此字段不能精确提取，但也要不为空才进行字符裁剪
            String waittingParseString = parsedDataJSON.getString(field_name);
            if (waittingParseString !=null && ! waittingParseString.equals("")) {

                String[] parsedDataFieldValue = waittingParseString.trim().split("\\s+");
                for (int i=0; i< parsedDataFieldValue.length; i++) {
                    //去前缀
                    for (String fieldPre : fieldPres) {
                        fieldPre = fieldPre.trim();
                        if (parsedDataFieldValue[i].contains(fieldPre)) {
                            //前缀为空
                            if(fieldPre.equals("")) {
                                cropFieldValue = parsedDataFieldValue[i];
                            }else{
                                if(parsedDataFieldValue[i].replace(fieldPre,"").equals("")==false)  {
                                    //前缀后面无空格
                                    cropFieldValue = parsedDataFieldValue[i].replace(fieldPre,"");
                                }else if(parsedDataFieldValue[i].replace(fieldPre,"").equals(""))  {
                                    //如果类似情况如：作者：之后就没有任务内容，那么就要有一个i+1< parsedDataFieldValue.length的判断
                                    if(i+1<parsedDataFieldValue.length) {
                                        //fieldString为数据库中提取字段的全部前缀
                                        for (String fieldValue : fieldString) {
                                            fieldValue=fieldValue.trim();
                                            if(! fieldValue.equals(fieldPre))    {
                                                if(parsedDataFieldValue[i+1].contains(fieldValue))    {
                                                    //判断字段是空格，并且后面的字符包含特征值，如"发布时间",表明当前字段为空
                                                    nullField = true;
                                                    break;
                                                }

                                            }
                                        }
                                    }else  {
                                        nullField = true;
                                    }
                                    //循环比较结束,发现分割值的下一个值不是特征值
                                    if(nullField==false)    {
                                        cropFieldValue = parsedDataFieldValue[i+1];
                                    }
                                }
                            }
                        }
                        //去后缀
                        for (String fieldSuf : fieldSufs) {
                            if(cropFieldValue.endsWith(fieldSuf))   {
                                //后缀为空
                                if(fieldSuf.equals("")) {
                                    break;
                                }else {
                                    cropFieldValue = cropFieldValue.replace(fieldSuf,"");
                                    break;
                                }
                            }
                        }
                    }
                    //如果已经找到特定字段的值，或者给字段本身不内容，跳出最外层循环
                    if(cropFieldValue.equals("")==false || nullField == true)    {
                        break;
                    }
                }
            }
            return cropFieldValue;
        }else {
            return null;
        }
    }

    /**
     * 比较两个字符串是否含有共同的子字符串
     * @param str1 字符串一
     * @param str2 字符串二
     * @param includeSelf 子字符串是否包括本身
     * @return 比较结果
     */
    public static boolean hasSameSubStr(String str1, String str2, boolean includeSelf)
    {
        String shortStr = str1.length() > str2.length()? str2: str1;
        String longStr = str1.length() > str2.length()? str1: str2;
        String temp = "";

        for(int i = 0; i < shortStr.length(); i++)
        {
            for(int j = i + 2; j <= shortStr.length(); j++)
            {
                temp = shortStr.substring(i, j);

                if(includeSelf && longStr.indexOf(temp) > 0)
                {
                    return true;
                }
                else if(!includeSelf && !temp.equals(shortStr) && longStr.indexOf(temp) > 0)
                {
                    return true;
                }
            }
        }

        return false;
    }

    //对字典的提取测试
    public static void main(String[] args) {
        /*String str = "作者：当代贵州评论员 编辑：杨刚 来源：《当代贵州》2016年第39期 发布时间：2016-10-20 20:19:48";
        String[] arr = str.split("\\s+");
        for (String s : arr) {
            if (s.startsWith("来源：")) {
                System.out.println(s.substring(3));
            }
        }*/
        /*String[] parsedDataFieldValue = "  啊啊啊 dc bd 作者： 高晓松 来源：".split("\\s+");
        JSONArray.toJSONString(parsedDataFieldValue).contains("来源");
        for (String s : parsedDataFieldValue) {

        }*/
        String [] arr = " aa               bbb     cc".split("\\s+");
        for (String s : arr) {
            System.out.println(s);
        }

        System.out.println(! (5==5));
    }


}
