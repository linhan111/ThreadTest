package com.github.linhan111.leetcode;

// 问题描述：二进制数组中最大连续1的个数，https://leetcode-cn.com/explore/learn/card/array-and-string/201/two-pointer-technique/788/
public class findMaxConsecutiveOnes {
    public int findMaxConsecutiveOnes(int[] nums) {
        int tmplength = 0;
        int maxlength = 0;
        // 定义一个max和一个tmp，tem遇到0就重置，遇到1就加1且与max比较
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                tmplength++;
                if (tmplength > maxlength) {
                    maxlength = tmplength;
                }
            } else {
                tmplength = 0;
            }
        }
        return maxlength;
    }
}
