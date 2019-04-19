package com.github.linhan111.T201904;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 测试有界队列ArrayBlockingQueue使用，主要是两个生产者和消费者谁先启动的问题，是队列装满了才启动消费者还是放入元素就开始消费还是多线程的不确定性，两者都有可能
 * @see java.util.concurrent.LinkedBlockingQueue 注意区分，其队列容量最大为Integer.maxValue
 * 备注：在分布式系统中，锁-队列为了保证程序正常运行都应该被替换为分布式系统中可用的锁与队列（都可用redis实现-对redis的HA属性有要求了）
 */
public class ArrayBlockingQueueTest {
    // 生产者开10个线程，消费者开5个线程，此时队列长度对生产-消费模型有决定性作用。
    // 若队列长度大于生产者线程数，则全部入队后出队
    // 若队列长度小于生产者线程数，则队里满之前入队，满之后生产者线程阻塞，等消费者线程启动后生产者被唤醒，生产者线程唤醒个数为消费者线程数
    private static ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<String>(5);

    public static void main(String[] args) throws InterruptedException {
        for (int x = 0; x < 10; x++) {
            Thread y = new Thread(() -> {
                try {
                    arrayBlockingQueue.put("1");
                    System.out.println("生产者开始生产，放入queue的元素为：" + "1");
                } catch (InterruptedException e) {
                    System.out.println("生产出现异常，堆栈：" + e.getCause());
                }
            });
            y.start();
        }

        // 模拟消费者线程启动延迟，若队列长度小于生产者线程数，则部分生产者现成阻塞
        Thread.sleep(1000);
        for(int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    String result = (String) arrayBlockingQueue.take();
                    System.out.println("消费者开始消费，从queue中取出的元素为：" + result + ",队列中有：" + arrayBlockingQueue.size());
                } catch (InterruptedException e) {
                    System.out.println("消费出现异常，堆栈：" + e.getCause());
                }
            }).start();
        }
    }
}
