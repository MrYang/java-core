package com.zz.multithread.concurrent.cooperation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Mr.Yang
 * @since 2017-07-14
 *
 * Condition的强大之处，假设缓存队列中已经存满，那么阻塞的肯定是写线程，唤醒的肯定是读线程，
 * 相反，阻塞的肯定是读线程，唤醒的肯定是写线程，
 * 假设只有一个Condition会有什么效果呢，缓存队列中已经存满，
 * 这个Lock不知道唤醒的是读线程还是写线程了，如果唤醒的是读线程，皆大欢喜，
 * 如果唤醒的是写线程，那么线程刚被唤醒，又被阻塞了，这时又去唤醒，这样就浪费了很多时间
 */
public class ConditionSample3 {

    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();    // 写入队列阻塞
    private Condition notEmpty = lock.newCondition();   // 从队列中读阻塞

    public static void main(String[] args) {
        ConditionSample3 conditionSample3 = new ConditionSample3();
        Queue<Integer> queue = new LinkedList<>();
        int maxSize = 10;
        Thread producer = conditionSample3.new Producer(queue, maxSize);
        Thread consumer = conditionSample3.new Consumer(queue);
        producer.start();
        consumer.start();
    }


    class Producer extends Thread {
        private final Queue<Integer> queue;
        private int maxSize;

        public Producer(Queue<Integer> queue, int maxSize) {
            this.queue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (queue.size() == maxSize) {
                        try {
                            System.out.println("queue full");
                            notFull.await();    // 阻塞生产者
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Random random = new Random();
                        int i = random.nextInt();
                        System.out.println("Producing value : " + i);
                        queue.add(i);
                        notEmpty.notifyAll();   // 唤醒消费者
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    class Consumer extends Thread {
        private final Queue<Integer> queue;

        public Consumer(Queue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (queue.isEmpty()) {
                        try {
                            System.out.println("queue empty");
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        int i = queue.remove();
                        System.out.println("consume value : " + i);
                        notFull.notifyAll();
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

}
