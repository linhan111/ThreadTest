package com.github.linhan111.TCWork;

import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * StreamUtil，常用的一些工具类，需要注意{@link java.util.function.Function}包下多个函数式接口的作用
 *
 * @author 林焓53411
 * @since 2021-01-04
 */
public class StreamUtil {
    /**
     * 自定义Predicate来过滤List
     *
     * @param originalList 原始的List
     * @param predicate    {@link Predicate}
     * @param <T>          泛型
     * @return List<T>
     */
    public static <T> List<T> filterListByCustomPredicate(List<T> originalList, Predicate<T> predicate) {
        return originalList.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * 根据Function对List中的对象属性去重
     *
     * @param originalCollection 原始的List
     * @param keyExtractor       {@link Function}
     * @param <T>                泛型
     * @return List<T>
     */
    public static <T> List<T> otherDistinctByKey(List<T> originalCollection, Function<T, Object> keyExtractor) {
        Set<Object> words = Sets.newConcurrentHashSet();
        return originalCollection.stream().filter(t -> words.add(keyExtractor.apply(t))).collect(Collectors.toList());
    }

    /**
     * BiFucntion demo，该function能接收多个参数
     *
     * @param a
     * @param b
     * @param biFunction
     * @param function
     * @return
     */
    public static int compute(int a, int b, BiFunction<Integer, Integer, Integer> biFunction, Function<Integer, Integer> function) {
        return biFunction.andThen(function).apply(10, 20);
    }

    public static void main(String[] args) {
        List<String> x = Arrays.asList("1", "2", "3", "3");
        System.out.println(otherDistinctByKey(x, p -> p));
        System.out.println(compute(4, 5, (a, b) -> a * b, a -> a + 2));
    }
}
