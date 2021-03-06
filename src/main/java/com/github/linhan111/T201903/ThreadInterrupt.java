package com.github.linhan111.T201903;

/**
 * @author lhan
 * @version 1.0.0
 * @className ThreadInterrupt
 * @date 19-3-28 上午10:42
 * @description 测试下线程中断状态
 * @program ThreadTest
 */
public class ThreadInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("线程开始睡眠");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread_Snail_1");
        System.out.println("子线程开始执行");
        t1.start();
        System.out.println("设置子线程的中断状态为已中断");
        t1.interrupt();
        System.out.println(Thread.interrupted());

        // 注意这个static方法与public void interrupt()这两个方法的区别，可以参考他们的注释文档
        Thread.interrupted();
    }
    // 每个线程都有一个中断标志位，但是只有线程为waiting或者time_waiting状态时设置线程中断状态为true时才会抛出InterruptedException
}
