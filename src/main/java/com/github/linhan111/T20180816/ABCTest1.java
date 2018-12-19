package com.github.linhan111.T20180816;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lhan
 * @version 1.0.0
 * @className ABCTest1
 * @date 18-12-7 下午4:30
 * @description 这种循环打印abc的实现方式会多出很多次循环，对性能影响较大
 * @program ThreadTest
 */
public class ABCTest1 {
    private static Lock lock = new ReentrantLock(); // 通过Lock来保证线程的访问的互斥
    private static int state = 0;

    public static void main(String[] args) {
        new ThreadC().start();
        new ThreadB().start();
        new ThreadA().start();
    }

    static class ThreadA extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; ) {
                try {
                    lock.lock();
                    while (state % 3 == 0) { // 多线程并发，不能用if，必须用循环测试等待条件，避免虚假唤醒（while与if的区别，每次执行前都会判断循环条件是否满足）
                        System.out.println("A");
                        state++;
                        i++;
                    }
                } finally {
                    lock.unlock(); // lock()和unlock()操作结合try/catch使用
                }
            }
        }
    }

    static class ThreadB extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; ) {
                try {
                    lock.lock();
                    while (state % 3 == 1) {
                        System.out.println("B");
                        state++;
                        i++;
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class ThreadC extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; ) {
                try {
                    lock.lock();
                    while (state % 3 == 2) {
                        System.out.println("C");
                        state++;
                        i++;
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}