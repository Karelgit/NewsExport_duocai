package cn.com.cloudpioneer.service;

import org.junit.Test;

import java.io.IOException;
import java.util.List;
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

    @Test
    public void testCrawlerDataEntityXml()  {
        String tid = "2ebb2984228fd024bfac23dbcb375a9e";
        try {
            List<String> list = crawlerDataEntityService.crawlerDataEntityXml(100,tid);
            for (String s : list) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
