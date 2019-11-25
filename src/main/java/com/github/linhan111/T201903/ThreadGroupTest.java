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
        // 这种创建方式无法设置线程组统一的异常处理，采用extends ThreadGroup并重写方法的方式可以设置，参考下面的实现！
        ThreadGroup group = new ThreadGroup("SnAiL_group");

        ThreadGroup group1 = new MyThreadGroup("this is test.");

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

    /**
     * 也可以通过这种方式创建线程组，注意线程组中异常的处理
     */
    private static class MyThreadGroup extends ThreadGroup {
        public MyThreadGroup(String name) {
            super(name);
        }

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("捕获到异常了：" + e);
        }
    }


    // 线程的异常处理：https://juejin.im/post/5dcd694d51882510a2331317
}
