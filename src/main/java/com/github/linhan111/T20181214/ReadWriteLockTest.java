package com.github.linhan111.T20181214;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lhan
 * @version 1.0.0
 * @className ReadWriteLockTest
 * @date 18-12-10 下午5:41
 * @description ReentrantReadWriteLock的使用：https://www.jianshu.com/p/9cd5212c8841
 * @program ThreadTest
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        /*ReentrantReadWriteLock rtLock = new ReentrantReadWriteLock();
        rtLock.readLock().lock();
        System.out.println("get readLock.");
        rtLock.writeLock().lock();
        // 这里产生了死锁，未释放读锁的情况下申请获得写锁，即为锁升级，在ReentrantReadWriteLock中不支持锁升级
        System.out.println("blocking");*/

        // 锁降级，在未释放写锁情况下申请获取读锁，支持锁降级
        ReentrantReadWriteLock rtLock1 = new ReentrantReadWriteLock();
        rtLock1.writeLock().lock();
        System.out.println("get write Lock");
        rtLock1.readLock().lock();
        System.out.println("get read lock");
    }
}
