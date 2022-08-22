package com.authserver.service;

import com.authserver.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.util.Password;

/**
 * @author: zhaixinwei
 * @date: 2022/5/13
 * @description: 自定义登录逻辑
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = passwordEncoder.encode("123456");
        SysUser user = new SysUser();
        user.setId(1);
        user.setUsername("zxwin");
        user.setPassword(password);
        return user;
    }
}
