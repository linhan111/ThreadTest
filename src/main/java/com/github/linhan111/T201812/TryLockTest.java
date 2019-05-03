package com.github.linhan111.T201812;

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

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new runnable(), "Thread-" + i).start();
        }
    }

    static class runnable implements Runnable {
        @Override
        public void run() {
            try {
                // 假定同时开启十个线程模拟并发情况，在十个线程中，获取锁的等待时间最大为2000毫秒，第一个线程进来休眠300毫秒，那个这种情况下最多只能4个线程能获取锁
                // 使用场景：在已知业务代码大致执行时间后，可硬编码的方式控制该接口的并发量，硬编码的方式优劣都有，自己分析吧
                if (lock.tryLock(2000, TimeUnit.MILLISECONDS)) {
                    System.out.println("当前获取锁的线程为：" + Thread.currentThread().getName());
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
}
