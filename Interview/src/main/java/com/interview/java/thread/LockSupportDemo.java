package com.interview.java.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * @author zxwin
 * @date 2023/2/12
 */
public class LockSupportDemo {

    public static void main(String[] args) {
        // LockSupport.park不释放锁实验
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(()->{
                synchronized (LockSupportDemo.class){
                    System.out.println(Thread.currentThread().getName() + "before park");
                    LockSupport.park();
                    System.out.println(Thread.currentThread().getName()+ "after park");
                }
            },"Thread-"+i);
            thread.start();
//            // 调用unpark()方法
//            LockSupport.unpark(thread);
        }
        // 程序输出before park阻塞
    }


    public void lockSupportMethod(){
        // 阻塞当前线程
        LockSupport.park();
        // 阻塞当前线程，并设置线程的parkBlocker字段
        LockSupport.park("blocker");
        // 阻塞当前线程，并设置过期时间,过期后阻塞线程就绪
        LockSupport.parkNanos(100);

        // 此函数表示在指定的时限前禁用当前线程
        LockSupport.parkUntil(100);

        // 解除指定线程的park阻塞
        LockSupport.unpark(Thread.currentThread());
    }

}
