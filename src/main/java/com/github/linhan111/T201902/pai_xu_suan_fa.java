package com.github.linhan111.T201902;

import java.util.Arrays;

/**
 * @author lhan
 * @version 1.0.0
 * @className pai_xu_suan_fa
 * @date 19-2-23 下午2:03
 * @description Java 常用的排序算法及原理（注意分析其时间空间复杂度）
 * 参考链接：https://www.cnblogs.com/guoyaohua/p/8600214.html
 * @program ThreadTest
 */
public class pai_xu_suan_fa {

    /**
     * 冒泡排序：最简单的一种
     *
     * @param array 排序前
     * @return 排序后
     */
    private static int[] bubble_sort(int[] array) {
        // new int[] {1,1000,45,6,234,678,100}; length = 7
        if (array.length == 0) {
            return array;
        }
        for (int i = 0; i < array.length; i++) {
            // 这里在我的记忆中是array.length -1次循环，注意这里的区别，可提高性能
            // 个人理解：把最大的数往后排，一次循环之后最后的数是最大的，下次循环排除掉最后的那个数，这是array.length - i的含义
            // 当然这里j < array.length - 1 也行，就是循环次数会多一些
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j + 1] < array[j]) {
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }

    /**
     * 选择排序：每次将集合中最小的元素取出来放在头部
     *
     * @param array 排序前
     * @return 排序后
     */
    private static int[] selection_sort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
        return array;
    }

    public static void main(String[] args) {
        int[] test = new int[]{1, 1000, 45, 6, 234, 678, 100};
         System.out.println(Arrays.toString(bubble_sort(test)));
//        System.out.println(Arrays.toString(selection_sort(test)));
    }
}
