package com.guava.optional;

import com.google.common.base.Optional;
import com.guava.entity.User;
import org.checkerframework.checker.nullness.Opt;

/**
 * guava中Optional的使用
 * Optional是为了避免重复的null值校验代码。Guava中Optional<T>表示可能为null的T类型引用。一个Optional可能包含非null的引用（引用存在）；
 * 也可能包含null的引用（引用缺失）
 * @author zxwin
 * @date 2022/09/17
 */
public class OptionalDemo {

    public static void main(String[] args) {
        // Optional API介绍

        /* ----创建实例的API---- */
        User exist = new User("001","test","male");
        User missing = null;

        // 1.Optional.of(T) 创建指定引用的Optional实例，若引用为null则快速失败
        Optional<User> userExist = Optional.of(exist);
        // 会抛出NPE异常，快速失败
//        Optional<User> userMissing = Optional.of(missing);

        // 2.Optional.absent() 创建引用缺失的实例
        Optional<User> absent = Optional.absent();
//        System.out.println(absent.get());
        // 3.Optional.fromNullable(T) 创建指定引用的Optional实例，若引用为null则表示缺失
        Optional<User> userNullable = Optional.fromNullable(exist);
        Optional<User> userNull = Optional.fromNullable(missing);
        System.out.println(userNull.isPresent());


        /* ----用Optional查询实例引用---- */
        // 如果Optional包含非null的引用（引用存在），返回true;否则false
        userExist.isPresent();
        // 返回Optional所包含的引用，若引用缺失，则抛出java.lang.IllegalStateException
        userExist.get();
        // 返回Optional所包含的引用，若引用缺失，返回指定的值
        userNull.or(new User());
        // 返回Optional所包含的引用，若引用缺失，返回null
        userNull.orNull();
        // 返回Optional所包含引用的单例不可变集，如果引用存在，返回一个只有单一元素的集合，如果引用缺失，返回一个空集合。
        userNull.asSet();

    }
}
