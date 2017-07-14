package com.zz.multithread.concurrent;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author Mr.Yang
 * @since 2017-07-14
 */
public class BlockQueueSample {

    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);
        ExecutorService service = Executors.newCachedThreadPool();

        for (int i = 0; i < 5; i++) {
            service.submit(new Producer(blockingQueue, "Producer" + i));
        }
        for (int i = 0; i < 5; i++) {
            service.submit(new Consumer(blockingQueue, "Consumer" + i));
        }
        service.shutdown();
    }

}

class Producer implements Runnable {

    private BlockingQueue<String> blockingQueue;
    private String name;
    private static AtomicInteger productID = new AtomicInteger(0);

    public Producer(BlockingQueue<String> blockingQueue, String name) {
        this.blockingQueue = blockingQueue;
        this.name = name;
    }

    @Override
    public void run() {
        IntStream.range(0, 10).forEach(i -> {
            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(3));

                String pro = "product-" + productID.getAndIncrement();
                boolean offer = blockingQueue.offer(pro, 5, TimeUnit.SECONDS);
                System.out.println("product " + name + " product: " + pro + ", is success:" + offer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("product " + name + " is done");
    }
}

class Consumer implements Runnable {
    private BlockingQueue<String> blockingQueue;
    private String name;

    public Consumer(BlockingQueue<String> blockingQueue, String name) {
        this.blockingQueue = blockingQueue;
        this.name = name;
    }

    @Override
    public void run() {
        IntStream.range(0, 10).forEach(i -> {
            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(3));
                String con = blockingQueue.take();

                System.out.println("consumer " + name + " consume : " + con);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("consumer " + name + " is done");
    }
}
