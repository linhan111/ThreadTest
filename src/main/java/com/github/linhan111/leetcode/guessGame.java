package com.github.linhan111.leetcode;

/**
 * 问题描述：猜数字大小，https://leetcode-cn.com/explore/learn/card/binary-search/209/template-i/837/
 * 个人理解：就是简单的二分查找，和之前的类似
 */
public class guessGame {
    public static int guessNumber(int n) {
        return guess(1, n);
    }

    /**
     * 这个接口用来找到目标值
     *
     * @param left  左index
     * @param right 右index
     * @return target
     */
    private static int guess(int left, int right) {
        if (left == right) return left;
        int mid = (left + right) >>> 1;

        return 0;
    }
}
