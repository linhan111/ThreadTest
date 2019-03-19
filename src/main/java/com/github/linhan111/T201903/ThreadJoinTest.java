package com.github.linhan111.T201903;

/**
 * @author lhan
 * @version 1.0.0
 * @className ThreadJoinTest
 * @date 19-3-18 下午1:51
 * @description 测试Thread中join方法
 * @program ThreadTest
 */
public class ThreadJoinTest {
    public volatile static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            // for (int i = 0; i < 10; i++); 如果这里是使用的局部变量，那最后的输出就为0
            for (i = 0; i < 10; i++) {
                // System.out.println(i);
            }
        });
        t1.start();
        t1.join();
        System.out.println(i);
    }
}
