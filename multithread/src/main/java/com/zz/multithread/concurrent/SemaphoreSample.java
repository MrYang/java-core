package com.zz.multithread.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

/**
 * @author Mr.Yang
 * @since 2017-07-16
 */
public class SemaphoreSample {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        //只能有三个线程同时访问
        final Semaphore sp = new Semaphore(3);
        // 模拟10个客户端访问
        IntStream.range(0, 10).forEach(i -> {
            Runnable runnable = () -> {
                try {
                    sp.acquire();

                    System.out.println("Accessing: " + i);
                    Thread.sleep((long) (Math.random() * 6000));
                    // 访问完后，释放
                    sp.release();
                    //availablePermits()指的是当前信号灯库中有多少个可以被使用
                    System.out.println("-----------------" + sp.availablePermits());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            service.execute(runnable);
        });
    }
}
