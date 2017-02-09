package NewsPusherModule.controller;

import NewsPusherModule.service.CrawlerDataEntityService;
import NewsPusherModule.service.NewsPusherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * <类详细说明:推送任务>
 *
 * @Author： Huanghai
 * @Version: 2016-11-29
 **/
@RestController
@Scope("prototype")
public class taskController {
    @Autowired
    private CrawlerDataEntityService service;
    @Autowired
    private NewsPusherService newsPusherService;

    @RequestMapping(value = "/newsExport/{tid}")
    public String newsExport(@PathVariable String tid) throws Exception {
        List<String> xmls = null;
        try {
            xmls = service.crawlerDataEntityXml(50,tid);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("service生成xml数据错误！");
        }

        //登陆获得返回数据
        String param = newsPusherService.loginDuocai();
        String result = null;
        for (String xml:xmls){
            //String xml = NewsPushUtil.readResourceAsXml("/newsTest.xml");
            //推送文章
            try {

//                result = newsPusherService.exportNewsToDuocai(xml,param); deprecated method...
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("推送到北方网错误！");
            }
        }
        return "done!";
    }

    @RequestMapping(value = "/testFinishedXml/{tid}")
    public String testFinishedXml(@PathVariable String tid) {
        StringBuffer sb = new StringBuffer();
        try {
            List<String> list = service.crawlerDataEntityXml(10,tid);
            sb = new StringBuffer();
            for (String s : list) {
                sb.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "注意：测试完之后记住把数据库表pos_duocai_export记录的位置回到测试之前的数值"+ '\n' + sb.toString();
    }
}
