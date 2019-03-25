package com.github.linhan111.T201903;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lhan
 * @version 1.0.0
 * @className ThreadPoolDemo
 * @date 19-3-18 下午1:51
 * @description 测试定时任务线程池，创建线程最好通过线程池创建，线程池创建最好指定ThreadFactory并设置
 * @program ThreadTest
 */
public class ThreadPoolDemo {
    // 原子变量
    private static AtomicInteger integer = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        // 创建ThreadFactory，可自定义线程名、优先级、是否为daemon、UncaughtExceptionHandler
        ScheduledExecutorService ses = new ScheduledThreadPoolExecutor(3, r -> {
            Thread t = new Thread(r);
            t.setPriority(10);
            t.setName("test_thread_" + integer.getAndIncrement());
            return t;
        });
        // 注意scheduleAtFixedRate与scheduleWithFixedDelay的区别！
        ses.scheduleWithFixedDelay(() -> {
            System.out.println("线程开始执行，线程名称：" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1000, 1000, TimeUnit.MILLISECONDS);
    }
}