package cn.com.cloudpioneer.service;

import cn.com.cloudpioneer.ApplicationNewsExport;
import cn.com.cloudpioneer.dao.FieldCroperEntityDao;
import cn.com.cloudpioneer.entity.FieldCroperEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <类详细说明>
 *
 * @Author： Huanghai
 * @Version: 2016-09-27
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationNewsExport.class)
@WebAppConfiguration
public class CrawlerDataEntityServiceTest {
    @Autowired
    private CrawlerDataEntityService crawlerDataEntityService;
    @Autowired
    private FieldCroperEntityDao fieldCroperEntityDao;

    @Test
    public void testRegex() {
        String domain = "http://www.qnz.com.cn";
        String str = "<div id=\"content_main\">\n" +
                "\" +\n" +
                "                \" <p align=\\\"center\\\"> <strong>航龙天文小镇首批项目开工</strong> </p>\\n\" +\n" +
                "                \" <p align=\\\"center\\\"> <strong>秦如培宣布开工 龙长春讲话 向红琼主持</strong> </p>\\n\" +\n" +
                "                \" <p> 　　在喜迎世界最大500米口径球面射电望远镜落成启用之际，9月25日下午，航龙天文小镇也迎来了首批项目开工仪式。据悉，航龙天文小镇是以天文科研为核心、旅游产业为纽带、跨区域合作为平台的非建制镇，覆盖我州“三县六镇”。 </p>\\n\" +\n" +
                "                \" <p align=\\\"center\\\"> <img alt=\\\"\\\" src=\\\"/upload/editor/2016-9-26/201692614133700vdtz0.jpg\\\"> </p>\\n\" +\n" +
                "                \" <p align=\\\"center\\\"> <span style=\\\"color:#337fe5;\\\">仪式现场</span> </p>\\n\" +\n" +
                "                \" <p> 　　省委常委、常务副省长秦如培宣布开工，州委书记龙长春讲话，州委副书记、州长向红琼主持开工仪式。出席开工仪式的领导还有：省政府副秘书长潘小林，省发改委副主任徐望北，省住建厅副厅长宋丽丽，州领导刘建民、王国太、陈忆秋、胡晓剑、瓦标龙等。 </p>\\n\" +\n" +
                "                \" <p align=\\\"center\\\"> <img alt=\\\"\\\" src=\\\"/upload/editor/2016-9-26/2016926141351563t6yhb.jpg\\\"> </p>\\n\" +\n" +
                "                \" <p align=\\\"center\\\"> <span style=\\\"color:#337fe5;\\\">省委常委、常务副省长秦如培宣布未来天文小镇正式开工</span> </p>\\n\" +\n" +
                "                \" <p align=\\\"center\\\"> <img alt=\\\"\\\" src=\\\"/upload/editor/2016-9-26/20169261414581146eikv.jpg\\\"> </p>\\n\" +\n" +
                "                \" <p align=\\\"center\\\"> <span style=\\\"color:#337fe5;\\\">州委书记龙长春介绍未来天文小镇基本情况</span> </p>\\n\" +\n" +
                "                \" <p align=\\\"center\\\"> <img alt=\\\"\\\" src=\\\"/upload/editor/2016-9-26/2016926141536460e9545.jpg\\\"> </p>\\n\" +\n" +
                "                \" <p align=\\\"center\\\"> <span style=\\\"color:#337fe5;\\\">州委副书记、州长向红琼主持奠基仪式</span> </p>\\n\" +\n" +
                "                \" <span style=\\\"color:#337fe5;\\\"></span>\\n\" +\n" +
                "                \" <span style=\\\"color:#337fe5;\\\"></span>\\n\" +\n" +
                "                \" <p> 　　航龙天文小镇覆盖我州平塘县克度镇、塘边镇、通州镇，罗甸县边阳镇、沫阳镇，惠水县羡塘镇“三县六镇”，规划建设总面积为1954平方公里。其中，克度镇航龙片区是小镇的核心区，也是今后我州着力打造的科研科普中心、学术交流高地和人才聚集洼地。按照省委关于“建设世界一流天文小镇”的战略定位，我州将重点立足大射电科研科普、大数据收储应用、天文体验旅游等新业态，努力将航龙天文小镇打造成世界一流的天文科学技术研究中心、天文学术交流高地、天文科普基地、天文旅游目的地。 </p>\\n\" +\n" +
                "                \" <p align=\\\"center\\\"> <img alt=\\\"\\\" src=\\\"/upload/editor/2016-9-26/2016926141611494f3bfd.jpg\\\"> </p>\\n\" +\n" +
                "                \" <p align=\\\"center\\\"> <span style=\\\"color:#337fe5;\\\">出席活动领导为未来天文小镇集中开工项目培土奠基</span> </p>\\n\" +\n" +
                "                \" <p> 　　龙长春表示，黔南州将认真贯彻落实中共中央政治局委员、国务院副总理刘延东在500米口径球面射电望远镜落成启用仪式上的讲话精神，以“开局就是决战、起步就是冲刺”的拼搏精神，在国家有关部委、中国科学院、国家天文台的关怀和省委、省政府的领导下，大力发扬“登高望远、精益求精、勇于争先”的大射电精神，倒排工期、挂图作战，全力确保天文小镇按时保质保量安全完工，让大射电汇聚的强大科技力量、凝聚的卓越创新精神为黔南打造民族地区创新发展先行示范区注入强大动力，助推黔南同步小康、富民强州。（作者：周道来 李庆红 蒋龙城 韦俨芳） </p>\\n\" +\n" +
                "                \" <p> &nbsp;&nbsp;&nbsp; （编辑：保超燕&nbsp; 审核编辑：邹骐聪） </p>\\n\" +\n" +
                "                \"</div>]";
            System.out.println(str.replace("src=\\\"","src=\\\""+domain));

        /*String str1 = "<img alt=\"\" src=\"/upload/editor/2016-9-26/2016926141536460e9545.jpg\">";
        System.out.println(str1.matches("^(<img).*(>)$"));*/
    }

    @Test
    public void testRemoveAdd(){
        String hstml="<div class=\"BSHARE_POP blkContainerSblkCon clearfix blkContainerSblkCon_14\" id=\"artibody\"> \n" +
                " <div class=\"img_wrapper\"> \n" +
                "  <img alt=\"易建联会留在湖人吗\" src=\"http://k.sinaimg.cn/n/sports/transform/20161013/FmRI-fxwrtna8355296.jpg/w570fac.jpg\"> \n" +
                "  <span class=\"img_descr\">易建联会留在湖人吗</span> \n" +
                "  <br> \n" +
                " </div> \n" +
                "\n" +
                " <div id=\"ad_51777\" class=\"otherContent_01\" style=\"display: block; margin: 10px 20px 10px 0px; float: left; overflow: hidden; clear: both; padding: 4px; width: 300px; height: 250px;\"> \n" +
                "  <ins class=\"sinaads sinaads-done\" id=\"Sinads49447\" data-ad-pdps=\"PDPS000000044110\" data-ad-status=\"done\" style=\"display: block; overflow: hidden; text-decoration: none;\"><ins style=\"text-decoration:none;margin:0px auto;width:300px;display:block;position:relative;overflow:hidden;\"><iframe width=\"300px\" height=\"250px\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" vspace=\"0\" hspace=\"0\" allowtransparency=\"true\" scrolling=\"no\" src=\"http://x.jd.com/exsites?spread_type=2&amp;ad_ids=1884:5&amp;location_info=0&amp;callback=getjjsku_callback\" name=\"clickTAG=http%3A%2F%2Fsax.sina.com.cn%2Fmfp%2Fclick%3Ftype%3D3%26t%3DMjAxNi0xMC0xMyAxNjozNToyMwkxMTEuMTIxLjY3LjgwCTExMS4xMjEuNjcuODBfMTQ3NjM0NzY5NC43MTcyNjYJaHR0cDovL3Nwb3J0cy5zaW5hLmNvbS5jbi9iYXNrZXRiYWxsL25iYS8yMDE2LTEwLTEzL2RvYy1pZnh3dnBhcjc4OTQ4OTAuc2h0bWwJUERQUzAwMDAwMDA0NDExMAk2ODEzYmM1OC1hMDU4LTRkMDItYWQ2Mi1kZTI1NzUwYTQ2MGEJQURBQUZFQ0U0OUQ0CUFEQUFGRUNFNDlENAlhdXRvX2xldmVsOjE4MDEwMHx1c2VyX2dyb3VwOjkwMiw5MDUsOTAzfHVzZXJfdGFnOjIwNzM3fHBvczpQRFBTMDAwMDAwMDQ0MTEwfHdhcF9vczo3MDB8dXNlcl9hZ2U6NjAyLDYwM3x2X3pvbmU6MzA4MDAwLDMwODAwMXx1c2VyX2dlbmRlcjo1MDF8X3Zfem9uZTozMDgwMDAsMzA4MDAxfGF1dG9fcHJpY2U6MTgwMjAwfG1vYmlsZV9icmFuZDoxMjA5fG1vYmlsZV9icm93c2VyOjgxMXx2ZXJzaW9uOmdsX3JwMV8xCQkzMDgwMDB8MzA4MDAxCUNEMENDRkFEM0Q4NglMWTE1MTIwOTY1CVBEUFMwMDAwMDAwNDQxMTAJQ0QwQ0NGQUQzRDg2X3BvcnRhbAlBRQktCTYJLQktCS0JLQktCS0JLQktCTIJMTQJc3RyYXRlZ3lfdWFjaGwJMAk2CW9zOndpbmRvd3M4fGJyb3dzZXI6cGhhbnRvbWpzfGlncnBfY3RyOjguNTAwMjE5NDMwMDI5MzY5RS00fHBsYXRmb3JtOm90aGVyfGRldmljZTpvdGhlcg%253D%253D&amp;viewTAG=http%3A%2F%2Fsax.sina.com.cn%2Fmfp%2Fview%3Ftype%3D3%26t%3DMjAxNi0xMC0xMyAxNjozNToyMwkxMTEuMTIxLjY3LjgwCTExMS4xMjEuNjcuODBfMTQ3NjM0NzY5NC43MTcyNjYJaHR0cDovL3Nwb3J0cy5zaW5hLmNvbS5jbi9iYXNrZXRiYWxsL25iYS8yMDE2LTEwLTEzL2RvYy1pZnh3dnBhcjc4OTQ4OTAuc2h0bWwJUERQUzAwMDAwMDA0NDExMAk2ODEzYmM1OC1hMDU4LTRkMDItYWQ2Mi1kZTI1NzUwYTQ2MGEJQURBQUZFQ0U0OUQ0CUFEQUFGRUNFNDlENAlhdXRvX2xldmVsOjE4MDEwMHx1c2VyX2dyb3VwOjkwMiw5MDUsOTAzfHVzZXJfdGFnOjIwNzM3fHBvczpQRFBTMDAwMDAwMDQ0MTEwfHdhcF9vczo3MDB8dXNlcl9hZ2U6NjAyLDYwM3x2X3pvbmU6MzA4MDAwLDMwODAwMXx1c2VyX2dlbmRlcjo1MDF8X3Zfem9uZTozMDgwMDAsMzA4MDAxfGF1dG9fcHJpY2U6MTgwMjAwfG1vYmlsZV9icmFuZDoxMjA5fG1vYmlsZV9icm93c2VyOjgxMXx2ZXJzaW9uOmdsX3JwMV8xCQkzMDgwMDB8MzA4MDAxCUNEMENDRkFEM0Q4NglMWTE1MTIwOTY1CVBEUFMwMDAwMDAwNDQxMTAJQ0QwQ0NGQUQzRDg2X3BvcnRhbAlBRQktCTYJLQktCS0JLQktCS0JLQktCTIJMTQJc3RyYXRlZ3lfdWFjaGwJMAk2CW9zOndpbmRvd3M4fGJyb3dzZXI6cGhhbnRvbWpzfGlncnBfY3RyOjguNTAwMjE5NDMwMDI5MzY5RS00fHBsYXRmb3JtOm90aGVyfGRldmljZTpvdGhlcg%253D%253D%26userid%3D111.121.67.80_1476347694.717266%26viewlog%3Dfalse%26hashCode%3D31ad7f5fbed2a5dde3f1f7fac06c2833\"></iframe></ins></ins> \n" +
                " </div> \n" +
                " <p>　　新浪体育讯　　北京时间10月13日，据《湖人国度》报道，洛杉矶湖人队今天完成了新赛季开始前的第一次裁人，扎克-奥古斯特、特拉维斯-威尔以及朱利安-雅各布斯这三名球员被裁，目前湖人队的阵容里还剩下17名球员。</p> \n" +
                " <p>　　湖人队今天裁掉这三名球员并不让人感到惊讶。特拉维斯-威尔和朱利安-雅各布斯在季前赛中的出场时间非常有限，而且表现并不突出。</p> \n" +
                " <p>　　扎克-奥古斯特代表湖人队参加了夏季联赛，但是他在季前赛中没有获得任何出场时间。</p> \n" +
                " <p>　　和特拉维斯-威尔与朱利安-雅各布斯的非保障合同不同，奥古斯特的合同是小部分受保障，这使得很多人相信奥古斯特最终可能会加盟湖人队的下属发展联盟球队。</p> \n" +
                " <p>　　随着以上这3名球员被裁，湖人队目前的阵容里还剩下17名球员，也就是说，湖人队在常规赛揭幕战开始之前还得裁掉2名球员。</p> \n" +
                " <p>　　这17名球员里面有14名球员拥有保障合同，剩下的3名球员分别是：易建联（受保障的合同仅为25万美元）、慈世平（无保障合同）和托马斯-罗宾逊（无保障合同）。这就意味着阿联将和慈世平以及罗宾逊争夺湖人队的最后一个常规赛席位。</p> \n" +
                " <p>　　（罗森）</p> \n" +
                " <div style=\"font-size: 0px; height: 0px; clear: both;\"></div> \n" +
                " <!-- {独家提供}--> \n" ;

    }

    /**
     * 测试从数据库里面拿到的爬虫数据经过字段处理之后得到的xml
     * 是新闻推送前的准数据，在推送前务必进行测试
     */
    @Test
    public void testCrawlerDataEntityXml()  {
        String tid = "0dd8ed34025af01999f135e919b67855";
        try {
            List<String> list = crawlerDataEntityService.crawlerDataEntityXml(10,tid);
            for (String s : list) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetCropFieldValue() {
        String taskId = "c79d53bc8900557469ca2fd010980d60";

        //加载字段精确裁剪规则croper
        FieldCroperEntity croper = fieldCroperEntityDao.findById(taskId);
        JSONObject cropObject = (JSONObject) JSON.parse(croper.getCropRule());
        //不同字段的名称前缀信息拼接字段
        List<String> fieldString = new ArrayList<>();
        for(String key : cropObject.keySet())   {
            JSONObject ruleObject = (JSONObject)JSON.parse(cropObject.getString(key));
            fieldString.add(ruleObject.getString("preffix"));
        }

//        String pasedData = "{\"content\":\"<div class=\\\"content\\\"> \\n <p align=\\\"center\\\"><img style=\\\"WIDTH: 800px; HEIGHT: 536px\\\" id=\\\"{216177AD-BD39-4247-ADA4-ECE80B10080B}\\\" title=\\\"\\\" border=\\\"0\\\" align=\\\"center\\\" src=\\\"1119954709_14797048903411n.jpg\\\" sourcedescription=\\\"编辑提供的本地文件\\\" sourcename=\\\"本地文件\\\"></p> \\n <p>&nbsp;&nbsp;&nbsp;&nbsp;贵州省盘县石桥镇妥乐村被誉为“世界古银杏之乡”，妥乐村拥有古银杏1200余株，胸径一般在1米左右，最大达到2.2米，树干高达几十米，树龄最长者为1000余年。初冬季节，整个妥乐村被银杏树群装点得“金彩”夺目。新华网 卢志佳 摄</p> \\n <div width=\\\"100%\\\"> \\n  <p> </p> \\n  <center> \\n   <div id=\\\"div_page_roll1\\\" style=\\\"display:none\\\">\\n     &nbsp;&nbsp; \\n    <span>1</span> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_2.htm\\\">2</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_3.htm\\\">3</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_4.htm\\\">4</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_5.htm\\\">5</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_6.htm\\\">6</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_7.htm\\\">7</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_8.htm\\\">8</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_2.htm\\\">下一页</a>&nbsp;&nbsp; \\n   </div> \\n   <div id=\\\"div_currpage\\\">\\n     &nbsp;&nbsp; \\n    <span>1</span> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_2.htm\\\">2</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_3.htm\\\">3</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_4.htm\\\">4</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_5.htm\\\">5</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_6.htm\\\">6</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_7.htm\\\">7</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_8.htm\\\">8</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_2.htm\\\">下一页</a>&nbsp;&nbsp; \\n   </div> \\n  </center> \\n  <p></p> \\n </div> \\n <script language=\\\"javascript\\\">function turnpage(page){  document.all(\\\"div_currpage\\\").innerHTML = document.all(\\\"div_page_roll\\\"+page).innerHTML;}</script> \\n <script language=\\\"javascript\\\">function turnpage(page){  document.all(\\\"div_currpage\\\").innerHTML = document.all(\\\"div_page_roll\\\"+page).innerHTML;}</script> \\n</div>\",\"author\":\"[责任编辑: 曾鹏] \",\"title\":\" 【“飞阅”中国】航拍世界古银杏之乡 ——贵州妥乐\",\"sourceName\":\" 新华网\",\"tag\":{\"column\":\"新华网贵州频道- 本网专稿\"}}";
//        String pasedData = "{\"content\":\"<div class=\\\"g-content-c\\\"> \\n <!--enpproperty <articleid>5364886</articleid><date>2016-10-31 09:28:27.0</date><author>郭奋</author><title>茅台三季报：持续两位数增长茅台现象倍受关注</title><keyword>方正证券,茅台王子酒,三季报,两位,招商证券</keyword><subtitle></subtitle><introtitle></introtitle><siteid>2</siteid><nodeid>423</nodeid><nodename>本地时政</nodename><nodesearchname></nodesearchname><picurl></picurl>/enpproperty--> \\n <!--enpcontent--> \\n <!--enpcontent--> \\n <p> 　　10月28日晚，贵州茅台发布三季报，2016年前三季度累计实现营业收入266亿元，同比增长15%；实现净利润125亿元，同比增长9%；营收和净利延续两位数增长势头，业绩喜人。与营收和净利相比，其他指标更为抢眼，经营性现金流325亿，同比大增185%；账面货币资金620亿元，同比大增103%；预收账款174亿元，同比大增210%，这三项指标均创历史新高，显示出充沛的现金流。</p> \\n <p> 　　总体来看，白酒行业目前仍处于调整期，经济下行压力依然巨大，贵州茅台抢抓大众消费升级趋势，强化品牌宣传，成功实现转型，经营指标明显好于行业，呈现出“企业发展、经销商盈利、消费者受益、股东分享成长”的多方共赢局面，在白酒行业呈现了独具一格的“茅台现象”，被业内外人士广泛关注。</p> \\n <p>　　大众消费升级，茅台成功转型</p> \\n <p> 　　白酒行业自2013年深度调整以来，以茅台、五粮液等为代表的名优白酒销量持续增长，价格回升趋势明显。据有关机构统计，自2011年至2015年，居民可支配收入增长79.5%，存款增加量增长101.7%，但茅台终端价格下降约50%。在“少喝酒、喝好酒、喝健康的酒”的消费理念带动下，大众消费的热情被点燃，客户覆盖更为广泛，研究机构数据显示目前公务消费已不足1%。茅台在1000元左右的终端零售价格，对于走亲访友、宴请宾客、结婚生子等场合，的确是更好的选择。与往年相较，茅台2016年整体呈现“淡季不淡、旺季更火”的特点。</p> \\n <p>　　预收账款创新高，渠道商信心大增</p> \\n <p> 　　伴随茅台酒价格回升和产销两旺的势头，截止三季度末，贵州茅台预收账款达174亿元，同比增长210.4%，创上市以来的历史新高，说明渠道经销商库存不足以应对即将到来的消费旺季，并普遍看好年底的茅台销售，积极踊跃打款，这是2013年以来未曾出现过的情况。</p> \\n <p> 　　经销商的积极性既来自于对后期市场的看好，也来自于自身盈利状况的大幅改善。贵州茅台在关注消费者利益的同时，也很关注经销商盈利状况。从去年底开始，茅台进一步加大品牌宣传力度，对经销商增加各种支持，引领大众消费向茅台倾斜，在终端取得良好效果。今年以来，茅台经销商的盈利状况改善，经销商普遍反应，“茅台经销商的好日子又要来了”，直爽淳朴的言语表达出对茅台未来发展的信心。</p> \\n <p>　　系列酒表现抢眼，渐成新增长极</p> \\n <p> 　　茅台系列酒由来已久，从最早的茅台王子酒、茅台迎宾酒，到“一曲三茅四酱”，产品体系不断丰富，但销售规模始终起色不大。2016年茅台在系列酒方面将原来的“一曲三茅四酱”品牌实施战略调整为全面践行“133”品牌战略中的“33”战略，即把“茅台王子酒、茅台迎宾酒、赖茅酒”打造成全国市场知名品牌，把“汉酱酒、仁酒、贵州大曲”打造成区域市场重点品牌；将王茅、华茅两个品牌作为战略储备。今年以来，茅台加强队伍建设，调整市场策略，狠抓市场营销工作，对系列酒销售体系给予更大的主动权，实现系列酒快速发展，前三季度茅台系列酒实现销售收入14.6亿元，同比增长67%。</p> \\n <p> 　　行业研究机构普遍认为，白酒行业目前在总量上已经趋于饱和，但品牌集中度仍然偏低，在消费升级和品牌消费趋势下，消费者会向优质、健康、品牌化的产品集中。这是茅台发展系列酒的有利背景，也是培养新增长极的重要机遇。今年以来，茅台实施的茅台酒与系列酒“两轮驱动”战略成效得以显现，未来发展空间被进一步打开。</p> \\n <p>　　看多茅台，机构持续加仓</p> \\n <p> 　　基金保险等投资机构的嗅觉最为灵敏，早在2015年二季度，全国社保基金等国内顶尖机构便进入茅台前十大股东名单，海外投资者关注时间更早，自2014年底沪港通开通以来，海外投资者持续买入，目前已经位列茅台第二大股东，新加坡政府投资公司（GIC PRIVATE LIMITED）也名列前十大机构，从最新的季报看，国内外投资机构依然对贵州茅台信心十足，青睐有加。</p> \\n <p> 　　证券研究机构也写了较多研究报告，仅今年8月以来就有近30篇研究报告分析贵州茅台的投资价值，力荐机构继续买入。其中，招商证券在8月份最新研报《预收首破百亿，确定性买入》中提到，“名酒继续复苏，优质资产重估，茅台值得给予高估值…年内目标价350元…确定性买入”，方正证券的报告《贵州茅台：白酒行业的定海神针》中提到，“需求的强劲支撑茅台未来很长一段时间保持两位数的强劲增长，供需紧张的局面会持续出现”。</p> \\n <p> 　　近期召开的秋季糖酒会上，众多业内人士普遍认为，白酒行业仍然处于弱复苏的周期中，经济形势低迷和消费需求疲软给行业复苏带来更多不确定性，但茅台强化品牌宣传，定位大众消费，成功实现了名酒到民酒的定位转换，呈现出企业、经销商、消费者和股东多方共赢的“茅台现象”，也为行业和资本市场所瞩目。年底茅台是否会交出更加靓丽的答卷，市场拭目以待。（郭奋）</p> \\n <!--/enpcontent--> \\n <!--/enpcontent--> \\n</div>\",\"author\":\"责任编辑：胡丽涓\",\"title\":\"茅台三季报：持续两位数增长茅台现象倍受关注\",\"publishTime\":\"发布时间：2016-10-31 09:28:27\",\"sourceName\":\"来源：贵州都市报\",\"tag\":{\"column\":\"贵阳网-本地经济\"}}";
//        String pasedData = "{\"content\":\"<p style=\\\"text-indent: 2em;\\\"> 凡事，一旦处于混乱的状态下，就易出现各种名词泛滥的情况――美其名曰“百家争鸣”。当今绘画领域，画家们（包括著名的画家）几乎不再用自己的画来说话，而是用“话”“大话”“废话”来说自己的画了。这，抑或是当下最为盛行的画家行为准则――笔下功夫欠缺，嘴上功夫了得，结果只能是“画不够，话来凑”。</p><p style=\\\"text-indent: 2em;\\\"> 在我看来，对“书画同源”“画者书之余”“书法是绘画的基础”“画法全是书法”等说法的无休止的研讨，就好比是在讨论做菜。在菜的品种和质量都不明确的情况下，却去探讨佐料该如何添加，显然有些本末倒置。我不明白，这究竟是要客人品尝菜肴呢，还是要客人品尝佐料？</p><p style=\\\"text-indent: 2em;\\\"> 审视时下，满眼都是那些开口闭口大肆强调“书画同源”的画家们的画，却几乎看不到他们其中哪一位能把毛笔字（已经不够格用“书法”二字）写好。我将这些画家们的字归纳为“三异”，即变异、怪异、诡异。如此，其“书画同源”“画法全是书法”也就可想而知了。正因为这一现象的盛行，我发现一个特别古怪且令人啼笑皆非的现实：越是没有文化的人越是喜欢大讲、特讲国学，越是毛笔字写得不好的画家越是喜欢鼓吹“书画同源”，越是文辞不通的人越是喜欢在画作上长跋转文。</p><p style=\\\"text-indent: 2em;\\\"> 其实，“画法全是书法”只是绘画理论的其中一种。确切地说，是文人画的信条。我们不能将其视为整个中国绘画的唯一路径。要言之，绘画的产生是早于书法的；而汉字由象形美渐渐走向笔画美，也是基于绘画（图画）对物象形态表现美的启发。这样看来，谁是谁的基础呢？再言之，中国绘画最鼎盛、最辉煌的时期是北宋，而此时的李成、范宽、许道宁、郭熙、燕文贵、王诜、郭忠恕、王希孟等这些开宗立派的巨匠，有谁开口闭口“画法即书法”？米芾是北宋首屈一指的大书法家。他也画画，而且还自创了“米家山水”。他不是也没有强调“画法即书法”吗？</p><p style=\\\"text-indent: 2em;\\\"> 中国绘画应该是多层面的，因为在“书画同源”理论提出之前，先贤们就创造了丰富多彩的绘画形式和表现手法。我们应该明白，书画虽同源但并不同理。继承和发扬，不能仅限于“书画同源”的文人画，那样会把中国绘画推向狭窄的死胡同。</p><p style=\\\"text-indent: 2em;\\\"> 来源：选自2016年第83期《中国书画报》&nbsp; &nbsp;&nbsp;</p>\",\"author\":\" (责编：鲁婧、董子龙) \",\"title\":\"别让书画同源蒙蔽了双眼\",\"publishTime\":\" 2016年11月29日08:46\\u00A0\\u00A0来源：  人民网-书画频道\",\"sourceName\":\" 2016年11月29日08:46\\u00A0\\u00A0来源：  人民网-书画频道\",\"tag\":{\"column\":\"人民网书画-书法资讯\"}}";
        String pasedData ="{\"content\":\"<p>　　据悉，贵州省第十三届旅游产业发展大会将于2018年6月在黔东南州召开，为确保重点旅游项目加快推进，天柱县委、县人民政府成立了第十三届贵州省旅发大会天柱县旅游项目建设六大指挥部：1、金凤山景区项目建设指挥部;2、凤园景区项目建设指挥部;3、三十里水景长廊项目建设指挥部;4、远口特色小城镇建设指挥部;5、三门塘景区建设指挥部;6、天柱-远口-三门塘道路建设指挥部。三门塘这颗清水江边上璀璨的侗寨明珠将被揭开它的面纱、光芒四射、美丽动人。</p><p>　　为加快推进三门塘重点旅游项目建设，12月3日下午，在坌处新集镇三门塘景区项目建设指挥部正式挂牌成立。</p><p>　　据三门塘总体规划设计单位广州博厦建筑设计研究院杨杰介绍，三门塘将打造成为独具地域特色的旅游景区，依山傍水、高档休闲的度假胜地，全景区打造面积为 60多公顷，整个景区的设计围绕三门塘木商文化，水上设计、木商和青石板设计为景区亮点。设有水上浮桥、水上会务中心、水上客栈、水榭歌台等水上娱乐设施。</p><p style=\\\"text-align: center;\\\"><img src=\\\"http://www.qdnrbs.cn/u/cms/www/201612/05092646l75a.jpg\\\" title=\\\"敬酒.jpg\\\" alt=\\\"敬酒.jpg\\\"></p>\",\"author\":\" 发布时间： 2016-12-05 \\u00A0 作者： 欧阳小珍 \\u00A0 来源 ：黔东南新闻网 编辑：廖俊超  \",\"title\":\"天柱：侗寨三门塘迎来全新的面貌\",\"publishTime\":\" 发布时间： 2016-12-05 \\u00A0 作者： 欧阳小珍 \\u00A0 来源 ：黔东南新闻网 编辑：廖俊超  \",\"sourceName\":\" 发布时间： 2016-12-05 \\u00A0 作者： 欧阳小珍 \\u00A0 来源 ：黔东南新闻网 编辑：廖俊超  \",\"tag\":{\"column\":\"黔东南新闻网-金色天柱新闻专题\"}}";
        JSONObject jsonObject = JSONObject.parseObject(pasedData);
        System.out.println(JSON.toJSONString(croper));
        System.out.println("sourceName:" +crawlerDataEntityService.getCropFieldValue("sourceName", croper, jsonObject, fieldString));
    }
}
