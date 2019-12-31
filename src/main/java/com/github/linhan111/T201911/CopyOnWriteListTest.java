package com.github.linhan111.T201911;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteListTest {
    public static void main(String[] args) {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList<String>();

        new Thread(() -> {
            copyOnWriteArrayList.add("this is test1");
        }).start();

        new Thread(() -> {
            System.out.println(copyOnWriteArrayList.get(0));
        }).start();

        new Thread(() -> {
            copyOnWriteArrayList.add("this is test2");
        }).start();

        new Thread(() -> {
            System.out.println(copyOnWriteArrayList.get(1));
        }).start();

        new Thread(() -> {
            copyOnWriteArrayList.add("this is test3");
        }).start();


        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                copyOnWriteArrayList.add("");
            }).start();
        }

        System.out.println(copyOnWriteArrayList.size());
    }
}
