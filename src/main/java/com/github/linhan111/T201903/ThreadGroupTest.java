package com.github.linhan111.T201903;

/**
 * @author lhan
 * @version 1.0.0
 * @className ThreadGroupTest
 * @date 19-3-19 上午10:30
 * @description 测试ThreadGroup使用，创建线程时最好加上线程组和线程名，方便调试
 * @program ThreadTest
 */
public class ThreadGroupTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group = new ThreadGroup("SnAiL_group");
        Thread t1 = new Thread(group, () -> System.out.println(Thread.currentThread().getThreadGroup().getName() + "---" + Thread.currentThread().getName()),
                               "SnAiL_Thread_1");
        Thread t2 = new Thread(group, () -> System.out.println(Thread.currentThread().getThreadGroup().getName() + "---" + Thread.currentThread().getName()),
                               "SnAiL_Thread_2");
        t1.start();
        t2.start();
        /*t1.join(); 这里让t1和t2执行完成后活动线程就为0了
        t2.join();*/
        System.out.println("活动线程有：" + group.activeCount());
        group.list();
    }
}
