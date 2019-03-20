package com.github.linhan111.T201903;

import java.util.concurrent.Semaphore;

/**
 * @author lhan
 * @version 1.0.0
 * @className SemaphoreTest
 * @date 19-3-20 下午3:18
 * @description Semaphore demo
 * @program ThreadTest
 */
public class SemaphoreTest {

    private static Semaphore semaphore = new Semaphore(5, false);

    // 也可以这里写Runnable，多个线程同时用一个Runnable实例来执行
    /*static Runnable runnable = () -> {
        try {
            semaphore.acquire(2);
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + " done.剩余permit个数：" + semaphore.availablePermits());
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };*/

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            // 这里10个线程都new一个Runnable实例出来，但是信号量是同一个，可以保证并发安全
            new Thread(() -> {
                try {
                    // 总共信号量有10个，每个线程执行需要两个，但是执行完成后只放出一个，注意分析结果及原因
                    semaphore.acquire(2);
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " done.剩余permit个数：" + semaphore.availablePermits());
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Thread_" + i).start();
        }
    }
}
