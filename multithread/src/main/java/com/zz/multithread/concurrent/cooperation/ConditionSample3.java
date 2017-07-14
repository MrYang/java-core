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
 */
public class ConditionSample3 {

    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

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
                            notFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Random random = new Random();
                        int i = random.nextInt();
                        System.out.println("Producing value : " + i);
                        queue.add(i);
                        notEmpty.notifyAll();
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
