package com.github.linhan111.T201808;

/**
 * @author lhan
 * @version 1.0.0
 * @className HappenBeforeTest
 * @date 18-12-12 上午10:46
 * @description 测试JMM的happen-before原则，详细参考：https://ifeve.com/java-%e4%bd%bf%e7%94%a8-happen-before-%e8%a7%84%e5%88%99%e5%ae%9e%e7%8e%b0%e5%85%b1%e4%ba%ab%e5%8f%98%e9%87%8f%e7%9a%84%e5%90%8c%e6%ad%a5%e6%93%8d%e4%bd%9c/
 * @program ThreadTest
 */
public class HappenBeforeTest {
    // 这个num变量不使用volatile关键字也不会存在并发问题！注意这里为什么不会出现这个问题
    static int num = 0;
    static volatile boolean flag = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (; 100 > num; ) {
                if (!flag && (num == 0 || ++num % 2 == 0)) {
                    System.out.println(num);
                    flag = true;
                }
            }
        }
        );

        Thread t2 = new Thread(() -> {
            for (; 100 > num; ) {
                if (flag && (++num % 2 != 0)) {
                    System.out.println(num);
                    flag = false;
                }
            }
        }
        );
        t1.start();
        t2.start();
    }
}
