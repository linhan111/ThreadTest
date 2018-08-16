package com.github.linhan111.T20180816;

/**
 * 测试串行和并行两者情况的耗时区别
 * <p>
 * 并行会有上下文切换等耗时操作，在一定循环次数内并行效率比串行低
 *
 * @author lhan
 * @date 2018/8/16
 */
public class ConcurrencyAndSerialTest {
    private static long count = 1_000_000_000L;

    public static void main(String[] args) throws InterruptedException {
        concurrency();
        serial();
    }

    private static void concurrency() throws InterruptedException {
        // 记录线程开始时间
        long startTime = System.currentTimeMillis();
        // 新开一个线程去执行（阿里编码手册上建议使用线程池的方式来创建线程！）
        Thread thread = new Thread(() -> {
            int a = 0;
            for (int i = 0; i < count; i++) {
                a += 5;
            }
        });
        thread.start();
        int b = 0;
        for (int i = 0; i < count; i++) {
            b--;
        }
        // 新开的thread线程执行完成后再执行主线程
        thread.join();
        long time = System.currentTimeMillis() - startTime;
        System.out.println("并行所需时间：" + time);
    }

    private static void serial() throws InterruptedException {
        long start = System.currentTimeMillis();
        int a = 0;
        for (int i = 0; i < count; i++) {
            a += 5;
        }
        int b = 0;
        for (int i = 0; i < count; i++) {
            b--;
        }
        System.out.println(" 串行所需时间：" + (System.currentTimeMillis() - start));
    }
}

