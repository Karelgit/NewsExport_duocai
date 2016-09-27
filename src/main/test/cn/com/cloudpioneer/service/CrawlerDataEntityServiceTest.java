package cn.com.cloudpioneer.service;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <类详细说明>
 *
 * @Author： Huanghai
 * @Version: 2016-09-27
 **/
public class CrawlerDataEntityServiceTest {
    CrawlerDataEntityService crawlerDataEntityService = new CrawlerDataEntityService();

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
}
