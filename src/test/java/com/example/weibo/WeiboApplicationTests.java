package com.example.weibo;


import com.example.weibo.pojo.TopicAbout;
import com.example.weibo.pojo.WeiboContentInfo;
import com.example.weibo.service.ContentServiceImpl;
import com.example.weibo.spirder.*;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.mining.cluster.ClusterAnalyzer;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@SpringBootTest
@MapperScan("com.example.weibo.mapper")
class WeiboApplicationTests {
    @Autowired
    private HandleWeibo handleWeibo;
    @Autowired
    private HanlpHandle hanlpHandle;
    @Autowired
    private ContentServiceImpl contentServiceImpl;
    @Autowired
    private TextPreprocessing textPreprocessing;
    @Autowired
    private WeiboContentInfo weiboContentInfo;
    @Autowired
    private TopicAndDegree topicAndDegree;
    @Autowired
    private TopicAbout topicAbout;
    @Test
    public void test() throws Exception {

           /*List<Set<String>> list =  hanlpHandle.readerTxt();
           for (Set set : list) {
               System.out.println(set);
           }*/
//           System.out.println(topicAndDegree.sortedHotDegree(topicAndDegree.hotDegree()));
//           topicAndDegree.calculateDegree(35414);
        String middle = "id=1234567890&refer";

        Pattern r = Pattern.compile("^(.+)\\&.*$");
        middle = middle.substring(3,13);
        if (middle.contains("&")) {
            Matcher m = r.matcher(middle);
            m.find();
            middle = m.group(1);
        }
        // 现在创建 matcher 对象

        System.out.println(middle);
    }




    @Test
    public void test1()
    {

       /* // 创建Httpclient对象
        HttpHost proxy = new HttpHost("218.87.74.193",65000, "http");
        CredentialsProvider provider = new BasicCredentialsProvider();
        //包含账号密码的代理
        provider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials("cs", "cs"));
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(provider).build();
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Get请求
            HttpGet httpGet = new HttpGet("https://httpbin.org/ip");
            httpGet.setConfig(config);
            // 执行http请求
            response = httpClient.execute(httpGet);
            resultString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            Document doc = Jsoup.parse(resultString);
             System.out.println(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        // 定义线程实现接口
       /* try {
            XinlangTest testXL = new XinlangTest("1562262496@qq.com", "hff199766");
            String id = null;
            String name = null;
            String time = null;
            String text = null;
            int forwardNum = 0;
            int commentNum = 0;
            int likeNum = 0;
            testXL.preLogin();
            testXL.login("https://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.15)&_=" + new Date().getTime());
            Document document = Jsoup.parse(testXL.crossWeibo1("https://weibo.com/qizhelin77?profile_ftype=1&is_ori=1"));
            Elements elements = document.select(".WB_cardwrap.WB_feed_type.S_bg2");
            for (Element element : elements) {
                String allText = EmojiParser.removeAllEmojis(element.select(".WB_text.W_f14").text()).replaceAll("/[\u4E00-\u9FB0]{2}", "");
                id = element.select(".WB_detail .WB_info a").attr("usercard").substring(3,13);
                name = EmojiParser.removeAllEmojis(element.select(".WB_detail .WB_info a").text());
                time = element.select(".WB_detail .WB_from a").attr("title");
                String forward = element.select("span[node-type=\"forward_btn_text\"]").select("span em").last().text();
                String title = null;
                title = element.select(".W_cursor.S_txt2").attr("title");
                String comment = null;
                if (title.contains("由于用户")){
                    comment = "评论";
                } else {
                   comment = element.select("span[node-type=\"comment_btn_text\"]").select("span em").last().text();
                }
//                String comment = element.select("span[node-type=\"comment_btn_text\"]").select("span em").last().text();
                String like = element.select("span[node-type=\"like_status\"]").select("span em").last().text();
                if (forward.contains("转发")) {
                    forwardNum = 0;
                } else if (forward.contains("万")) {
                    forwardNum = 1000000;
                } else {
                    forwardNum = Integer.parseInt(forward);
                }
                if (comment.contains("评论")) {
                    commentNum = 0;
                }  else if (comment.contains("万")) {
                    commentNum = 1000000;
                }else {
                    commentNum = Integer.parseInt(comment);
                }
                if (like.contains("赞")) {
                    likeNum = 0;
                }  else if (like.contains("万")) {
                    likeNum = 1000000;
                }else {
                    likeNum = Integer.parseInt(like);
                }
                if (allText.contains("展开全文")){
                    String url = element.select(".WB_text.W_f14").select("a[action-type=\"fl_unfold\"]").attr("href");
                    String newUrl = "https:" + url;
                    Document document1 = Jsoup.parse(testXL.crossWeibo1(newUrl));
                    Elements elements1 = document1.select(".WB_cardwrap.WB_feed_type.S_bg2");
                    for (Element element1:elements1) {
                        text = EmojiParser.removeAllEmojis(element1.select(".WB_text.W_f14").text()).replaceAll("/[\u4E00-\u9FB0]{2}", "");
                        System.out.println("id" + id);
                        System.out.println("name" + name);
                        System.out.println("time" + time);
                        System.out.println("text" + text);
                        System.out.println("转发数：" + forwardNum);
                        System.out.println("评论数：" + commentNum);
                        System.out.println("点赞数" + likeNum);
                        System.out.println("----------------------------");
                    }
                } else {
                    text = allText;
                    System.out.println("id" + id);
                    System.out.println("name" + name);
                    System.out.println("time" + time);
                    System.out.println("text" + text);
                    System.out.println("转发数：" + forwardNum);
                    System.out.println("评论数：" + commentNum);
                    System.out.println("点赞数" + likeNum);
                    System.out.println("----------------------------");
                }
                weiboContentInfo.setuId(id);
                weiboContentInfo.setuName(name);
                weiboContentInfo.setReleaseTime(time);
                weiboContentInfo.setContentText(text);
                weiboContentInfo.setForwardNum(forwardNum);
                weiboContentInfo.setCommentNum(commentNum);
                weiboContentInfo.setLikeNum(likeNum);
//                contentImplService.addContent(weiboContentInfo);
            }
        } catch (Exception e){
            e.printStackTrace();
        }*/

    }


}
