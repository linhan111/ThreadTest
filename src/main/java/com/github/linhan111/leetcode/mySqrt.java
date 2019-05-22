package com.github.linhan111.leetcode;

/**
 * 问题描述：自己实现sqrt方法，求非负整数的平方根，只保留整数部分
 * https://leetcode-cn.com/explore/learn/card/binary-search/209/template-i/836/
 */
public class mySqrt {
    public static int mySqrt(int x) {
        if (x <= 1) {
            return x;
        }
        int left = 1, right = x;
        while (left < right) {
            int mid = (left + right) / 2;
            if (x / mid >= mid) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        // 舍弃小数部分，则取得刚好大于target的值减一
        return right - 1;
    }

    public static void main(String[] args) {
        System.out.println(mySqrt(8));
        System.out.println(mySqrt(100));
    }
}
