package com.interview.java;

import java.util.concurrent.TimeUnit;

/**
 * finally为被执行的情况
 * @author seisei
 * @date 2023/7/17
 */
public class FinallyTest {

    /**
     * 情况1，没有执行到try语句
     */
    private void test1(boolean flag){
        if (flag){
            return;
        }

        try {
            // 如果分支在try中，finally会被执行
//            if (flag){
//                return;
//            }
            System.out.println("try");
        }finally {
            System.out.println("finally");
        }

    }

    /**
     * 情况二：线程被打断
     */
    private Thread test2(){
        Thread thread = new Thread(() -> {
            try {
                System.out.println("try");
                int i = 0;
                while (i < 100) {

                    System.out.println(i);
                    TimeUnit.SECONDS.sleep(1);
                    i++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("finally");
            }
        });

        return thread;
    }

    public static void main(String[] args) throws InterruptedException {
        FinallyTest finallyTest = new FinallyTest();
//        finallyTest.test1(true);
//        finallyTest.test1(false);
        Thread thread = finallyTest.test2();
        thread.start();
        TimeUnit.SECONDS.sleep(5);

        thread.interrupt();


    }
}
