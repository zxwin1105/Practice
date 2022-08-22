package com.unit.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Random;

/**
 * @author: zhaixinwei
 * @date: 2022/7/19
 * @description:
 * 著作权归https://pdai.tech所有。
 * 链接：https://www.pdai.tech/md/develop/ut/dev-ut-x-mockito.html
 * Mock 测试就是在测试过程中，对于某些不容易构造（如 HttpServletRequest 必须在Servlet 容器中才能构造出来）
 * 或者不容易获取比较复杂的对象（如 JDBC 中的ResultSet 对象），用一个虚拟的对象（Mock 对象）来创建以便测试的测试方法。
 * Mock 最大的功能是帮你把单元测试的耦合分解开，如果你的代码对另一个类或者接口有依赖，它能够帮你模拟这些依赖，
 * 并帮你验证所调用的依赖的行为。
 */
@RunWith(MockitoJUnitRunner.class)
public class MockTest {

    /*
    @Mock注解使用方式
     */

    @Mock
    private Random random;

    @Test
    public void test1(){
        Mockito.when(random.nextInt()).thenReturn(1);

        Assert.assertEquals(1,random.nextInt());
    }

    /*
     * 参数匹配-模糊匹配
     */
    @Test
    public void test2(){
        List mock = Mockito.mock(List.class);

        Mockito.when(mock.get(Mockito.anyInt())).thenReturn("hello mockito");


        Assert.assertEquals("hello mockito",mock.get(2));
    }

}
