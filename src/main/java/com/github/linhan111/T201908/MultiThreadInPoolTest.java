package com.github.linhan111.T201908;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 测试场景：三个耗时任务，分别耗时1s 2s 3s，获取到三个执行结果后才能一起返回（这里有组装数据的逻辑），如何使响应时间最短！
 *
 * @author lhan111
 */
public class MultiThreadInPoolTest {
    private static final ExecutorService executorService = new ThreadPoolExecutor(3, 10, 3L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(20),
            new ThreadFactoryBuilder().setNameFormat("test-%d").build());

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        Future<String> result1 = executorService.submit(() -> {
            Thread.sleep(1000);
            return "test1-thread";
        });

        Future result2 = executorService.submit(() -> {
            Thread.sleep(1000);
            return "test2-thread";
        });

        Future result3 = executorService.submit(() -> {
            Thread.sleep(5000);
//            return "test3-thread";
            throw new RuntimeException("dsfsd");
        });

        try {
            System.out.println(result1.get());
            System.out.println(result2.get());
            System.out.println(result3.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("执行时间(ms)：" + (System.currentTimeMillis() - startTime));

        // 注意submit与execute方法的区别，同时注意submit方法中callable与runnable两个入参的区别！
        // 参考连接-submit与execute的区别：https://www.cnblogs.com/twoheads/p/9512705.html
        // 参考连接-callable与runnable的区别：https://blog.csdn.net/jethai/article/details/52345218
        Future x = executorService.submit(() -> {
            throw new RuntimeException("submit方法内部报错");
        });
        try {
            x.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.execute(() -> {
            throw new RuntimeException("execute方法内部报错");
        });
    }
}
