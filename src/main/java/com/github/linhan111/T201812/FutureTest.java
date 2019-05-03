package com.github.linhan111.T201812;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author lhan
 * @version 1.0.0
 * @className FeatureTest
 * @date 19-1-19 下午5:22
 * @description 测试同步异步执行的CompletableFuture
 * @program ThreadTest
 */
public class FutureTest {

    private static final ExecutorService executorService = new ThreadPoolExecutor(3, 10, 3L, TimeUnit.SECONDS,
                                                                                  new LinkedBlockingQueue<>(20),
                                                                                  new ThreadFactoryBuilder().setNameFormat("test-%d").build());

    public static void main(String[] args) throws Exception {
        long q = System.currentTimeMillis();
        Thread.sleep(2000);
        // 线程池处理耗时任务，那么这段代码执行耗时为：串行代码执行时间+新开线程jvm产生的耗时
        executorService.execute(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("耗时为：" + (System.currentTimeMillis() - q));


        long p = System.currentTimeMillis();
        Thread.sleep(2000);
        // 如果上面改成如下形式，使用submit后调用get方法会等待执行结果，这种情况的时间消耗为：串行时间+新开线程执行的耗时+开线程等jvm产生的耗时
        // 因为调用了Future.get()方法会使当前线程等待执行结果，所以效率还不如串行执行
        executorService.submit(() -> {
            Thread.sleep(3000);
            return "";
        }).get();
        // 若在get方法中指定了等待时间，则在等待时间结束后还未能拿到结果会抛出timeoutException，不影响后续流程的话可以使用catch抓住

        System.out.println("耗时为：" + (System.currentTimeMillis() - p));
    }
}
