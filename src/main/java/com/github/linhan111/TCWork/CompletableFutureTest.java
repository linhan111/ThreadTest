package com.github.linhan111.TCWork;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

/**
 * 先执行future4中的逻辑，后续的数据都要依赖他的返回值（返回业务数据前的查询），future1和future2没有先后（访问业务数据），future3的逻辑是用来组装数据（必须等待future1和future2全部结束才能执行）
 * 整个流程下来，执行时间为future4的执行时间 + future1和future2中最长的执行时间 + future3的执行时间
 * {@link java.util.concurrent.CompletableFuture} 用法：https://www.cnblogs.com/happyliu/p/9462703.html
 * {@link java.util.concurrent.CompletableFuture#thenAcceptBothAsync(CompletionStage, BiConsumer)} 用法：https://developer.aliyun.com/article/615637
 *
 * @author linhan111
 * @since 2021-01-08
 */
public class CompletableFutureTest {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        CompletableFuture future4 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenAccept(aVoid -> {
            CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "1";
            });
            CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "2";
            });

            /**
             * 这里也可以使用 {@link java.util.concurrent.CompletableFuture#allOf(CompletableFuture[])} 来实现
             */
            CompletableFuture future3 = future2.thenAcceptBoth(future1, (s2, s1) -> {
                System.out.println(s2 + " " + s1);

            });
            try {
                future3.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        try {
            future4.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
    }
}
