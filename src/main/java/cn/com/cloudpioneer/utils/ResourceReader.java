package cn.com.cloudpioneer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Tijun on 2016/9/20.
 */
public class ResourceReader
{
    public static String readResource(String path) throws IOException
    {
       InputStream is= ResourceReader.class.getResourceAsStream(path);
        InputStreamReader reader=new InputStreamReader(is);
        char [] chars=new char[1024];
        StringBuffer buffer=new StringBuffer();
        while (reader.read(chars,0,chars.length)!=-1){
            buffer.append(chars);
        }
       return buffer.toString();
    }
}
