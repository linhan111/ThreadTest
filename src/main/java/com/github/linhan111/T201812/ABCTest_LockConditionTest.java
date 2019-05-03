package com.github.linhan111.T201812;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lhan
 * @version 1.0.0
 * @className LockConditionTest
 * @date 18-12-14 上午11:05
 * @description 三个线程交替打印ABC问题，使用Condition来实现，可自定义线程等待（等待时会释放当前线程获取的锁），线程唤醒（调用signal时会重新请求锁）
 * @program ThreadTest
 */
public class ABCTest_LockConditionTest {
    static ReentrantLock lock = new ReentrantLock();

    /**
     * 定义多个条件控制不同线程的执行顺序（类似Object的wait与notify）
     */
    static Condition condition1 = lock.newCondition();
    static Condition condition2 = lock.newCondition();
    static Condition condition3 = lock.newCondition();

    /**
     * 定义重复次数
     */
    static int count = 0;

    public static void main(String[] args) {
        new Thread(new runnable3(), "线程3").start();
        new Thread(new runnable2(), "线程2").start();
        new Thread(new runnable1(), "线程1").start();
    }

    static class runnable1 implements Runnable {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    // 这里使用while代替if，每次线程都会去判断条件是否满足（使用while避免虚假唤醒），参考：https://blog.csdn.net/qq_36974281/article/details/81951006
                    while (count % 3 != 0) {
                        condition1.await();
                    }
                    System.out.println("当前线程为：" + Thread.currentThread().getName() + "，输出为：A");
                    count++;
                    condition2.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class runnable2 implements Runnable {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    while (count % 3 != 1) {
                        condition2.await();
                    }
                    System.out.println("当前线程为：" + Thread.currentThread().getName() + "，输出为：B");
                    count++;
                    condition3.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class runnable3 implements Runnable {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    while (count % 3 != 2) {
                        condition3.await();
                    }
                    System.out.println("当前线程为：" + Thread.currentThread().getName() + "，输出为：C");
                    count++;
                    condition1.signal();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    // 备注：Condition在jdk中的使用可查看阻塞队列源码，例如ArrayBlockingQueue等，源码解析：https://www.cnblogs.com/tjudzj/p/4454490.html
}
