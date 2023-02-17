package com.interview.java.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaixinwei
 * @date 2023/1/29
 */
public class InterruptDemo {

    public static void main(String[] args) throws InterruptedException {
//        Thread interrupt =  new Thread(new InterruptShow());
//        interrupt.start();
//        interrupt.interrupt();

        Thread interrupted =  new Thread(new InterruptedShow());
        interrupted.start();

        interrupted.interrupt();
    }
}

class InterruptShow implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("Interrupt() start");
            TimeUnit.MILLISECONDS.sleep(2000);
            System.out.println("Interrupt() sleep end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Interrupt() end.");
    }
}

class InterruptedShow implements Runnable {

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            System.out.println("Interrupted()");
        }
        System.out.println("Interrupted() end.");
        // interrupted()会清除中断标记
        System.out.println(Thread.interrupted());
        System.out.println(Thread.currentThread().isInterrupted());
    }
}
