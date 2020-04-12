package com.example.weibo.service;

import com.example.weibo.mapper.IpMapper;
import com.example.weibo.pojo.IpInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: weibo
 * @description:
 * @author: huang fangfan
 * @create: 2020-03-29 18:12
 */
@Service("ipInfoServiceImpl")
public class IpInfoServiceImpl implements IpInfoService {
    @Autowired
    private IpMapper ipMapper;

    @Override
    public void addIp(IpInfo ipInfo) throws Exception {
        ipMapper.addIp(ipInfo);
    }
}
