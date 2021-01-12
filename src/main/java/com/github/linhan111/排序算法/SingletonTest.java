package com.github.linhan111.排序算法;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 单例模式（lazy-load）
 */
public class SingletonTest {

    /**
     * 类级的内部类
     * 也就是静态的成员式内部类，该内部类的实例与外部类的实例没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static SingletonTest instance = new SingletonTest();
    }

    /**
     * 私有化构造方法，防止其他类调用
     */
    private SingletonTest() {
    }

    public static SingletonTest getInstance() {
        return SingletonHolder.instance;
    }

}
