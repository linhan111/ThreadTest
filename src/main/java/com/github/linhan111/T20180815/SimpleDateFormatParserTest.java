package com.github.linhan111.T20180815;

import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试SimpleDateFormat多线程情况下的问题
 *
 * 原文链接：https://www.cnblogs.com/zuoxiaolong/p/con1.html
 * PS:注意评论区讨论的static变量对内存的影响及多线程情况下static变量的问题
 *
 * SimpleDateFormat时间格式化的线程安全问题解析：https://www.cnblogs.com/zhuimengdeyuanyuan/archive/2017/10/25/7728009.html
 *
 * @author lhan
 * @date 2018/8/15
 */
@NoArgsConstructor
public class SimpleDateFormatParserTest {
    private static final DateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);


    public static void main(String[] args) {
        // 由阿里编码规范，线程池需要显示创建，方便阅读查错
        ExecutorService service = new ThreadPoolExecutor(100, 200, 10L,
                                                         TimeUnit.SECONDS,
                                                         new LinkedBlockingDeque<Runnable>(),
                                                         r -> new Thread(r, "test thread:" + ATOMIC_INTEGER.incrementAndGet()));
        for (int i = 0; i < 100; i++) {
            service.execute(() -> {
                for (int j = 0; j < 100; j++) {
                    try {
                        Thread.sleep(1000L);
                        // SimpleDateFormat由类注释409行可以看出是未同步的，多线程情况下需要在外部进行同步或每个线程一个实例
                        // 以下代码多线程环境下会产生问题
                        /// SIMPLE_DATE_FORMAT.parse("2018-08-15 10:10:10");
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-08-15 10:10:10");
                        System.out.println(Thread.currentThread().getName());
                        System.out.println(Thread.currentThread().getName());
                    } catch (ParseException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
