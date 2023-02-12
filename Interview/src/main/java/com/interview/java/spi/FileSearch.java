package com.interview.java.spi;

/**
 * @author zhaixinwei
 * @date 2023/1/12
 */
public class FileSearch implements Search{
    @Override
    public void search() {
        System.out.println("fileSearch");
    }
}
