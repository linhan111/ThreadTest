package com.github.linhan111.T201907;

public class ThreadLocalTest {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set(Thread.currentThread().getName());
        // key为当前threa，value为set的值，会覆盖，注意ThreadLocalMap中的Entry保持的是和当前线程的WeakReference，方便gc
        threadLocal.set("this is test");
        System.out.println(threadLocal.get());

        new Thread(() -> {
            threadLocal.set("新开线程测试");
            System.out.println(threadLocal.get());
//            threadLocal.remove();
        }).start();
    }

    // 注意ThreadLocal的内存溢出问题，WeakReference中的key-value，value保持了一个强引用，
    // 在线程回收之前不会回收这一块堆内存，通过声明ThreadLocal为static或手动调用remove解决
    // 参考：https://www.jianshu.com/p/250798f9ff76
}
