package com.github.linhan111.T201908;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 对Fork-join框架的测试，测试处理size为50的List，假设处理每条数据的耗时为100ms，对比单线程与并行处理的耗时
 * 参考链接：https://www.liaoxuefeng.com/article/1146802219354112
 * 需要去了解下原理，使用更顺手！
 *
 * @author linhan111
 * @date 2019-08-22 15:41
 */
public class ForkJoinTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(100);
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        long startTime1 = System.currentTimeMillis();
//        long xxx = list.stream().reduce(0, Integer::sum);
        long baseResult = 0;
        for (Integer integer : list) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignore) {
            }
            baseResult += integer;
        }
        System.out.println("串行执行时间：" + (System.currentTimeMillis() - startTime1) + "，result为：" + baseResult);

        long startTime2 = System.currentTimeMillis();

        Task task = new Task(0, list.size(), list);
        // 重要！！
        // 总共50个任务，大于等于阈值（10）则fork一半出去，最后计算出来，只需8个线程并发执行即可，所以这里线程池的配置若大于8则耗时固定，若小于8则有线程需要执行多次，耗时增加
        //
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);

        // 这里会阻塞主线程获取结果
        Long result = forkJoinPool.invoke(task);

        System.out.println("fork-join执行时间：" + (System.currentTimeMillis() - startTime2) + "，result为：" + result);
    }

    // 没有返回值的Fork-join类：RecursiveAction
    @AllArgsConstructor
    static class Task extends RecursiveTask<Long> {
        private int start;

        private int end;

        private List<Integer> tempList;

        @Override
        protected Long compute() {
            long sum = 0;
            // 粒度小于10直接执行，如果不小于10，则fork一半出去（再次分解，其他线程执行另一半数据），线程总数为：ForkJoinPool初始化时定义的值
            if (end - start < 10) {
                for (int i = start; i < end; i++) {
                    // function(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sum += tempList.get(i);
                }
                return sum;
            } else {
                int mid = (start + end) / 2;
                Task leftTask = new Task(start, mid, this.tempList);
                // 注意这里需要修改mid + 1为mid
                Task rightTask = new Task(mid, end, this.tempList);

                Task.invokeAll(leftTask, rightTask);

                Long x = leftTask.join();
                Long y = rightTask.join();
                return x + y;
                /*leftTask.fork();
                return rightTask.compute() + leftTask.join();*/
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
        // TODO 考虑下如果Fork-join任务中存在对集合的并发写如何处理？
        // TODO 注意执行过程中的异常处理
    }
}
