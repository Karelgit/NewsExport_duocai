package cn.com.cloudpioneer.util;

import java.io.*;

/**
 * <类详细说明:对xml文件的操作>
 *
 * @Author： Huanghai
 * @Version: 2016-09-21
 **/
public class HandleXml {
    public static String readXml(String fileName) {
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
        System.out.println(stringBuffer.toString());
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        readXml("news.xml");
    }
}
