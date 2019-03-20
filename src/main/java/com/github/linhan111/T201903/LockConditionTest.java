package com.github.linhan111.T201903;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lhan
 * @version 1.0.0
 * @className LockConditionTest
 * @date 19-3-20 下午2:22
 * @description Condition测试
 * @program ThreadTest
 */
public class LockConditionTest {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                condition.await();
                System.out.println("Thread is going on.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "Thread_condition");
        t1.start();
        // 如果这里main线程不休眠2s，则代码会直接执行34-37行，从而Thread_condition线程执行了await后永远不能被唤醒
        Thread.sleep(2000);
        //通知线程t1继续执行
        lock.lock();
        System.out.println("Thread wait.");
        condition.signal();
        lock.unlock();
    }
}
