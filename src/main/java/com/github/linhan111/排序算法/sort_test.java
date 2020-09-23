package com.github.linhan111.排序算法;

import java.util.Arrays;

public class sort_test {
    public static void main(String[] args) {
        int[] array = new int[]{1, 10, 100, 7, 8, 5};
        System.out.println(Arrays.toString(sort(array)));
    }
    private static int[] sort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length -i -1; j++) {
                if (array[j + 1] < array[j]) {
                    int tem = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = tem;
                }
            }
        }
        return array;
    }
}
