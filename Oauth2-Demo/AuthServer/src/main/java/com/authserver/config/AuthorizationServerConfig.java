package com.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

/**
 * @author: zhaixinwei
 * @date: 2022/5/13
 * @description: 授权服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private RedisTokenStore redisTokenStore;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client") // 客户端ID
                .secret(passwordEncoder.encode("112233")) // 秘钥
                .redirectUris("http://www.baidu.com")// 重定向地址
                .scopes("all") // 授权范围
                .authorizedGrantTypes("authorization_code"); // 授权类型
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(redisTokenStore);
    }
}
