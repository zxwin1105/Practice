package com.authserver.controller;

import com.authserver.domain.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zhaixinwei
 * @date: 2022/5/13
 * @description: 资源服务，用户数据服务
 */
@RestController
@RequestMapping("/user")
public class UserController {

    // 获取当前登录用户的信息
    @GetMapping()
    public Object getUser(Authentication authentication){
        return authentication.getPrincipal();
    }

    @PostMapping()
    public String addUser(SysUser sysUser){
        System.out.println("add user:"+sysUser);
        return sysUser == null ? "fail" : "success";
    }
}

