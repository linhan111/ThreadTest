package com.github.linhan111.T201903;

/**
 * @author lhan
 * @version 1.0.0
 * @className VolatileTest
 * @date 19-3-19 上午10:01
 * @description 测试volatile关键字，同时了解jvm client模式与server模式
 * @program ThreadTest
 */
public class VolatileTest {
    private static volatile boolean ready = false;
    private static volatile int number = 0;


    public static void main(String[] args) throws InterruptedException {
        // 新开一个线程标志位为true的情况下循环打印，如果ready变量不用volatile那么该线程永远不能看到其他线程对该值的修改，则不会退出循环
        new Thread(() -> {
            while (!ready) {
                System.out.println(number);
            }
        }).start();
        number = 42;
        // 这里会打印100ms的number出来，直到标志位被主线程修改为true
        Thread.sleep(100);
        ready = true;
        Thread.sleep(1000);
        System.out.println(number);

        // 注意：demo上面说若jvm是client模式启动的，新开线程能看到主线程对变量的修改，但在server模式下，线程可见性不能得到保证
        // jvm client模式与server模式对比：https://blog.csdn.net/LMAKE_nbsp/article/details/86552486

    }
}
