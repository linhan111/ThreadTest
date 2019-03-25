package com.github.linhan111.T201903;

/**
 * @author lhan
 * @version 1.0.0
 * @className ThreadPoolTest
 * @date 19-3-21 下午2:42
 * @description 测试子线程中抛出异常是否会影响主线程的执行，参考文档：https://www.cnblogs.com/yangfanexp/p/7594557.html
 * @program ThreadTest
 */
public class ThreadExceptionTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> Integer.parseInt("34L"), "test_thread.");
        t1.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("当前线程为:" + t.getName() + "，异常堆栈为：" + e);
            }
        });
        t1.start();
        Thread.sleep(5000);
        System.out.println("这是main线程执行完成后的输出，主线程感知不到子线程的错误。");
    }
    // 根据java的设计，子线程中抛出的异常不会影响到主线程，异常捕获有几种方式：
    // 1、在子线程中使用try-catch
    // 2、定义子线程或全局默认的未捕获异常处理器UncaughtExceptionHandler
    // 3、使用Future的get方法来捕获
    // （注意UncaughtExceptionHandler：若当前线程抛出UncheckException则回调当前线程的，否则回调线程组的，否则回调默认全局默认的，如果都没有则子线程会退出）
}
