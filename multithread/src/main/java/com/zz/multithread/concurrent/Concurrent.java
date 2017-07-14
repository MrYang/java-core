package com.zz.multithread.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Concurrent {
    private Executor pool = Executors.newCachedThreadPool();


    public void future() throws ExecutionException, InterruptedException {
        List<CompletableFuture<Long>> list = new ArrayList<>();
        String[] urlArray = new String[]{};
        for (String url : urlArray) {
            CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(new Random().nextInt(2));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 0L;
            });
            list.add(future);
        }

        long count = 0;
        for (CompletableFuture<Long> f : list) {
            try {
                count += f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void completionService() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletionService<Integer> cs = new ExecutorCompletionService<>(threadPool);
        IntStream.range(0, 5).forEach((int i) -> cs.submit(() -> i));

        IntStream.range(0, 5).forEach(i -> {
            try {
                cs.take().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

    }

    private Lock lock = new ReentrantLock();

    public void condition() {
    }

    public void semaphore() {

    }

    public void cyclicBarrier() {

    }

    public void countdownLatch() {

    }

}
