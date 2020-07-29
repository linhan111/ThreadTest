package com.github.linhan111.排序算法;

import java.util.Arrays;

/**
 * 归并排序Demo
 * 注意归并排序的使用场景：在ShardingSphere中，需要对order by等关键字查询的分表结果聚合，实现方式：得到的两个有序集合，通过归并排序组合成最终的结果
 * 参考链接：
 * https://blog.csdn.net/qq_39776901/article/details/77151983
 * https://www.jianshu.com/p/95c69f8e47e7
 *
 * @author linhan111
 */
public class MergeSortTest {
    public static void main(String[] args) {
        int[] arr = {3, 1, 2, 4, 5, 7, 6, 8};
        // left在这里定义，控制左半部分的首元素
        int left = 0;
        // right指向最后一个元素
        int right = arr.length - 1;
        // 调用排序函数
        sort(arr, left, right);

        System.out.println(Arrays.toString(arr));
    }

    private static int[] sort(int[] arr, int left, int right) {
        // mid是作为分割的指针
        int mid = (left + right) / 2;
        // FIXME 递归拆分，调用归并（这里是关键，需要好好思考）
        if (left < right) {
            sort(arr, left, mid);
            sort(arr, mid + 1, right);
            merge(arr, left, right, mid);
        }
        return arr;
    }

    private static void merge(int[] arr, int left, int right, int mid) {
        // 定义一个新数组来存放排序后的数
        int[] temp = new int[right - left + 1];
        // x控制新数组的下标
        int x = 0;
        // j指向右半部分数组的首元素
        int j = mid + 1;
        // 将left赋值给l，最后覆盖的时候要用到，否则left值被改变
        int l = left;
        while (left <= mid && j <= right) {
            if (arr[left] < arr[j]) {
                temp[x++] = arr[left++];
            } else {
                temp[x++] = arr[j++];
            }
        }
        // 处理剩余数组
        while (left <= mid) {
            temp[x++] = arr[left++];
        }
        // 处理剩余数组
        while (j <= right) {
            temp[x++] = arr[j++];
        }
        // 覆盖原来的数组
        for (int i = 0; i < temp.length; i++) {
            arr[i + l] = temp[i];
        }
    }
}
