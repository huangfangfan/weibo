package com.example.weibo.pojo;

import org.springframework.stereotype.Component;

/**
 * @program: weibo
 * @description:
 * @author: huang fangfan
 * @create: 2020-04-01 00:57
 */
@Component
public class WeiboContentInfo {
    int id;
    String uId;
    String  uName;
    String releaseTime;
    String contentText;
    int forwardNum;
    int commentNum;
    int likeNum;
    String keywordsList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public int getForwardNum() {
        return forwardNum;
    }

    public void setForwardNum(int forwardNum) {
        this.forwardNum = forwardNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public String getKeywordsList() {
        return keywordsList;
    }

    public void setKeywordsList(String keywordsList) {
        this.keywordsList = keywordsList;
    }
}
