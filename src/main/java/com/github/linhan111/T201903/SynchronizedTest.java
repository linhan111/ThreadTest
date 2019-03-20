package com.github.linhan111.T201903;

/**
 * @author lhan
 * @version 1.0.0
 * @className SynchronizedTest
 * @date 19-3-19 下午2:36
 * @description Synchronized测试，作用于静态方法上等
 * @program ThreadTest
 */
public class SynchronizedTest implements Runnable {
    static int i = 0;

    // 如果这里不是static方法，则每个SynchronizedTest实例并未使用到同步关键字同步，只能将这里设置为static方法，用java特性（static方法只加载一次）来保证线程安全
    private static synchronized void increase() {
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new SynchronizedTest());
        Thread t2 = new Thread(new SynchronizedTest());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
