package com.github.linhan111.T201904;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExceptionTest {

    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5, 5L,
                                                                    TimeUnit.SECONDS, new LinkedBlockingQueue<>(20),
                                                                    new ThreadFactoryBuilder().setNameFormat("test-%d").build());

    public static void main(String[] args) {
        System.out.println("子线程开始");
        // 新开线程方式
       /* new Thread(() -> {
            System.out.println("即将抛出异常");
            System.out.println(2 / 0);
        }).start();*/
        // 线程池提交任务方式，调用线程池的submit方法不会抛出异常！
        pool.execute(() -> {
            System.out.println("即将抛出异常");
            System.out.println(2 / 0);
        });
        System.out.println("执行结果应该不影响主线程流程");
    }
}
