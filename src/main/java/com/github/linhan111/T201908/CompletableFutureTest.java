package com.github.linhan111.T201908;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture的使用
 * 详见：https://www.cnblogs.com/fingerboy/p/9948736.html
 * @author lhan111
 */
public class CompletableFutureTest {
    private static final ExecutorService executorService = new ThreadPoolExecutor(3, 10, 3L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(20),
            new ThreadFactoryBuilder().setNameFormat("test-%d")
                    .setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                        @Override
                        public void uncaughtException(Thread t, Throwable e) {
                            System.out.println("catch exception, thread info: " + t.getName() + ", throwable info:" + e.getMessage());
                        }
                    }).build());

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CompletableFuture<String> result1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(1/0);
                Thread.sleep(1000);
                return "get result1 complete";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "this is new thread's sout.";
        }, executorService).exceptionally(Throwable::getMessage);
        // 上面一句代码，如果抛出异常，且后续使用join获取执行结果，则异常会打印且中断主线程，可使用exceptionally来处理子线程中的异常而不对主线程产生影响

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
        System.out.println(result1.join());
        System.out.println(result2.join());
        // 注意获取执行结果的join和get方法的区别，get方法的方法声明会抛出异常，需要异常捕捉，join方法隐式抛出异常，存在异常直接中断主线程执行
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }
}
