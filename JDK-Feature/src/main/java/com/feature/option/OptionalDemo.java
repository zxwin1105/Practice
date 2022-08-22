package com.feature.option;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * @author zhaixinwei
 * @date 2022/7/27
 * @description jdk8 新特性 Optional
 */
public class OptionalDemo {

    private final Optional<String> name;

    private final OptionalInt age;

    public OptionalDemo(String name, Integer age) {
        this.name = Optional.of(name);
        this.age = OptionalInt.of(age);
    }

    public void show() {
        System.out.println(this.name.get());
    }

    public static void main(String[] args) {

    }
}
