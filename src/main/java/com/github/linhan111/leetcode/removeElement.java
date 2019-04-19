package com.github.linhan111.leetcode;

import java.util.Arrays;

/**
 * 问题描述：数组中移除特定元素（不使用额外空间），https://leetcode-cn.com/explore/learn/card/array-and-string/201/two-pointer-technique/787/
 */
public class removeElement {
    public static void main(String[] args) {
        int[] x = new int[]{1, 2, 3, 4, 4, 5, 10};
        removeElement(x, 4);
        System.out.println(Arrays.toString(x));
    }

    /**
     * 移除元素
     *
     * @param nums 输入数组
     * @param val  数组中待移除的元素
     * @return 修改后数组的长度
     */
    private static int removeElement(int[] nums, int val) {
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            // 只有每次不需要移除元素的时候result指针才移动
            if (nums[i] != val) {
                nums[result] = nums[i];
                result++;
            }
        }
        return result;
    }
}
