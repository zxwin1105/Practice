package com.interview.java.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaixinwei
 * @date 2023/1/19
 */
public class ConCurrentBasic {

    // 可见性验证
    static class Visibility{
        public static void main(String[] args) {
            for (int i = 0; i < 1000; i++) {
                Visibility visibility = new Visibility();
                Thread shardThread = new Thread(visibility::setShare);
                Thread resThread = new Thread(visibility::setRes);
                shardThread.start();
                resThread.start();


                // 等待其他线程执行完毕，打印结果
                try {
                    shardThread.join();
                    resThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(visibility.getRes());

            }

        }

        private volatile int share = 0;

        private int res = 0;

        /**
         * 给share复制，原子操作
         */
        public void setShare(){
            this.share = 10;
        }

        /**
         * 给res赋值，原子操作
         */
        public void setRes(){
            res = share;
        }

        public int getRes() {
            return res;
        }
    }

    // 原子性验证
    static class FactorDemo{

        public static void main(String[] args) throws InterruptedException {
            for (int j = 0; j < 100; j++) {
                // 创建线程共享对象
                FactorDemo factorDemo = new FactorDemo();

                // 创建两个线程取执行factor()方法
                for (int i = 0; i < 2; i++) {
                    new Thread(factorDemo::factor).start();
                }
                // 等待其他线程执行完毕，main线程执行打印共享变量操作
                TimeUnit.SECONDS.sleep(1);
                System.out.println("结果"+j+":"+factorDemo.getShare());
            }
        }

        // 共享变量
        private int share = 1;

        /**
         * 对共享变量的操作
         */
        public void factor() {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            share+=1;
        }

        public int getShare() {
            return share;
        }
    }
}
