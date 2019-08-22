package com.github.linhan111.T201908;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 对Fork-join框架的测试，测试处理size为50的List，假设处理每条数据的耗时为100ms，对比单线程与并行处理的耗时
 * 参考链接：https://www.liaoxuefeng.com/article/1146802219354112
 * https://www.jianshu.com/p/61fe0c5e540a
 * 需要去了解下原理，使用更顺手！
 *
 * @author linhan111
 * @date 2019-08-22 15:41
 */
public class ForkJoinTest1 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(100);
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }

        long startTime2 = System.currentTimeMillis();

        Task task = new Task(0, list.size(), list);
        // 重要！！
        // 总共50个任务，大于等于阈值（10）则fork一半出去，最后计算出来，只需8个线程并发执行即可，所以这里线程池的配置若大于8则耗时固定，若小于8则有线程需要执行多次，耗时增加
        //
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);

        // 这里会阻塞主线程获取结果
        List<Integer> result = forkJoinPool.invoke(task);

        // 如果不在worker线程中catch异常，则会中断其他线程的执行
        /*if (task.isCompletedAbnormally()) {
            if (task.getException() != null) {
                System.out.println(task.getException());
            }
        }*/

        System.out.println("fork-join执行时间：" + (System.currentTimeMillis() - startTime2) + "，result为：" + result);
    }

    @AllArgsConstructor
    static class Task extends RecursiveTask<List<Integer>> {
        private int start;

        private int end;

        private List<Integer> tempList;

        @Override
        protected List<Integer> compute() {
            // 粒度小于10直接执行，如果不小于10，则fork一半出去（再次分解，其他线程执行另一半数据），线程总数为：ForkJoinPool初始化时定义的值
            List<Integer> q = new ArrayList<>(10);
            try {
                if (end - start < 10) {
                    for (int i = start; i < end; i++) {
                        // function(i);
                        try {
                            // FIXME 为什么这里不同worker线程休眠不同时间，并行结果仍是有序的？！！1
                            Thread.sleep(100 + new Random().nextInt(100));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        q.add(tempList.get(i));
                    }
                    System.out.println(start + "-" + end);
                    if (end == 43) {
                        int x = 2 / 0;
                    }
                    return q;
                } else {
                    int mid = (start + end) / 2;
                    Task leftTask = new Task(start, mid, this.tempList);
                    Task rightTask = new Task(mid, end, this.tempList);

                    Task.invokeAll(leftTask, rightTask);

                    List<Integer> x = leftTask.join();
                    List<Integer> y = rightTask.join();
                    x.addAll(y);
                    return x;
                }
            } catch (Exception e) {
                // catch异常，并返回空的执行结构（记录日志或做其他补偿操作），最后对结果归集的时候自然缺少这个发生异常的worker线程的数据
                return new ArrayList<>();
            }
        }

        /*private void function(int i) {
            try {
                System.out.println("Thread:" + Thread.currentThread().getId() + ", i= " + i);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        // 考虑下如果Fork-join任务中存在对集合的并发写如何处理？
        // 答：在每个子任务处理中返回集合，最后合并的时候合并所有集合，不会有线程安全问题，且由于ForkJoinPool的初始化时默认为LIFO_QUEUE模式，则各个线程返回的集合可认为是有序的，

        // FIXME 如这个例子中最后合并的List中是0-49的有序集合，需要分析下原理

        // 注意执行过程中的异常处理！！！
    }
}
