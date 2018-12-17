package com.github.linhan111.T20181214;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lhan
 * @version 1.0.0
 * @className TryLockTest
 * @date 18-12-13 下午3:28
 * @description 测试Lock中tryLock方法，可实现获取锁超时放弃功能
 * @program ThreadTest
 */
public class TryLockTest {
    private static ReentrantLock lock = new ReentrantLock();

    static class runnable implements Runnable {
        @Override
        public void run() {
            try {
                if (lock.tryLock(2000, TimeUnit.MILLISECONDS)) {
                    System.out.println("当前获取到锁的线程为：" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("未获得锁的线程为：" + Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                System.out.println("tryLock发生异常，e={}" + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new runnable(), "Thread-" + i).start();
        }
    }
}
