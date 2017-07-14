package com.zz.multithread.concurrent.cooperation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * @author Mr.Yang
 * @since 2017-07-14
 */
public class ConditionSample2 {

    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        int maxSize = 10;
        Thread producer = new Producer(queue, maxSize);
        Thread consumer = new Consumer(queue);
        producer.start();
        consumer.start();
    }
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
            synchronized (queue) {
                while (queue.size() == maxSize) {
                    try {
                        System.out.println("queue full");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Random random = new Random();
                    int i = random.nextInt();
                    System.out.println("Producing value : " + i);
                    queue.add(i);
                    queue.notifyAll();
                }
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
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        System.out.println("queue empty");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int i = queue.remove();
                    System.out.println("consume value : " + i);
                    queue.notifyAll();
                }
            }
        }
    }
}
