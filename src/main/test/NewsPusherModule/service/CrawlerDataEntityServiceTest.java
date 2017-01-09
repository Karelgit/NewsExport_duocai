package NewsPusherModule.service;

import NewsPusherModule.ApplicationNewsExport;
import NewsPusherModule.controller.ExportDuocaiController;
import NewsPusherModule.entity.Article;
import NewsPusherModule.entity.DuocaiInfo;
import NewsPusherModule.dao.CrawlerDataEntityDao;
import NewsPusherModule.dao.FieldCroperEntityDao;
import NewsPusherModule.entity.CrawlerDataEntity;
import NewsPusherModule.entity.FieldCroperEntity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private CrawlerDataEntityDao crawlerDataEntityDao;
    @Autowired
    private ExportDuocaiController exportDuocaiController;

    @Test
    public void testRegex() {

    }

    @Test
    public void testRemoveAdd(){
        Article article = new Article();
        article.setTitle("贵州将打造肉牛养殖深加工综合产业园 3年引进10万头青年母牛");
        article.setContent("多彩贵州网讯(本网记者 杨昌鼎)12月22日，贵州省农业委员会与中合三农集团有限公司签订框架协议，将在贵州省建设1—3个大型肉牛养殖、深加工综合产业园区，300个肉牛养殖单元(合作社)，共同携手推动贵州畜牧产业发展。");
        article.setAuthor("杨昌鼎");
        article.setSourceName("多彩贵州网");
        Article article1 = new Article();
        article1.setTitle("贵州将打造肉牛养殖深加工综合产业园 ");
        article1.setContent("多彩贵州网讯(本网记者 杨昌鼎)12月22日，共同携手推动贵州畜牧产业发展。");
        article1.setAuthor("杨昌鼎");
        article1.setSourceName("多彩贵州网");
        List<Article> list = new ArrayList<>();
        list.add(article);
        list.add(article1);
        DuocaiInfo duocaiInfo = new DuocaiInfo();
        //duocaiInfo.setChannelId("3000000000000000");
        duocaiInfo.setInitEditor("100000125");
        duocaiInfo.setPassword("1234567;");
        duocaiInfo.setUserName("zhangcao111");

        Map<String,Object> map = new HashMap<>();
        map.put("duocaiInfo",duocaiInfo);
        map.put("articles",list);
        String json = JSON.toJSONString(map);
        System.out.println(json);

    }

    /**
     * 测试从数据库里面拿到的爬虫数据经过字段处理之后得到的xml
     * 是新闻推送前的准数据，在推送前务必进行测试
     */
    @Test
    public void testCrawlerDataEntityXml()  {
        String tid = "5acc3ad0defcbcd62166e80d688e3605";
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
        String taskId = "088e8ba36ec0000e85549f239946cb80";
        List<CrawlerDataEntity> crawlerDataEntities = crawlerDataEntityDao.findByPage(0,1,taskId);
        String parsedData = crawlerDataEntities.get(0).getParsedData();

        //加载字段精确裁剪规则croper
        FieldCroperEntity croper = fieldCroperEntityDao.findById(taskId);
        JSONObject cropObject = (JSONObject) JSON.parse(croper.getCropRule());
        //不同字段的名称前缀信息拼接字段
        List<String> fieldString = new ArrayList<>();
        for(String key : cropObject.keySet())   {
            JSONObject ruleObject = (JSONObject)JSON.parse(cropObject.getString(key));
            fieldString.add(ruleObject.getString("preffix"));
        }

////        String parsedData = "{\"content\":\"<div class=\\\"content\\\"> \\n <p align=\\\"center\\\"><img style=\\\"WIDTH: 800px; HEIGHT: 536px\\\" id=\\\"{216177AD-BD39-4247-ADA4-ECE80B10080B}\\\" title=\\\"\\\" border=\\\"0\\\" align=\\\"center\\\" src=\\\"1119954709_14797048903411n.jpg\\\" sourcedescription=\\\"编辑提供的本地文件\\\" sourcename=\\\"本地文件\\\"></p> \\n <p>&nbsp;&nbsp;&nbsp;&nbsp;贵州省盘县石桥镇妥乐村被誉为“世界古银杏之乡”，妥乐村拥有古银杏1200余株，胸径一般在1米左右，最大达到2.2米，树干高达几十米，树龄最长者为1000余年。初冬季节，整个妥乐村被银杏树群装点得“金彩”夺目。新华网 卢志佳 摄</p> \\n <div width=\\\"100%\\\"> \\n  <p> </p> \\n  <center> \\n   <div id=\\\"div_page_roll1\\\" style=\\\"display:none\\\">\\n     &nbsp;&nbsp; \\n    <span>1</span> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_2.htm\\\">2</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_3.htm\\\">3</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_4.htm\\\">4</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_5.htm\\\">5</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_6.htm\\\">6</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_7.htm\\\">7</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_8.htm\\\">8</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_2.htm\\\">下一页</a>&nbsp;&nbsp; \\n   </div> \\n   <div id=\\\"div_currpage\\\">\\n     &nbsp;&nbsp; \\n    <span>1</span> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_2.htm\\\">2</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_3.htm\\\">3</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_4.htm\\\">4</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_5.htm\\\">5</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_6.htm\\\">6</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_7.htm\\\">7</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_8.htm\\\">8</a> \\n    <a href=\\\"http://www.gz.xinhuanet.com/2016-11/21/c_1119954709_2.htm\\\">下一页</a>&nbsp;&nbsp; \\n   </div> \\n  </center> \\n  <p></p> \\n </div> \\n <script language=\\\"javascript\\\">function turnpage(page){  document.all(\\\"div_currpage\\\").innerHTML = document.all(\\\"div_page_roll\\\"+page).innerHTML;}</script> \\n <script language=\\\"javascript\\\">function turnpage(page){  document.all(\\\"div_currpage\\\").innerHTML = document.all(\\\"div_page_roll\\\"+page).innerHTML;}</script> \\n</div>\",\"author\":\"[责任编辑: 曾鹏] \",\"title\":\" 【“飞阅”中国】航拍世界古银杏之乡 ——贵州妥乐\",\"sourceName\":\" 新华网\",\"tag\":{\"column\":\"新华网贵州频道- 本网专稿\"}}";
////        String parsedData = "{\"content\":\"<div class=\\\"g-content-c\\\"> \\n <!--enpproperty <articleid>5364886</articleid><date>2016-10-31 09:28:27.0</date><author>郭奋</author><title>茅台三季报：持续两位数增长茅台现象倍受关注</title><keyword>方正证券,茅台王子酒,三季报,两位,招商证券</keyword><subtitle></subtitle><introtitle></introtitle><siteid>2</siteid><nodeid>423</nodeid><nodename>本地时政</nodename><nodesearchname></nodesearchname><picurl></picurl>/enpproperty--> \\n <!--enpcontent--> \\n <!--enpcontent--> \\n <p> 　　10月28日晚，贵州茅台发布三季报，2016年前三季度累计实现营业收入266亿元，同比增长15%；实现净利润125亿元，同比增长9%；营收和净利延续两位数增长势头，业绩喜人。与营收和净利相比，其他指标更为抢眼，经营性现金流325亿，同比大增185%；账面货币资金620亿元，同比大增103%；预收账款174亿元，同比大增210%，这三项指标均创历史新高，显示出充沛的现金流。</p> \\n <p> 　　总体来看，白酒行业目前仍处于调整期，经济下行压力依然巨大，贵州茅台抢抓大众消费升级趋势，强化品牌宣传，成功实现转型，经营指标明显好于行业，呈现出“企业发展、经销商盈利、消费者受益、股东分享成长”的多方共赢局面，在白酒行业呈现了独具一格的“茅台现象”，被业内外人士广泛关注。</p> \\n <p>　　大众消费升级，茅台成功转型</p> \\n <p> 　　白酒行业自2013年深度调整以来，以茅台、五粮液等为代表的名优白酒销量持续增长，价格回升趋势明显。据有关机构统计，自2011年至2015年，居民可支配收入增长79.5%，存款增加量增长101.7%，但茅台终端价格下降约50%。在“少喝酒、喝好酒、喝健康的酒”的消费理念带动下，大众消费的热情被点燃，客户覆盖更为广泛，研究机构数据显示目前公务消费已不足1%。茅台在1000元左右的终端零售价格，对于走亲访友、宴请宾客、结婚生子等场合，的确是更好的选择。与往年相较，茅台2016年整体呈现“淡季不淡、旺季更火”的特点。</p> \\n <p>　　预收账款创新高，渠道商信心大增</p> \\n <p> 　　伴随茅台酒价格回升和产销两旺的势头，截止三季度末，贵州茅台预收账款达174亿元，同比增长210.4%，创上市以来的历史新高，说明渠道经销商库存不足以应对即将到来的消费旺季，并普遍看好年底的茅台销售，积极踊跃打款，这是2013年以来未曾出现过的情况。</p> \\n <p> 　　经销商的积极性既来自于对后期市场的看好，也来自于自身盈利状况的大幅改善。贵州茅台在关注消费者利益的同时，也很关注经销商盈利状况。从去年底开始，茅台进一步加大品牌宣传力度，对经销商增加各种支持，引领大众消费向茅台倾斜，在终端取得良好效果。今年以来，茅台经销商的盈利状况改善，经销商普遍反应，“茅台经销商的好日子又要来了”，直爽淳朴的言语表达出对茅台未来发展的信心。</p> \\n <p>　　系列酒表现抢眼，渐成新增长极</p> \\n <p> 　　茅台系列酒由来已久，从最早的茅台王子酒、茅台迎宾酒，到“一曲三茅四酱”，产品体系不断丰富，但销售规模始终起色不大。2016年茅台在系列酒方面将原来的“一曲三茅四酱”品牌实施战略调整为全面践行“133”品牌战略中的“33”战略，即把“茅台王子酒、茅台迎宾酒、赖茅酒”打造成全国市场知名品牌，把“汉酱酒、仁酒、贵州大曲”打造成区域市场重点品牌；将王茅、华茅两个品牌作为战略储备。今年以来，茅台加强队伍建设，调整市场策略，狠抓市场营销工作，对系列酒销售体系给予更大的主动权，实现系列酒快速发展，前三季度茅台系列酒实现销售收入14.6亿元，同比增长67%。</p> \\n <p> 　　行业研究机构普遍认为，白酒行业目前在总量上已经趋于饱和，但品牌集中度仍然偏低，在消费升级和品牌消费趋势下，消费者会向优质、健康、品牌化的产品集中。这是茅台发展系列酒的有利背景，也是培养新增长极的重要机遇。今年以来，茅台实施的茅台酒与系列酒“两轮驱动”战略成效得以显现，未来发展空间被进一步打开。</p> \\n <p>　　看多茅台，机构持续加仓</p> \\n <p> 　　基金保险等投资机构的嗅觉最为灵敏，早在2015年二季度，全国社保基金等国内顶尖机构便进入茅台前十大股东名单，海外投资者关注时间更早，自2014年底沪港通开通以来，海外投资者持续买入，目前已经位列茅台第二大股东，新加坡政府投资公司（GIC PRIVATE LIMITED）也名列前十大机构，从最新的季报看，国内外投资机构依然对贵州茅台信心十足，青睐有加。</p> \\n <p> 　　证券研究机构也写了较多研究报告，仅今年8月以来就有近30篇研究报告分析贵州茅台的投资价值，力荐机构继续买入。其中，招商证券在8月份最新研报《预收首破百亿，确定性买入》中提到，“名酒继续复苏，优质资产重估，茅台值得给予高估值…年内目标价350元…确定性买入”，方正证券的报告《贵州茅台：白酒行业的定海神针》中提到，“需求的强劲支撑茅台未来很长一段时间保持两位数的强劲增长，供需紧张的局面会持续出现”。</p> \\n <p> 　　近期召开的秋季糖酒会上，众多业内人士普遍认为，白酒行业仍然处于弱复苏的周期中，经济形势低迷和消费需求疲软给行业复苏带来更多不确定性，但茅台强化品牌宣传，定位大众消费，成功实现了名酒到民酒的定位转换，呈现出企业、经销商、消费者和股东多方共赢的“茅台现象”，也为行业和资本市场所瞩目。年底茅台是否会交出更加靓丽的答卷，市场拭目以待。（郭奋）</p> \\n <!--/enpcontent--> \\n <!--/enpcontent--> \\n</div>\",\"author\":\"责任编辑：胡丽涓\",\"title\":\"茅台三季报：持续两位数增长茅台现象倍受关注\",\"publishTime\":\"发布时间：2016-10-31 09:28:27\",\"sourceName\":\"来源：贵州都市报\",\"tag\":{\"column\":\"贵阳网-本地经济\"}}";
////        String parsedData = "{\"content\":\"<p style=\\\"text-indent: 2em;\\\"> 凡事，一旦处于混乱的状态下，就易出现各种名词泛滥的情况――美其名曰“百家争鸣”。当今绘画领域，画家们（包括著名的画家）几乎不再用自己的画来说话，而是用“话”“大话”“废话”来说自己的画了。这，抑或是当下最为盛行的画家行为准则――笔下功夫欠缺，嘴上功夫了得，结果只能是“画不够，话来凑”。</p><p style=\\\"text-indent: 2em;\\\"> 在我看来，对“书画同源”“画者书之余”“书法是绘画的基础”“画法全是书法”等说法的无休止的研讨，就好比是在讨论做菜。在菜的品种和质量都不明确的情况下，却去探讨佐料该如何添加，显然有些本末倒置。我不明白，这究竟是要客人品尝菜肴呢，还是要客人品尝佐料？</p><p style=\\\"text-indent: 2em;\\\"> 审视时下，满眼都是那些开口闭口大肆强调“书画同源”的画家们的画，却几乎看不到他们其中哪一位能把毛笔字（已经不够格用“书法”二字）写好。我将这些画家们的字归纳为“三异”，即变异、怪异、诡异。如此，其“书画同源”“画法全是书法”也就可想而知了。正因为这一现象的盛行，我发现一个特别古怪且令人啼笑皆非的现实：越是没有文化的人越是喜欢大讲、特讲国学，越是毛笔字写得不好的画家越是喜欢鼓吹“书画同源”，越是文辞不通的人越是喜欢在画作上长跋转文。</p><p style=\\\"text-indent: 2em;\\\"> 其实，“画法全是书法”只是绘画理论的其中一种。确切地说，是文人画的信条。我们不能将其视为整个中国绘画的唯一路径。要言之，绘画的产生是早于书法的；而汉字由象形美渐渐走向笔画美，也是基于绘画（图画）对物象形态表现美的启发。这样看来，谁是谁的基础呢？再言之，中国绘画最鼎盛、最辉煌的时期是北宋，而此时的李成、范宽、许道宁、郭熙、燕文贵、王诜、郭忠恕、王希孟等这些开宗立派的巨匠，有谁开口闭口“画法即书法”？米芾是北宋首屈一指的大书法家。他也画画，而且还自创了“米家山水”。他不是也没有强调“画法即书法”吗？</p><p style=\\\"text-indent: 2em;\\\"> 中国绘画应该是多层面的，因为在“书画同源”理论提出之前，先贤们就创造了丰富多彩的绘画形式和表现手法。我们应该明白，书画虽同源但并不同理。继承和发扬，不能仅限于“书画同源”的文人画，那样会把中国绘画推向狭窄的死胡同。</p><p style=\\\"text-indent: 2em;\\\"> 来源：选自2016年第83期《中国书画报》&nbsp; &nbsp;&nbsp;</p>\",\"author\":\" (责编：鲁婧、董子龙) \",\"title\":\"别让书画同源蒙蔽了双眼\",\"publishTime\":\" 2016年11月29日08:46\\u00A0\\u00A0来源：  人民网-书画频道\",\"sourceName\":\" 2016年11月29日08:46\\u00A0\\u00A0来源：  人民网-书画频道\",\"tag\":{\"column\":\"人民网书画-书法资讯\"}}";
////        String parsedData ="{\"content\":\"<p>　　据悉，贵州省第十三届旅游产业发展大会将于2018年6月在黔东
//        String parsedData ="{\"content\":\"<p>　<strong>　<a href=\\\"http://www.qdnzx.com.cn/\\\" target=\\\"_blank\\\">黔东南新闻网</a>讯</strong> &nbsp;近日，天柱县30里水景民族风情文化长廊举行开工仪式，标志着该项目正式开工建设。</p><p>　　据了解，该项目总投资31亿元，由三个分项目组成，采取政府投资和社会资本PPP模式合作推进实施，项目沿线涉及3个街道8个社区5万余人，覆盖区域5249亩，建设工期为两年半。项目建设包括鉴江、朗江小流域综合治理，配套商业及污水处理三个项目及慢行系统、民俗民居、景观打造、河道治理、宗祠文化展示等5大板块，建设内容涵盖文化艺术长廊区、朗江休闲度假区、云龙旅游观光园、宗祠文化展示区、休闲垂钓区、健康养生度假区、婚纱摄影基地、四季花海、会展中心等32个旅游景点，项目建成后3-5年将达到国家5A级旅游景区标准，年接待游客将超过40万人次，预估10年运营期总收益将超过38亿元，利润23亿元;15年运营期总收益达69亿元，利润达36亿元，新增就业岗位1万个以上，受益人群达11万人。</p><p>　　该项目是天柱县打造山水田园城市，实现天蓝、地绿、水净、安居、乐业、增收目标的重要举措。</p><p><br></p>\",\"author\":\" 发布时间： 2016-12-06 \\u00A0 作者： 欧阳海波 \\u00A0 来源 ：黔东南新闻网 编辑：曹杨军  \",\"title\":\"天柱县30里水景民族风情文化长廊开工建设\",\"publishTime\":\" 发布时间： 2016-12-06 \\u00A0 作者： 欧阳海波 \\u00A0 来源 ：黔东南新闻网 编辑：曹杨军  \",\"sourceName\":\" 发布时间： 2016-12-06 \\u00A0 作者： 欧阳海波 \\u00A0 来源 ：黔东南新闻网 编辑：曹杨军  \",\"tag\":{\"column\":\"黔东南新闻网-金色天柱新闻专题\"}}";
        JSONObject jsonObject = JSONObject.parseObject(parsedData);
//        System.out.println(JSON.toJSONString(croper));
        System.out.println("sourceName:" +crawlerDataEntityService.getCropFieldValue("sourceName", croper, jsonObject, fieldString));
    }

    @Test
    public void testExportNews()    {
        String articles =   "[{\"author\":\"胡丽涓\",\"channelId\":\"3000000000000000\",\"content\":\"    <p> 据新华社电 2017年贵州将重点强化产业精准扶贫，新增10个省级扶贫产业园，实施2万个以上的产业扶贫项目，力争带动60万贫困群众脱贫。</p> <p> 记者8日从贵州省委农村工作会议暨全省扶贫开发工作会议上了解到，为确保2017年贵州产业扶贫取得重大突破，贵州将召开全省产业扶贫招商引资会议，分层次、分批次外出招商；并通过大力发展“一村一品”产业与乡村旅游、强化配套服务、支持农业龙头企业发展、实施政策性农业保险贫困县全覆盖等，多措并举提升产业扶贫的质量与效益。</p> <p> 据了解，2017年，贵州将通过产业扶贫、生态脱贫、易地扶贫搬迁等措施，减贫100万人以上，实现20个贫困县脱贫摘帽，对40万农村青壮年劳动力开展技能培训；向对口帮扶城市有组织输出1万名劳务人员。</p>   \",\"sourceName\":\"贵州都市报\",\"templateId\":\"100000330\",\"title\":\"贵州今年将实施2万个以上产业扶贫项目\"},{\"author\":\"胡丽涓\",\"channelId\":\"3000000000000000\",\"content\":\"    <p align=\\\"center\\\"><img id=\\\"6104383\\\" src=\\\"http://cloudpioneer-test.oss-cn-shanghai.aliyuncs.com/21236056e8a995b6f95c675a7d7aa44f/99fe4c6d9824e38a207810ba9ed2c3af.jpg\\\"> </p> <p class=\\\"pictext\\\" align=\\\"center\\\"> 雷天福躺在床上。<br></p> <p> 本报讯（记者 杜高富摄影报道）“我父亲在贵医附院家属区运输垃圾，将垃圾倒进转运站的压缩垃圾车过程中，司机启动机器，‘咬’碎了他的右手中指。”昨日，雷先生拨打贵州都市报新闻热线96811反映。</p> <p> <strong>倒垃圾手指被“咬”碎</strong></p> <p> 当天中午2点，记者来到贵阳建筑医院见到了雷先生的父亲雷天福，他躺在病床上打着吊针，右手包裹着纱布，雷天福的老婆付成秀守在病床旁边一脸焦虑。</p> <p> 雷天福告诉记者，1月1日上午10点半左右，他在贵医附院家属区清扫垃圾，用手推式垃圾车将垃圾运输到宅吉垃圾转运站。平时，垃圾转运站是用机器将手推式垃圾车内的垃圾倒进压缩式垃圾车箱内。</p> <p> 由于吊垃圾车的机器坏了，于是，就得采用人工将垃圾倒进压缩式垃圾车箱内，这样就容易倒泼出很多垃圾到地上。于是司机便走过来对雷天福和付成秀说，“我们一起用力，把垃圾倒进转运车内。”于是，司机与付成秀负责手推式垃圾车的一边手把，雷天福一人负责另一边手把，三人用力，一下就将该垃圾车连车倒进了转运垃圾车箱内。</p> <p> 此时，司机进入驾驶室，雷天福看到他的垃圾车手把也在转运车内，立即走过去欲把此车拉下来，“唉哟，我的手遭了……”雷天福正在拉手把时，谁知司机此时启动了机器，压缩机运转时把他的右手中指压碎了。</p> <p> 事故发生后，司机和付成秀迅速将雷天福送到贵医附院救治。经医生检查后，建议转到贵阳建筑医院治疗。于是当天就转到了贵阳建筑医院。</p> <p> <strong>医药费管理方与垃圾站方协调中</strong></p> <p> 雷天福说，转院后，环卫用人单位方及开垃圾车的京贵环卫站方都没有交钱，至今已花去了2万多元，还欠下医院9000多元没着落。“我在贵医附院家属区清扫转运垃圾已有18年，眼看今年5月份就退休了，没有想到就出事了。我在垃圾站倒垃圾受伤，肯定是工伤。可是，事情出了，医药费却没有着落，我想不通。”</p> <p> 建筑医院的医生告诉记者，雷天福的右手中指第一关节外被压碎，为尽可能保住手指，当天做断指再植术。养了三天多仍然摇摇欲坠，发现患者中指因碎了，血管不通，养不活。于是，院方将情况详情介绍，经患者方同意，将第一关节及第二关节约一半进行了截肢。</p> <p> 对此事，记者与雷天福的用工单位、贵医附院家属区负责该区环卫管理的刘近平书记联系，刘书记说，雷天福受伤一事，他们一直都管的。由于此事故的责任有一点分歧，患者方称是京贵垃圾站开压缩式垃圾车的司机喊他一起将倒垃圾倒垃圾箱内，而司机则称没有喊患者倒垃圾进转运车箱内，当时没有直接的第三方目击证人，双方因此责任划分暂时还不明确。</p> <p> 刘近平说，尽管如此，他们以人为本，当时他先掏了2000元垫付医药费先救人。就此事，他们一直在努力，1月6日，刘近平还陪同雷天福的儿子雷先生前往用人单位找了相关负责人。目前他们正在与京贵垃圾站方积极协调中。</p>   \",\"sourceName\":\"贵州都市报\",\"templateId\":\"100000330\",\"title\":\"环卫工倒垃圾手指被“咬”碎\"}]";
        String duocaiInfo = "{\"userName\":\"libei001\",\"companyId\":\"001\",\"password\":\"1234567;\",\"initEditor\":\"100000125\"}";
        exportDuocaiController.exportArticles(articles,duocaiInfo);
    }
}
