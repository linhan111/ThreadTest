package com.github.linhan111.T201907;

public class ThreadLocalTest {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set(Thread.currentThread().getName());
        // key为当前thread，value为set的值，会覆盖，注意ThreadLocalMap中的Entry保持的是和当前线程的WeakReference，方便gc
        threadLocal.set("this is test");
        System.out.println(threadLocal.get());

        new Thread(() -> {
            ThreadLocal<String> newThreadLocal = new ThreadLocal<>();
            threadLocal.set("新开线程测试");
            System.out.println(threadLocal.get());
            newThreadLocal.set("新线程的threadLocal");
            System.out.println(newThreadLocal.get());
//            threadLocal.remove();
        }).start();

        // 这里还是会输出'this ist test'，原因是ThreadLocal的set是根据线程来关联的，不同线程不影响，但是相同线程会覆盖
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadLocal.get());
    }

    // 注意ThreadLocal的内存溢出问题，WeakReference中的key-value，value保持了一个强引用，
    // 在线程回收之前不会回收这一块堆内存，通过声明ThreadLocal为static或手动调用remove解决
    // 参考：https://www.jianshu.com/p/250798f9ff76

    /*public static void main(String[] args) {
        BigDecimal b = new BigDecimal("1.1");
        BigDecimal x = new BigDecimal(b.intValue());
        System.out.println(x.equals(b));

        // 该方法可判断BigDecimal是否为整数！！！暂时只想到这一种方法
        BigDecimal b1 = new BigDecimal("2");
        BigDecimal x1 = new BigDecimal(b1.intValue());
        System.out.println(x1.equals(b1));
    }*/
}
