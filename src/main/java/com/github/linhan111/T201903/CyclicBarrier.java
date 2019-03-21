package com.github.linhan111.T201903;

import java.util.concurrent.BrokenBarrierException;

/**
 * @author lhan
 * @version 1.0.0
 * @className CyclicBarrier
 * @date 19-3-21 上午10:36
 * @description CyclicBarrier的await方法与reset
 * @program ThreadTest
 */
public class CyclicBarrier {
    private static java.util.concurrent.CyclicBarrier cyclicBarrier = new java.util.concurrent.CyclicBarrier(6,
                                                                                                             () -> System.out.println("这里先执行我的方法"));

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    // 当CyclicBarrier完成一次计数后会reset并开始一次新的计数，一旦计数完成，CyclicBarrier初始化时自定义的Runnable会执行
                    System.out.println("我开始等待了，线程为：" + Thread.currentThread().getName());
                    cyclicBarrier.await();
                    System.out.println("我又开始执行了，线程为：" + Thread.currentThread().getName());
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "SnAiL_Thread_" + i).start();
        }
    }

    // 注意CyclicBarrier的await方法会抛出的两个异常含义
}
