package com.example.weibo.spirder;

import com.example.weibo.pojo.User;
import com.example.weibo.pojo.WeiboContentInfo;
import com.example.weibo.service.ContentServiceImpl;
import com.example.weibo.service.UserServiceImpl;
import com.github.pagehelper.Page;
import com.vdurmont.emoji.EmojiParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: weibo
 * @description:
 * @author: huang fangfan
 * @create: 2020-04-03 15:27
 */
@Component("handleWeibo")
@MapperScan("com.example.weibo.mapper")
public class HandleWeibo {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private User user;
    @Autowired
    private ThreadTest threadTest;
    @Autowired
    private WeiboContentInfo weiboContentInfo;
    @Autowired
    private ContentServiceImpl contentServiceImpl;
    /**
     * 功能描述:
     * 〈剪切用户地址然后拼接〉
     *
     * @param middle 1
     * @return : java.lang.String
     * @author : huangfangfan
     * @date : 2020/3/28 23:37
     */
    public String linkSlice(String middle) {
        Pattern r = Pattern.compile("^(.+)\\?.*$");
        // 现在创建 matcher 对象
        Matcher m = r.matcher(middle);
        m.find();
        middle = m.group(1);
        String headUrl = "https://weibo.com";
        String footUrl = "?profile_ftype=1&is_ori=1#_0";
        return headUrl + middle + footUrl;
    }
    /**
     * 功能描述:
     * 〈循环从数据库读取UID在插入用户信息〉
     *
     * @param
     * @return : void
     * @author : huangfangfan
     * @date : 2020/3/26 19:05
     */
    public void circleInsertUser() {
        XinlangTest testXL = new XinlangTest("1562262496@qq.com", "hff199766");
        int countId = 0;
        int pageNum = 1;
        String baseUrlOne ="https://weibo.com/" ;
        String baseUrlTwo = "/follow?page=" ;
        String baseUrlThree = "#Pl_Official_HisRelation__59";
        try {
            testXL.preLogin();
            //登录新浪通行证抓包时获取的请求地址
            testXL.login("https://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.15)&_=" + System.currentTimeMillis());
            do {
                countId = userServiceImpl.queryCountId();
                pageNum++;
                Page<String> list = userServiceImpl.queryAll(pageNum,20);
                for (String url: list) {
                    for (int i = 1;i <= 5;i++) {
                        System.out.println(baseUrlOne + url + baseUrlTwo + i + baseUrlThree);
                        scrap(testXL.crossWeibo1(baseUrlOne + url + baseUrlTwo + i + baseUrlThree));
                    }
                    System.out.println("----------------------------------------------------------------");
                }
            } while (countId <= 100000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 功能描述:
     * 〈根据链接插入微博内容〉
     *
     * @param
     * @return : void
     * @author : huangfangfan
     * @date : 2020/4/1 14:56
     */
    public void insertContent() {
        XinlangTest testXL = new XinlangTest("1562262496@qq.com", "hff199766");
        String id = null;
        String name = null;
        String time = null;
        String text = null;
        int forwardNum = 0;
        int commentNum = 0;
        int likeNum = 0;
        Pattern r = Pattern.compile("^(.+)\\&.*$");
        try {
            testXL.preLogin();
            //登录新浪通行证抓包时获取的请求地址
            testXL.login("https://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.15)&_=" + System.currentTimeMillis());
            int countId = userServiceImpl.queryCountId();
            for (int pageNum = 227; pageNum < countId/20; pageNum++) {
                Page<String> userLink = userServiceImpl.queryUserLink(pageNum,20);
                for (String link: userLink) {
                    Document document = Jsoup.parse(testXL.crossWeibo1(linkSlice(link)));
                    Elements elements = document.select(".WB_cardwrap.WB_feed_type.S_bg2");
                    for (Element element : elements) {
                        String allText = EmojiParser.removeAllEmojis(element.select(".WB_text.W_f14").text()).replaceAll("/[\u4E00-\u9FB0]{2}", "");
                        id = element.select(".WB_detail .WB_info a").attr("usercard").substring(3,13);
                        if (id.contains("&")) {
                            Matcher m = r.matcher(id);
                            m.find();
                            id = m.group(1);
                        }
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
                        String like = element.select("span[node-type=\"like_status\"]").select("span em").last().text();
                        if (like == null) {
                            likeNum = 0;
                        }
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
                        contentServiceImpl.addContent(weiboContentInfo);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 功能描述:
     * 〈处理下载的关注页面
     * 〉
     *
     * @param
     * @return : void
     * @author : huangfangfan
     * @date : 2020/3/15 18:06
     */
    public void scrap(String html) {
        Pattern r = Pattern.compile("^(.+)\\&.*$");
        try {
            Document document = Jsoup.parse(html);
            Elements elements = document.select(".mod_info");
            for (Element element:elements) {
                int num = element.select(".info_name span").size();
                if (num > 0){
                }
                else {
                    //  此处应该用正则表达式，不是所有的uid都是十位
                    String uId = element.select(".info_name .S_txt1").attr("usercard").substring(3,13);
                    if (uId.contains("&")) {
                        Matcher m = r.matcher(uId);
                        m.find();
                        uId = m.group(1);
                    }
                    user.setuId(uId);
                    if (userServiceImpl.selecrUser(user) != null) {
                        continue;
                    } else {
                        String uName = element.select(".info_name a").text();
                        user.setuName(uName);
                        String uLink = element.select(".info_name .S_txt1").attr("href");
                        user.setuLink(uLink);
                        String uFollow = element.select(".info_connect .conn_type").first().select(" .count a").text();
                        user.setuFollow(Integer.parseInt(uFollow));
                        String uFansNum = element.select(".info_connect .W_vline").first().select(" .count a").text();
                        user.setuFansNumber(Long.parseLong(uFansNum));
                        System.out.println("id: " + uId + "name" + uName + "link" + uLink + "关注" + uFollow + "粉丝" + uFansNum);
                        userServiceImpl.addUser(user);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
