package com.zz.multithread.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Concurrent {
    private Executor pool = Executors.newCachedThreadPool();

    public void condition() {

    }

    public void future() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> "async")
                .whenComplete((v, e) -> {
            System.out.println(v);
            System.out.println(e);
        });

        System.out.println(future.get());
    }

    public void completionService() {

    }

    public void semaphore() {

    }

    public void cyclicBarrier() {

    }

    public void countdownLatch() {

    }

    public void forkjoin() {

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Concurrent().future();
    }
}
