package com.github.linhan111.T201908;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * CompletableFuture的使用
 * 详见：https://www.cnblogs.com/fingerboy/p/9948736.html
 * @author lhan111
 */
public class CompletableFutureTest {
    private static final ExecutorService executorService = new ThreadPoolExecutor(3, 10, 3L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(20),
            new ThreadFactoryBuilder().setNameFormat("test-%d").build());

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CompletableFuture<String> result1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                return "get result1 complete";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "this is new thread's sout.";
        }, executorService);

        CompletableFuture<String> result2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                return "get result2 complete";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "this is new thread's sout.";
        }, executorService);
        CompletableFuture.allOf(result1, result2);
        // 如果这里需要获取执行结果，则阻塞主线程，阻塞时间是最长的执行时间
//        System.out.println(result1.join());
//        System.out.println(result2.join());
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }
}
