package com.github.linhan111.TCWork;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public interface CandiceConcurrentExecutor<T, E> {

    /**
     * Run process concurrently and get all of result after all of thread has been completed
     *
     * @param concurrentCollection the data collection the process handle with
     * @return all of handling result by different thread
     */
    default List<T> run(Collection<E> concurrentCollection) {
        final List<T> resultList = new ArrayList<>();
        if (concurrentCollection == null || concurrentCollection.size() == 0) {
            return resultList;
        }
        // build CompletableFuture to execute any process by async
        List<CompletableFuture<List<T>>> countCompletableFutures =
                concurrentCollection.stream().map(elem -> getFuture(elem)).collect(Collectors.toList());
        CompletableFuture<Void> allCountFutures = CompletableFuture.allOf(
                countCompletableFutures.toArray(new CompletableFuture[0]));

        // waiting for computer result for each CompletableFuture
        allCountFutures.join();
        CompletableFuture<List<List<T>>> allCompletableFeature = allCountFutures.thenApply(future -> {
            return countCompletableFutures.stream().map(countCompletableFuture -> countCompletableFuture.join())
                    .collect(Collectors.toList());
        });

        // if the result list is not null, append the processing result to the summary list
        CompletableFuture result = allCompletableFeature.thenApply(countLists -> {
            return countLists.stream().filter(Objects::nonNull)
                    .map(eachCountList -> resultList.addAll(eachCountList)).collect(Collectors.toList());
        });
        return resultList;
    }

    /**
     * Get feature for computing process for any type
     *
     * @param elem
     * @return
     */
    CompletableFuture<List<T>> getFuture(E elem);
}
