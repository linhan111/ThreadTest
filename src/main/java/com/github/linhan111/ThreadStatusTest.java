package com.github.linhan111;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lhan
 * @version 1.0.0
 * @className ThreadStatusTest
 * @date 19-3-8 上午11:12
 * @description 测试下线程的几种状态
 * @program ThreadTest
 */
public class ThreadStatusTest {
    private static int state = 0;
    private static ReentrantLock lock = new ReentrantLock(false);

    public static void main(String[] args) throws InterruptedException {
        /*// 主线程加t1线程同时开启，然后主线程休眠10s后打断t1线程
        Thread t1 = new Thread(() -> {
            while (true) {
                System.out.println("=== " + LocalDateTime.now() + " ===");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        Thread.sleep(5000);
        t1.interrupt();*/

        // 两个线程同时执行一个逻辑，在循环为10的整数倍的时候让出cpu timeslice使得其他线程得到机会执行
        Runnable runnable = () -> {
            for (int i = 0; i < 1000 ; i++) {
                System.out.println(Thread.currentThread().getName() + "===" + i);
                if (i % 10 == 0) {
                    Thread.yield();
                }
            }
        };
        new Thread(runnable).start();
        new Thread(runnable).start();

        /*Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10;) {
                lock.lock();
                while (state % 2 == 0) {
                    state++;
                    i++;
                    System.out.println(Thread.currentThread().getName() + "=====" + state);

                }
                lock.unlock();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; ) {
                lock.lock();
                while (state % 2 != 0) {
                    state++;
                    i++;
                    System.out.println(Thread.currentThread().getName() + "=====" + state);
                }
                lock.unlock();
            }
        });

        t1.start();
        t2.start();*/
    }
}
