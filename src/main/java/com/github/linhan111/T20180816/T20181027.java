package com.github.linhan111.T20180816;

/**
 * @author lhan
 * @version 1.0.0
 * @className T20181026
 * @date 18-10-26 上午11:06
 * @description 创建线程的两种方式（注意都可使用lambda表达式来简化编程）：http://ifeve.com/creating-and-starting-java-threads/
 * @program ThreadTest
 */
public class T20181027 {
    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println("线程名为：" + Thread.currentThread().getName()
                                                             + "线程优先级为：" + Thread.currentThread().getPriority());
        for (int i = 0; i < 10; i++) {
            // ali编码规范推荐使用线程池来代替显式创建线程
            new Thread(runnable).start();
            // 注意这里run方法调用的输出，run方法是当前线程调用，不会启用创建的线程
            new Thread(runnable).run();
        }

        Thread thread = new Thread(() -> System.out.println("线程名为：" + Thread.currentThread().getName()
                                                                    + "，线程id为：" + Thread.currentThread().getId()
                                                                    + "，线程优先级为：" + Thread.currentThread().getPriority()));
        for (int i = 0; i < 10; i++) {
            new Thread(thread, "这里是自定义的线程名字").start();
        }
    }
}
