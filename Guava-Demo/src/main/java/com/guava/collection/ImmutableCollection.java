package com.guava.collection;

import com.google.common.collect.ImmutableSet;

/**
 * Guava 不可变集合
 *
 * @author zhaixinwei
 * @date 2022/12/9
 */
public class ImmutableCollection {

    public static void main(String[] args) {
        ImmutableSet<String> immutableSet = ImmutableSet.of("set4","set2","set3");
        ImmutableSet<String> immutableSet1 = ImmutableSet.copyOf(immutableSet);
        ImmutableSet<Object> immutableSet2 = ImmutableSet.builder().add(immutableSet).build();
        System.out.println(immutableSet1 == immutableSet);
        System.out.println(immutableSet1);
        System.out.println(immutableSet2);

    }

}
