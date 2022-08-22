package com.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mp.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhaixinwei
 * @date 2022/8/3
 * @description
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
