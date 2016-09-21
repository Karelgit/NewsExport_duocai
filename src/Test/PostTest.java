import cn.com.cloudpioneer.util.HandleXml;
import cn.com.cloudpioneer.util.PostUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <类详细说明:Post方法测试类>
 *
 * @Author： Huanghai
 * @Version: 2016-09-21
 **/
public class PostTest {
    @Test
    public void testPostMethod()  {
        String url = "http://work.gog.cn:8443/pub/cms_api_60/Api!login.do";
        Map<String,String> params = new HashMap<>();
//        String newsXML = HandleXml.readXml("news.xml");

//        params.put("news",newsXML);
        String response = PostUtil.postMethod(url,params);
        System.out.println(response);
    }
}
