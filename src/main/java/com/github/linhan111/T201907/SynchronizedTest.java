package com.github.linhan111.T201907;

/**
 * 测试sync关键字作用在class/this/object上的区别
 * 参考链接：https://blog.csdn.net/luckey_zh/article/details/53815694
 *          下面这两篇blog需要好好看看，讲解的很清楚
 *          https://www.cnblogs.com/huansky/p/8869888.html
 *          https://juejin.im/post/594a24defe88c2006aa01f1c
 */
public class SynchronizedTest {

    private static void method1() throws InterruptedException {
        // 建议使用锁对象实例的方式，更安全；可控的情况下可以使用this关键字，不过需要代码验证
        synchronized (SynchronizedTest.class) {
            System.out.println("A begin time=" + System.currentTimeMillis() + "当前线程为=" + Thread.currentThread().getName());
            Thread.sleep(2000);
            System.out.println("A end time=" + System.currentTimeMillis() + "当前线程为=" + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    method1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
