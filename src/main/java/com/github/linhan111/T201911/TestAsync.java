package com.github.linhan111.T201911;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

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

    public static void main(String[] args) {
        try {
            System.out.println(test());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // 如果抛出InterruptedException，应该将当前线程状态修改为interrupt
                Thread.currentThread().interrupt();
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


    private static String test() throws InterruptedException {
        Stopwatch watch = Stopwatch.createStarted();
        // 注意如果直接写在main方法中则调用链过短，会报npe，一般web应用加载多个拦截器过滤器等不会报错
        System.out.println(Thread.currentThread().getStackTrace()[2].getClassName() +
                "#" + Thread.currentThread().getStackTrace()[2].getMethodName());
        Thread.sleep(1000);

        // 注意这里stop之后watch后不会计入后面的时间
        watch.stop();
        Thread.sleep(2000);
        System.out.println("代码执行时间：" +  watch.elapsed(TimeUnit.MILLISECONDS));
        return "this is test.";
    }
}
