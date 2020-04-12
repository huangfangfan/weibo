package com.example.weibo.service;

import com.example.weibo.pojo.TopicAbout;
import com.example.weibo.pojo.WeiboContentInfo;
import com.github.pagehelper.Page;

import java.util.List;

public interface ContentService {
    void addContent(WeiboContentInfo weiboContentInfo) throws Exception;
    Page<WeiboContentInfo> queryUserText(int pageNum, int pageSize) throws Exception;
    int queryCountIdFromView() throws Exception;
    void insertKeywords(WeiboContentInfo weiboContentInfo) throws Exception;
    List<WeiboContentInfo> queryByKeyword(WeiboContentInfo weiboContentInfo) throws Exception;
    TopicAbout queryTopicAbout(TopicAbout topicAbout) throws Exception;
}
