package com.github.linhan111.T20181214;

import lombok.NoArgsConstructor;

/**
 * @author lhan
 * @version 1.0.0
 * @className Sington
 * @date 18-12-21 下午2:16
 * @description 单例模式几种写法对比，注意双重校验锁的方式与该方式造成的问题和解决方案
 * @program Singleton
 */
@NoArgsConstructor
public class Singleton {
    // 线程不安全的这种
    /*private static Singleton instance;

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }*/


    // 效率不高的这种，每次获取instance实例都需要获取锁，不管该实例是否已初始化
   /* private static Singleton instance;

    public synchronized static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }*/

    // 双重校验锁的实现方式，在synchronized中在加一次判断的原因是多线程情况下，即使用同步关键字锁住了创建instance的方法，但可能有线程A执行了该同步方法，线程B也执行了该方法，即创建了多个实例（多个线程一起进入了同步块外的if）
    // 这种方法由于jvm中的指令重排，instance = new Singleton(); 这句可能被jvm进行重排序，导致另一个线程使用instance实例时该对象并未初始化，这种方式的解决方法是将instance方法设置为volatile，保证该对象的变更对其他线程可见，且禁止指令重排
    /*private static Singleton instance;

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }*/

    // 这种饿汉式的初始化方式保证了多线程情况下的实例唯一，但会存在一个问题，第一次加载类时就会对instance实例化，效率不高，且如果该实例的创建依赖其他参数或配置文件，则会创建失败
    /*private static final Singleton instance = new Singleton();

    public static Singleton getInstance () {
        return instance;
    }*/

    // 这种写法仍然使用JVM本身机制保证了线程安全问题；由于 SingletonHolder 是私有的，除了 getInstance() 之外没有办法访问它，因此它是懒汉式的；同时读取实例的时候不会进行同步，没有性能缺陷；也不依赖 JDK 版本
    private static class SingletonHolder {
        private static Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.instance;
    }

    // 还有一种根据枚举的方式来实现单例的使用较少，了解就去网上找资料看吧
}
