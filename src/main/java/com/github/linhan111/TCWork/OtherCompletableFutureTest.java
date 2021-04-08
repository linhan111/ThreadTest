package com.github.linhan111.TCWork;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * OtherCompletableFutureTest
 * 嵌套任务看下执行时间如何计算的
 *
 * @author 林焓53411
 * @since 2021/4/7
 */
public class OtherCompletableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            CompletableFuture c1 = CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            CompletableFuture c2 = CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            try {
                CompletableFuture.allOf(c1, c2).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return "2";
        });

        CompletableFuture.allOf(future1, future2).get();
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
    }

}
