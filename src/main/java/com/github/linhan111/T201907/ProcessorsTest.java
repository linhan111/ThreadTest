package com.github.linhan111.T201907;


/**
 * @author linhan111
 */
public class ProcessorsTest {
    public static void main(String[] args) {
        // 4C8T的cpu，availableProcessors为线程数而非核心数
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(Runtime.getRuntime().freeMemory());
        System.out.println(Runtime.getRuntime().totalMemory());
//         jvm 启动参数为：
//        -Xms20m
//        -Xmx20m
//        -Xmn10M
    }
}
