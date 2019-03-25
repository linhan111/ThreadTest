package com.github.linhan111.T201903;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lhan
 * @version 1.0.0
 * @className SelfDefinedThreadPoolExecutor
 * @date 19-3-25 下午5:24
 * @description 扩展ThreadPoolExecutor方法，可在任务执行前后及销毁时实现特定逻辑（跟踪状态、输出调试信息方便查错等）
 * @program ThreadTest
 */
public class SelfDefinedThreadPoolExecutor {
    public static void main(String[] args) {
        ThreadPoolExecutor xz = new ThreadPoolExecutor(5, 5, 60L,
                                                       TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), r -> {
            Thread t = new Thread(r);
            t.setPriority(10);
            return t;
        }) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行：" + r);
                super.beforeExecute(t, r);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行完成：" + r);
                super.afterExecute(r, t);
            }

            @Override
            protected void terminated() {
                System.out.println("线程池退出");
                super.terminated();
            }
        };
        for (int i = 0; i < 5; i++) {
            xz.submit(() -> System.out.println("这是线程池的任务"));
        }
    }
}
