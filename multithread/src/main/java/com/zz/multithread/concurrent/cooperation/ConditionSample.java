package com.zz.multithread.concurrent.cooperation;

import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Yang
 * @since 2017-07-14
 *
 * 一定要获取锁，才能调用wait，notify方法
 */
public class ConditionSample {

    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();

        thread1.start();

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread2.start();
    }

    static class Thread1 extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("thread1 get lock");
            }
        }
    }

    static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                lock.notify();
                System.out.println("thread2 call notify");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2 sleep ");
            }

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread2 release lock");
        }
    }
}
