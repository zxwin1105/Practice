package com.unit.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author: zhaixinwei
 * @date: 2022/7/18
 * @description: 参数化测试，允许开发人员针对同一方法使用不同的值进行多次测试
 *
 * 1. 为准备使用参数化测试的测试类指定特殊的运行器 org.junit.runners.Parameterized。
 * 2. 为测试类声明几个变量，分别用于存放期望值和测试所用数据。
 * 3. 为测试类声明一个带有参数的公共构造函数，并在其中为第二个环节中声明的几个变量赋值。
 * 4. 为测试类声明一个使用注解 org.junit.runners.Parameterized.Parameters 修饰的，
 *    返回值为 java.util.Collection 的公共静态方法，并在此方法中初始化所有需要测试的参数对。
 * 5. 编写测试方法，使用定义的变量作为参数进行测试。
 */
@RunWith(Parameterized.class)
public class ParameterizedTest {


    private Integer number;
    private Boolean expectValue;

    // 待测试类
    private PrimeNumberChecker primeNumberChecker;

    public ParameterizedTest(Integer number,Boolean expectValue){
        this.number = number;
        this.expectValue = expectValue;

    }

    @Parameterized.Parameters
    public static Collection params(){
        return Arrays.asList(new Object[][]{
            {2,true},
            {3,true},
            {6,false},
            {8,false}
        });
    }

    @Test
    public void test(){
        System.out.println("input number:"+number);
        Assert.assertEquals(expectValue,primeNumberChecker.validate(number));
    }

    @Before
    public void populate(){
        primeNumberChecker = new PrimeNumberChecker();
    }
}
