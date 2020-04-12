package com.example.weibo.pojo;

import org.springframework.stereotype.Component;

/**
 * @program: weibo
 * @description:
 * @author: huang fangfan
 * @create: 2020-04-08 23:15
 */
@Component("topicAbout")
public class TopicAbout {
    int id;
    int fansNumber;
    int forwardNumber;
    int commentNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFansNumber() {
        return fansNumber;
    }

    public void setFansNumber(int fansNumber) {
        this.fansNumber = fansNumber;
    }

    public int getForwardNumber() {
        return forwardNumber;
    }

    public void setForwardNumber(int forwardNumber) {
        this.forwardNumber = forwardNumber;
    }

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }
}
