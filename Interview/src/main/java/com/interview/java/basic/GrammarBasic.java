package com.interview.java.basic;

/**
 * Java面试常见的语法基础
 *
 * @author zhaixinwei
 * @date 2023/1/12
 */
public class GrammarBasic {

    public static void main(String[] args) throws Exception {
        GrammarBasic grammarBasic = new GrammarBasic();
        grammarBasic.addOpt();

//        GrammarBasic object = grammarBasic.getObject(GrammarBasic.class);
        grammarBasic.reflect();
    }

    /**
     * 1. a = a+b 和 a+=b的区别？
     * a+=b会有隐士的强制类型转换
     */
    public void addOpt() {
        byte a = 127;
        byte b = 127;
        // a = a+b; error: cannot convert from int to byte
        a += b;
        System.out.println(a);
    }

    /**
     * 2. 为什么需要泛型？
     * - 适用于多种数据类型执行相同的代码
     * - 在使用时指定类型，不需要做强制类型转换（提高类型安全）
     *
     * 定义泛型方法
     */
    public <T> T getObject(Class<T> tClass) throws IllegalAccessException, InstantiationException {
        return tClass.newInstance();
    }

    /**
     * 3. 反射
     * 反射是指在运行阶段中，可以获取类的方法和属性；可以调用一个对象的方法和属性，这种动态获取信息以及调用对象方法功能称为Java反射技术。
     */
    public void reflect() throws ClassNotFoundException {
        // 1. 获取Class对象的三种方式
        Class<GrammarBasic> class1 = GrammarBasic.class;
        Class<? extends GrammarBasic> class2 = new GrammarBasic().getClass();
        Class<?> class3 = Class.forName("com.interview.java.basic.GrammarBasic");
        // 常用方法
        System.out.println(class1.getName());
        System.out.println(class1.getSimpleName());

    }
}
