package com.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mp.entity.User;
import com.mp.mapper.UserMapper;
import com.mp.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author zhaixinwei
 * @date 2022/8/3
 * @description
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
}
