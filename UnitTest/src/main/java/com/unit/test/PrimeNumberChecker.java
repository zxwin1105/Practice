package com.unit.test;

/**
 * @author: zhaixinwei
 * @date: 2022/7/18
 * @description: 参数化测试方式的 被测试类
 */
public class PrimeNumberChecker {


    /**
     * 判断一个数是否是素数
     * @param number 被判断数
     * @return 是否为素数
     */
    public boolean validate(final Integer number){
        for (int i = 2; i < (number / 2); i++) {
            if (number % i == 0){
                return false;
            }
        }
        return true;
    }
}
