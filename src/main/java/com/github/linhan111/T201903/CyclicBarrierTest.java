package com.github.linhan111.T201903;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author lhan
 * @version 1.0.0
 * @className CyclicTest
 * @date 19-3-20 下午4:38
 * @description CyclicBarrier demo，设置屏障保证多少个线程完成后在继续执行，之前完成的线程未到parties数则阻塞
 *
 *
 * important：注意CyclicBarrier与CountDownLatch的使用区别及联系！！！！！！
 *
 *
 * @program ThreadTest
 */
public class CyclicBarrierTest {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(500);
            new Thread(() -> {
                try {
                    System.out.println("当前阻塞线程数量为：" + cyclicBarrier.getNumberWaiting());
                    cyclicBarrier.await();
                    System.out.println("阻塞之后开始执行，线程为：" + Thread.currentThread().getName());
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "Thread_" + i).start();
        }
    }

    // 如果这里改成6，则先开始的6个线程能等待后继续执行，之后的4个线程通过jps+jstack命令查看都是waiting状态，永远无法改变（或调用reset方法重置？？）
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(6, () -> {
        System.out.println("我优先执行，线程为" + Thread.currentThread().getName());
    });
}
