package cn.com.cloudpioneer.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <类详细说明>
 *
 * @Author： Huanghai
 * @Version: 2016-10-13
 **/
public class RegexTest {
    public static void main(String[] args) {
       /* String str = "11111<img class src=\"/dap/dxeditor.output.f?pkid=01120316032104947034\" width=62 height=62>测试<img src=\"/192.168.1.4:8080/dap/dxeditor.output.f?pkid=01120316032104947034\">222222<img src=\"/dd\">\"";
//        String str1 = "<img class src><img class csrca>";
        Pattern p = Pattern.compile("<img[^>]*>.*?");
        Matcher m = p.matcher(str);
        if(m.find())    {
            System.out.println(m.group(0));
        }*/

      Pattern  pattern = Pattern.compile("<!--.*?-->");
      Matcher  matcher = pattern.matcher("<!--dfdsfd--> <!--dfdd--><sdsdsdsd>");
        String str = matcher.replaceAll("");
        System.out.println(str);
    }
}
