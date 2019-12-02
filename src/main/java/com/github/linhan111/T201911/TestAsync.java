package com.github.linhan111.T201911;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试提交到线程池的异步任务在主线程退出后是否能继续获得结果
 */
public class TestAsync {

    private static final ExecutorService executorService = new ThreadPoolExecutor(3, 10, 3L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(20),
            new ThreadFactoryBuilder().setNameFormat("test-%d").build());

    public static void main(String[] args) throws IOException {
        System.out.println(test());
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 3; i++) {
                if (i == 2) {
                    System.out.println(1 / 0);
                }
            }
        }, executorService).exceptionally(e -> {
            System.out.println("线程池出现异常了，e：" + e.getMessage());
            return null;
        });
        System.out.println("main 函数执行结束了");
        // 防止程序意外退出
//        System.in.read();
    }

    private static String test() {
        return "this is test.";
    }
}
