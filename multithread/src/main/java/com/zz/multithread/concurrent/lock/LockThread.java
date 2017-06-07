package com.zz.multithread.concurrent.lock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @author Mr.Yang
 * @since 2017-05-12
 *
 * ReentrantReadWriteLock
 */
public class LockThread implements Runnable {

    private int count = 0;
    private int random = 0;

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " is running");

        lock1.lock();
        System.out.println(threadName + " get lock1");
        try {
            count++;
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(threadName + " first:count=" + count + "\trandom=" + random);
            lock1.unlock();
        }

        lock2.lock();
        System.out.println(threadName + " get lock2");
        try {
            Random r = new Random();
            random = r.nextInt(200);
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(threadName + " second:count=" + count + "\trandom=" + random);
            lock2.unlock();
        }
    }

    public static void main(String[] args) {
        LockThread lockThread = new LockThread();
        IntStream.of(1, 2, 3).forEach(i -> new Thread(lockThread, "Thread" + i).start());
    }
}
