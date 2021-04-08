package com.github.linhan111.TCWork;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * FutureTaskWorker，使用CompletableFuture搜集结果，参考：https://blog.csdn.net/teachy/article/details/104971814
 * 这里只适用于多个任务返回值都相同的情况，可以避免定义多个CompletableFuture来用allOf搜集结果（如果业务上有些异步任务返回List类型，有些返回字符串类型，或者任务的处理逻辑不一致，就不适用这种方法）
 *
 * @author 林焓53411
 * @since 2021-01-11
 */
@Data
@AllArgsConstructor
public class FutureTaskWorker {

    private static ExecutorService executorService = new ThreadPoolExecutor(4, 32, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(1000), new ThreadFactoryBuilder().setNameFormat("FutureTaskWorker-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());


    /**
     * ----------------------------------------------------------------------------------------------------------------------
     * <p>
     * <p>
     * 测试这个类的用法，详情参考stellr-common项目中StatisticsCountServiceImpl#getStatisticsCountList
     * 注意CandiceConcurrentExecutor这个和本类的区别，candice的写法可以方便的新建CandiceConcurrentExecutor（通过接口实现的方式），但这两种，应该都只能支持返回值为相同类型的异步任务结果聚合！！！！
     * {@link CandiceConcurrentExecutor#run(Collection)}
     * <p>
     * <p>
     * ----------------------------------------------------------------------------------------------------------------------
     */
    @Test
    public void candiceDemoTest() {
        List<SingleNode> params = new ArrayList<>();
        CandiceConcurrentExecutor<SingleNode, SingleNode> candiceConcurrentExecutor = elem -> CompletableFuture.supplyAsync(ArrayList::new);
        List<SingleNode> resultList = candiceConcurrentExecutor.run(params);
    }

    @Test
    public void getAllResult() {
        List<Long> list = new ArrayList<>(3);
        list.add(1000L);
        list.add(2000L);
        list.add(3000L);
        FutureTaskWorker.FutureTaskWorkerTest<Long, String> futureTaskWorker = new FutureTaskWorker.FutureTaskWorkerTest<>(list, aLong -> getThreadName(aLong));
        long begin = System.currentTimeMillis();
        List<String> allResult = futureTaskWorker.getAllResult();
        long end = System.currentTimeMillis();
        System.out.println(allResult);
        System.out.println("结束耗时:" + (end - begin));
    }


    private CompletableFuture<String> getThreadName(long sleepTime) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(sleepTime);
                System.out.println(Thread.currentThread().getName() + "已经睡眠" + sleepTime + "毫秒");
                return Thread.currentThread().getName();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }, executorService);
    }

    @Data
    @AllArgsConstructor
    private class FutureTaskWorkerTest<T, R> {

        /**
         * 需要异步执行的任务
         */
        private List<T> taskList;

        /**
         * 需要执行的方法
         */
        private Function<T, CompletableFuture<R>> workFunction;


        /**
         * 搜集执行结果
         *
         * @return java.util.List<R>
         */
        private List<R> getAllResult() {
            List<CompletableFuture<R>> futureList = taskList.stream().map(workFunction).collect(Collectors.toList());
            CompletableFuture<Void> allCompletableFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));
            return allCompletableFuture.thenApplyAsync(e -> futureList.stream().map(CompletableFuture::join).collect(Collectors.toList()), executorService).join();
        }
    }
}