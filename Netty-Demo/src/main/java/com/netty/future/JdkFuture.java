package com.netty.future;

import ch.qos.logback.core.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * JDKFuture 使用测试。
 * JDK提供的Future只能通过同步方式获取执行结果。
 * @author zhaixinwei
 * @date 2022/10/28
 */
@Slf4j
public class JdkFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 使用线程池工厂创建线程
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        /*
            理解Future，Future相当于一个结果的载具。任务线程将结果写入Future中，将Future返回给调用任务线程。
            调用线程再通过Future获取任务结果。
         */
        Future<String> future = executorService.submit(() -> {
            log.debug("begin execute task");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("execute thread task");
            return "task completed";
        });

        // 调用Future#get()，阻塞方法，当线程任务执行完成之后，会通过阻塞方法获取其返回值
        String result = future.get();
        log.info("task result:{}",result);

    }
}
