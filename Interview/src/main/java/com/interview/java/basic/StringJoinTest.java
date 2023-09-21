package com.interview.java.basic;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author seisei
 * @date 2023/7/17
 */
public class StringJoinTest implements Serializable {

    public static void main(String[] args) {
        StringJoiner stringJoiner = new StringJoiner(",","(",")");
        for (int i = 0; i < 65539; i++) {
            stringJoiner.add(Integer.toString(i));
        }

        System.out.println(stringJoiner.toString());
    }
}
