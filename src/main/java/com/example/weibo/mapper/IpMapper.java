package com.example.weibo.mapper;

import com.example.weibo.pojo.IpInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository("ipMapper")
@Mapper
public interface IpMapper {
    void addIp(IpInfo ipInfo) throws Exception;
}
