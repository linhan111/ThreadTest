package com.github.linhan111.leetcode;

public class binarySerach {
    private static int binarySerach(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;

        // 注意这里的<=逻辑
        while (left <= right) {
            // 防止精度丢失
            int mid = left + (right - left) / 2;
            if (array[mid] == key) {
                return mid;
            } else if (array[mid] < key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
