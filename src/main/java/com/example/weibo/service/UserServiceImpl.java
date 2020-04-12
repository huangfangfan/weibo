package com.example.weibo.service;

import com.example.weibo.mapper.UserMapper;
import com.example.weibo.pojo.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: weibo
 * @description:
 * @author: huang fangfan
 * @create: 2020-03-24 13:49
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Override
    public void addUser(User user) throws Exception {
        userMapper.addUser(user);
    }

    @Override
    public String selecrUser(User user) throws Exception {
        return userMapper.selectUser(user);
    }

    @Override
    public Page<String> queryAll(int pageNum, int pageSize) throws Exception {
        PageHelper.startPage(pageNum,pageSize);
        return userMapper.queryAll();
    }

    @Override
    public int queryCountId() throws Exception {
        return userMapper.queryCountId();
    }

    @Override
    public Page<String> queryUserLink(int pageNum, int pageSize) throws Exception {
        PageHelper.startPage(pageNum,pageSize);
        return userMapper.queryUserLink();
    }

}
