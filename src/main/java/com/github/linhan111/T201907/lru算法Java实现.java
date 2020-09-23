package com.github.linhan111.T201907;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 本地实现一个lru算法，同时参考guava的本地缓存，看下他的lru如何实现的
 */
public class lru算法Java实现 {

    private final static int initSize = 3;
    private static LinkedHashMap<Integer, Integer> lruMap = new LinkedHashMap<Integer, Integer>(initSize + 1, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > initSize;
        }
    };

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            lruMap.put(i, i);
        }
        lruMap.get(0);
        lruMap.get(2);
        lruMap.put(3, 3);
        lruMap.forEach((k, v) -> System.out.println("key is : " + k + ", value is : " + v));
    }
}