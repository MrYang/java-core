package com.zz.feature.java8;

import java.util.concurrent.CompletableFuture;

/**
 * @author Mr.Yang
 * @since 2017-06-07
 */
public class CompletableFutureExample {

    public void test() throws Exception {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();

        completableFuture.complete(100);

        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> System.out.println("xxx"));

        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> "supply async");

        completableFuture2.whenComplete((String str, Throwable e) -> {
            System.out.println(str);
        });

        System.out.println(completableFuture.get());
        System.out.println(completableFuture2.get());
        System.in.read();
    }
}
