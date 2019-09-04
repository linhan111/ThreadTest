package com.github.linhan111.T201909;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试场景：测试parallelStream是无序的，且多线程执行
 *
 * @author lhan111
 */
public class ParallelStreamTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        long startTime = System.currentTimeMillis();
        // 这里换成stream则遍历为串行
        list.parallelStream().filter(p -> p.compareTo(10) >= 0).forEach(p -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignore) {
            }
            System.out.println(p);
        });
        System.out.println("执行时间为：" + (System.currentTimeMillis() - startTime));
    }
}
