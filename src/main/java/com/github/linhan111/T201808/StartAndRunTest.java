package com.github.linhan111.T201808;

/**
 * @author lhan
 * @version 1.0.0
 * @className StartAndRunTest
 * @date 18-12-10 下午4:40
 * @description Thread中run方法与start方法的区别，参考链接：https://www.cnblogs.com/whyalwaysme/p/4495959.html
 * @program ThreadTest
 */
public class StartAndRunTest extends Thread {
    public static void main(String args[]) {
        StartAndRunTest t1 = new StartAndRunTest();
        StartAndRunTest t2 = new StartAndRunTest();

        t1.run();
        t2.run();
        // 每个线程在一个单独的调用堆栈中启动
        // 这里注意run方法与start方法的区别，使用run方法不会新开线程执行，相当于在主线程中调用run()方法，run()方法进入当前调用堆栈而不是新调用堆栈的开头（）
        // t1.run();
        // t2.run();
    }

    @Override
    public void run() {
        for (int i = 1; i < 5; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(i);
            System.out.println(Thread.currentThread().getName());
        }
    }
}
