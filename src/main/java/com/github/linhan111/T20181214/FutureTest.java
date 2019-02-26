package com.github.linhan111.T20181214;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lhan
 * @version 1.0.0
 * @className FeatureTest
 * @date 19-1-19 下午5:22
 * @description 测试同步异步执行的CompletableFuture
 * @program ThreadTest
 */
public class FutureTest {

    private static final ExecutorService executorService = new ThreadPoolExecutor(3, 10, 3L, TimeUnit.SECONDS,
                                                                           new LinkedBlockingQueue<>(20), new ThreadFactoryBuilder().setNameFormat("test-%d").build());

    public static void main(String[] args) throws Exception {
        long q = System.currentTimeMillis();
        Thread.sleep(2000);
        executorService.execute(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("耗时为：" + (System.currentTimeMillis() - q));
    }
}
