package com.mybatis.mapper;

import com.mybatis.entity.UserInfo;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cache.decorators.FifoCache;


/**
 * @author zhaixinwei
 * @date 2022/11/17
 */
//@CacheNamespace(eviction = FifoCache.class, flushInterval = 600000)
public interface UserMapper {

    int insertUserInfo(UserInfo userInfo);

    UserInfo getOneById(@Param("id") String id);
}
