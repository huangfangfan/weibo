package com.example.weibo.mapper;

import com.example.weibo.pojo.TopicAbout;
import com.example.weibo.pojo.User;
import com.example.weibo.pojo.WeiboContentInfo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 岁月无声
 */
@Repository("userMapper")
@Mapper
public interface UserMapper {
    /**
     * 功能描述:
     * 〈新增用户〉
     *
     * @param user 1
     * @return : void
     * @author : huangfangfan
     * @date : 2020/3/28 23:10
     */
    void addUser(User user) throws Exception;
    /*
     * 功能描述:
     * 〈判断用户在数据库是否存在〉
     *
     * @param user 1
     * @return : java.lang.String
     * @author : huangfangfan
     * @date : 2020/3/28 23:10
     */
    String selectUser(User user) throws Exception;
    /**
     * 功能描述:
     * 〈分页查询用户id〉
     *
     * @param  1
     * @return : com.github.pagehelper.Page<java.lang.String>
     * @author : huangfangfan
     * @date : 2020/3/28 23:10
     */
    Page<String> queryAll() throws Exception;
    /**
     * 功能描述:
     * 〈获取用户总数量〉
     *
     * @param  1
     * @return : int
     * @author : huangfangfan
     * @date : 2020/3/28 23:11
     */
    int queryCountId() throws Exception;
    /**
     * 功能描述:
     * 〈分页获取用户链接〉
     *
     * @param  1
     * @return : com.github.pagehelper.Page<java.lang.String>
     * @author : huangfangfan
     * @date : 2020/3/28 23:13
     */
    Page<String> queryUserLink() throws Exception;
    /**
     * 功能描述:
     * 〈插入微博内容及信息〉
     *
     * @param weiboContentInfo 1
     * @return : void
     * @author : huangfangfan
     * @date : 2020/4/1 1:18
     */
    void addContent(WeiboContentInfo weiboContentInfo) throws Exception;
    /**
     * 功能描述:
     * 〈查询文本〉
     *
     * @param  1
     * @return : com.github.pagehelper.Page<java.lang.String>
     * @author : huangfangfan
     * @date : 2020/4/5 14:00
     */
    Page<WeiboContentInfo> queryUserText() throws Exception;
    /**
     * 功能描述:
     * 〈获取视图中数据总条数〉
     *
     * @param  1
     * @return : int
     * @author : huangfangfan
     * @date : 2020/4/5 16:24
     */
    int queryCountIdFromView() throws Exception;
    /**
     * 功能描述:
     * 〈将关键字插入视图〉
     *
     * @param weiboContentInfo 1
     * @return : void
     * @author : huangfangfan
     * @date : 2020/4/5 22:08
     */
    void insertKeywords(WeiboContentInfo weiboContentInfo) throws Exception;
    /**
     * 功能描述:
     * 〈查询包含关键字的文本〉
     *
     * @param weiboContentInfo 1
     * @return : java.util.List<com.example.weibo.pojo.WeiboContentInfo>
     * @author : huangfangfan
     * @date : 2020/4/8 23:17
     */
    List<WeiboContentInfo> queryByKeyword(WeiboContentInfo weiboContentInfo) throws Exception;
    /**
     * 功能描述:
     * 〈查询计算话题热度需要的数据〉
     *
     * @param topicAbout 1
     * @return : com.example.weibo.pojo.TopicAbout
     * @author : huangfangfan
     * @date : 2020/4/8 23:23
     */
    TopicAbout queryTopicAbout(TopicAbout topicAbout) throws Exception;
}
