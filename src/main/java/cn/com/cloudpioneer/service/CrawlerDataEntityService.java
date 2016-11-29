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
                    //有字段冗余信息的裁剪规则
                    String author = getCropFieldValue("author", croper, map);
                    if(author!=null)    {
                        entity.setAuthor(author);
                    }else {
                        entity.setAuthor(map.getString("author"));
                    }

                    String sourceName = getCropFieldValue("sourceName", croper, map);
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
    public String getCropFieldValue(String field_name, FieldCroperEntity croper, JSONObject parsedDataJSON) {
        //fieldName是数据库中字段的规则
        String cropRules = JSONObject.parseObject(croper.getCropRule()).getString(field_name);
        //特征值前缀
        String fieldName_preffixs = null;
        //特征值后缀
        String fieldName_suffixs = null;

        String cropFieldValue = null;
        if (cropRules != null) {
            fieldName_preffixs = JSONObject.parseObject(cropRules).getString("preffix");
            String[] fieldPres = fieldName_preffixs.split("\\|");
            fieldName_suffixs = JSONObject.parseObject(cropRules).getString("suffix");
            String[] fieldSufs = fieldName_suffixs.split("\\|");

            // parsedDataJSON.getString(fieldName)用于保证就算此字段不能精确提取，但也要不为空才进行字符裁剪
            String waittingParseString = parsedDataJSON.getString(field_name);
            if (waittingParseString != null) {

                String[] parsedDataFieldValue = waittingParseString.split("\\s+");
                for (int i=0; i< parsedDataFieldValue.length; i++) {
                    //去前缀
                    for (String fieldPre : fieldPres) {
                        if (parsedDataFieldValue[i].contains(fieldPre)) {
                            //前缀为空
                            if(fieldPre.equals("")) {
                                cropFieldValue = parsedDataFieldValue[i];
                            }else{
                                /**后缀不为空
                                 * 处理属性名和属性值之间有空格
                                 * 如果parsedDataFieldValue[i].substring(fieldPre.length()) != null，说明属性名比如'来源：'直接跟如'新华网'
                                 * 如果parsedDataFieldValue[i].substring(fieldPre.length()) 为 null，说明如'来源：'后面有空字符串，通过判断之后的字段是否
                                 * 包含裁剪规则的前缀，可以知道此字段是否为来源的值
                                 */

                                if(parsedDataFieldValue[i].substring(fieldPre.length()).equals("")==false)  {
                                    cropFieldValue = parsedDataFieldValue[i].substring(fieldPre.length());
                                    break;
                                }else if(parsedDataFieldValue[i].substring(fieldPre.length()).equals("")
                                        && JSONArray.toJSONString(fieldPres).contains(parsedDataFieldValue[i+1]) == false)  {

                                    cropFieldValue = parsedDataFieldValue[i+1];
                                    break;
                                }
                            }
                        }
                        //去后缀
                        for (String fieldSuf : fieldSufs) {
                            if(cropFieldValue.endsWith(fieldSuf))   {
                                //后缀为空
                                if(fieldSuf.equals("")) {
                                    //do nothing
                                }else {
                                    cropFieldValue = cropFieldValue.substring(0,cropFieldValue.length()-cropFieldValue.indexOf(fieldSuf)+1);
                                }
                                break;
                            }
                        }
                    }
                }
            }
            return cropFieldValue;
        }else {
            return null;
        }
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
        String [] arr = " ".split("\\|");
        for (String s : arr) {
            
        }
    }


}
