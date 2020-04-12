package com.example.weibo.service;

import com.example.weibo.mapper.UserMapper;
import com.example.weibo.pojo.TopicAbout;
import com.example.weibo.pojo.WeiboContentInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: weibo
 * @description:
 * @author: huang fangfan
 * @create: 2020-04-01 01:18
 */
@Service("contentServiceImpl")
public class ContentServiceImpl implements ContentService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public void addContent(WeiboContentInfo weiboContentInfo) throws Exception {
        userMapper.addContent(weiboContentInfo);
    }

    @Override
    public Page<WeiboContentInfo> queryUserText(int pageNum, int pageSize) throws Exception {
        PageHelper.startPage(pageNum,pageSize);
        return userMapper.queryUserText();
    }

    @Override
    public int queryCountIdFromView() throws Exception {
        return userMapper.queryCountIdFromView();
    }

    @Override
    public void insertKeywords(WeiboContentInfo weiboContentInfo) throws Exception {
        userMapper.insertKeywords(weiboContentInfo);
    }

    @Override
    public List<WeiboContentInfo> queryByKeyword(WeiboContentInfo weiboContentInfo) throws Exception {
        return userMapper.queryByKeyword(weiboContentInfo);
    }

    @Override
    public TopicAbout queryTopicAbout(TopicAbout topicAbout) throws Exception {
        return userMapper.queryTopicAbout(topicAbout);
    }
}
