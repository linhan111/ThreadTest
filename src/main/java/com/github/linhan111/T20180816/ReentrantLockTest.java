package com.github.linhan111.T20180816;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lhan
 * @version 1.0.0
 * @className ReentrantLockTest
 * @date 18-12-7 上午11:45
 * @description 测试ReentrantLock特性，注意可重入锁与synchronized的区别，同时注意读写锁的使用等，详细参考：https://www.cnblogs.com/takumicx/p/9338983.html
 * @program ThreadTest
 */
public class ReentrantLockTest {
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("当前Thread为：" + Thread.currentThread().getName());
        lock.lock();
        // ali编码推荐使用线程池来实现
        new Thread(new SignalThread(), "测试线程").start();
        try {
            System.out.println("主线程等待通知");
            condition.await();
        } finally {
            lock.unlock();
        }
        System.out.println("主线程恢复运行");
    }

    static class SignalThread implements Runnable {
        @Override
        public void run() {
            System.out.println("当前Thread为：" + Thread.currentThread().getName());
            lock.lock();
            try {
                condition.signal();
                System.out.println("子线程通知");
            } finally {
                lock.unlock();
            }
        }
    }
}
