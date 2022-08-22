package com.pay.config;

import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author: zhaixinwei
 * @date: 2022/6/22
 * @description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "wxpay")
public class MyWXPayConfig extends WXPayConfig {

    private String appId;
    private String mchId;
    private String key;

    @Override
    public String getAppId() {
        return appId;
    }

    @Override
    public String getMchId() {
        return mchId;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String s, long l, Exception e) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig wxPayConfig) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API,true);
            }
        };
    }
}
