package com.github.linhan111.T201907;

/**
 * happen-before原则：
 * https://ifeve.com/java-%e4%bd%bf%e7%94%a8-happen-before-%e8%a7%84%e5%88%99%e5%ae%9e%e7%8e%b0%e5%85%b1%e4%ba%ab%e5%8f%98%e9%87%8f%e7%9a%84%e5%90%8c%e6%ad%a5%e6%93%8d%e4%bd%9c/
 */
public class hbTest {
    static int a = 1;

    public static void main(String[] args){
        Thread tb = new Thread(() -> {
            System.out.println(a);
        });
        Thread ta = new Thread(() -> {
            tb.start();
            a = 2;
        });

        ta.start();
    }
}
