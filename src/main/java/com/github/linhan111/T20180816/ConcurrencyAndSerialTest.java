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

    // 减少上下文切换的方法有：无锁并发编程、cas算法、使用最少线程、使用协程
    // 无锁并发编程：多线程竞争锁时，会引起上下文切换所以使用多线程处理数据时，可以使用一些方法来避免锁的使用，如将数据的id按照hash算法取模分段，不同的线程可以处理不同段的数据，典型应用有CurrencyHashMap.
    // cas算法：Java的Atomic包使用cas算法来更新数据而不需要加锁
    // 使用最少线程：避免创造不必要的线程，任务很少而创建了很多线程会使线程长时间处于等待状态
    // 协程：在单线程中实现多任务的调度，并在单线程中维持多任务的切换
    // 这是在work-vmware-deepin环境配置时的测试提交，基本开发环境测试通过后考虑将deepin做成双系统，在java开发中确实使用linux效率上有提升
}

