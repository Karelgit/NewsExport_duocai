package cn.com.cloudpioneer.util;

import java.io.*;

/**
 * <类详细说明:对xml文件的操作>
 *
 * @Author： Huanghai
 * @Version: 2016-09-21
 **/
public class HandleXml {
    public String readXml(String fileName) {
        InputStream in = ClassLoader.getSystemResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuffer stringBuffer = new StringBuffer();
        String str;
        try {
            while ((str = reader.readLine()) !=null)    {
                stringBuffer.append(str+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    public void  writeXml(String xml,String path)  {
        try {
           path= this.getClass().getResource(path).getPath();
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(xml);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeResponseToLocal(String xml,String path)  {
        try {
            FileWriter fw = new FileWriter(path,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(xml);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        String xmlPath = projectPath+"/src/main/resources/test.xml";
//        readXml("news.xml");
        new HandleXml().writeXml("bbb"+"\n",xmlPath);
    }
}
