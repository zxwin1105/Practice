package com.pay.config;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhaixinwei
 * @date: 2022/6/22
 * @description:
 */
@Slf4j
@Configuration
public class WXBeanConfig {

    @Value("${wxpay.notify_url}")
    private String notifyUrl;

    @Bean
    public WXPay wxPay(WXPayConfig wxPayConfig) {
        WXPay wxPay = null;
        try {
            wxPay = new WXPay(wxPayConfig,notifyUrl,true,false);
        } catch (Exception e) {
            log.error("注册微信支付失败{}",e.getMessage());
        }
        return wxPay;
    }
}
