package com.example.weibo.service;

import com.example.weibo.pojo.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface UserService {
    //新增用户
    public void addUser(User user) throws Exception;
    public String selecrUser(User user)throws Exception;
    public Page<String> queryAll(int pageNum, int pageSize) throws Exception;
    public int queryCountId() throws Exception;
    public Page<String> queryUserLink(int pageNum, int pageSize) throws Exception;
}
