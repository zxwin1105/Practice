package com.pay.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.pay.config.MyWXPayConfig;
import com.pay.domain.Order;
import com.pay.exception.ServiceException;
import com.pay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhaixinwei
 * @date: 2022/6/22
 * @description:
 */
@Slf4j
@Service
public class PayServiceImpl implements IPayService {

    @Autowired
    private MyWXPayConfig myWXPayConfig;

    @Autowired
    private WXPay wxPay;

    @Override
    public Map<String, String> createNative(Order order) {
        Map<String,String> trade = new HashMap<>(16);
        trade.put("out_trade_no",order.getOrderId());
        trade.put("body","支付测试");
        // 单位分
        trade.put("total_fee",String.valueOf(order.getActualAmount()));
        //终端IP
        trade.put("spbill_create_ip", "127.0.0.1");
        trade.put("trade_type","NATIVE");
        // 微信下单
        Map<String, String> tradeRes = null;
        try {
            tradeRes = wxPay.unifiedOrder(trade);
        } catch (Exception e) {
            log.error("微信下单失败：{}",e.getMessage());
        }
        // 下单失败处理
        if(tradeRes == null || tradeRes.size() == 0 ||
                WXPayConstants.FAIL.equals(tradeRes.get("result_code"))){
            log.error("微信支付下单失败：{}",tradeRes!=null?tradeRes.get("err_code_des"):"");
            throw new ServiceException(500,"微信支付下单失败");
        }
        return tradeRes;
    }

    @Override
    public Map<String, String> payStatus(String orderId) {
        Map<String,String> map = new HashMap<>();
        map.put("out_trade_no",orderId);
        Map<String, String> tradeRes = null;
        try {
            tradeRes = wxPay.orderQuery(map);
        } catch (Exception e) {
            log.error("微信订单查询失败：{}",e.getMessage());
        }
        if(tradeRes == null || tradeRes.size() == 0 ||
                WXPayConstants.FAIL.equals(tradeRes.get("result_code"))){
            log.error("微信订单查询失败：{}",tradeRes!=null?tradeRes.get("err_code_des"):"");
            throw new ServiceException(500,"微信订单查询失败");
        }
        return tradeRes;
    }



    @Override
    public String callback(HttpServletRequest request) {
        // 反馈的微信息
        String xmlRes = null;
        Map<String,String> resMap = new HashMap<>();
        // 微信将响应信息通过数据流的方式返回
        try(ByteArrayOutputStream notifyOs = new ByteArrayOutputStream();
            ServletInputStream notifyIs = request.getInputStream()) {

            byte[] buff = new byte[1024];
            int len = 0;
            while (-1 != (len = notifyIs.read(buff))){
                notifyOs.write(buff,0,len);
            }
//            String notifyXmlStr = notifyOs.toString("utf-8");
            String notifyXmlStr ="<xml>\n" +
                    "  <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
                    "  <attach><![CDATA[支付测试]]></attach>\n" +
                    "  <bank_type><![CDATA[CFT]]></bank_type>\n" +
                    "  <fee_type><![CDATA[CNY]]></fee_type>\n" +
                    "  <is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
                    "  <mch_id><![CDATA[10000100]]></mch_id>\n" +
                    "  <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>\n" +
                    "  <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>\n" +
                    "  <out_trade_no><![CDATA[1409811653]]></out_trade_no>\n" +
                    "  <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                    "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "  <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>\n" +
                    "  <time_end><![CDATA[20140903131540]]></time_end>\n" +
                    "  <total_fee>1</total_fee>\n" +
                    "  <coupon_fee><![CDATA[10]]></coupon_fee>\n" +
                    "  <coupon_count><![CDATA[1]]></coupon_count>\n" +
                    "  <coupon_type><![CDATA[CASH]]></coupon_type>\n" +
                    "  <coupon_id><![CDATA[10000]]></coupon_id>\n" +
                    "  <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                    "  <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>\n" +
                    "</xml>";
            // 将XML转为map
            Map<String,String> responseMap = null;
            if (StringUtils.hasLength(notifyXmlStr)){
                responseMap = WXPayUtil.xmlToMap(notifyXmlStr);
            }

            // * 签名认证，保证微信响应的内容是当前服务商的某个订单
            if (!WXPayUtil.isSignatureValid(responseMap,"B552ED6B279343CB493C5DD0D78AB241", WXPayConstants.SignType.HMACSHA256)) {
                resMap.put("return_code",WXPayConstants.FAIL);
                resMap.put("return_msg","签名失败");
                // 响应失败
                return WXPayUtil.generateSignature(resMap,"B552ED6B279343CB493C5DD0D78AB241");
            }

            // TODO 当收到通知进行处理时，首先检查对应业务数据的状态，判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。
            // 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。

        }catch (Exception e){
            log.error("解析支付响应信息错误：{}",e.getMessage());
            throw new ServiceException(500,"微信订单查询失败");
        }
        // 响应成功
        return xmlRes;
    }
}
