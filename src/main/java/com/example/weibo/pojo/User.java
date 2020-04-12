package com.example.weibo.pojo;

import org.springframework.stereotype.Component;

/**
 * @program: wespirder-charterone
 * @description:
 * @author: huang fangfan
 * @create: 2020-03-11 16:53
 */
@Component
public class User {
    String uId;
    String  uName;
    String uLink;
    int uFollow;
    long uFansNumber;
    String uFollowPage;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuLink() {
        return uLink;
    }

    public void setuLink(String uLink) {
        this.uLink = uLink;
    }

    public int getuFollow() {
        return uFollow;
    }

    public void setuFollow(int uFollow) {
        this.uFollow = uFollow;
    }

    public long getuFansNumber() {
        return uFansNumber;
    }

    public void setuFansNumber(long uFansNumber) {
        this.uFansNumber = uFansNumber;
    }

    public String getuFollowPage() {
        return uFollowPage;
    }

    public void setuFollowPage(String uFollowPage) {
        this.uFollowPage = uFollowPage;
    }
}
