package com.interview.java.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaixinwei
 * @date 2023/1/30
 */
public class VolatileDemo {
    private static boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            while (!stop){
                System.out.println("running");
            }
        }).start();

        TimeUnit.MILLISECONDS.sleep(100);

        stop = true;
    }
}
