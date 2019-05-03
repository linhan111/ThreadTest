package com.github.linhan111.leetcode;

/**
 * 删除排序数组中的重复出现的元素，返回移除后数组的新长度（注意题目中的空间限制，不使用额外数组空间，原地修改输入数组）
 * 问题详情：https://leetcode-cn.com/explore/learn/card/array-and-string/202/conclusion/795/
 */
public class removeDuplicates {
    public static void main(String[] args) {
        System.out.println(removeDuplicates(new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4}));
    }


    private static int removeDuplicates(int[] nums) {
        if (nums.length == 1) {
            return 1;
        }
        int result = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            // 注意有序数组的条件，只需要比较后一个，加上双指针
            if (nums[i] != nums[i + 1]) {
                nums[result] = nums[i + 1];
                result += 1;
            }
        }
        return result;
    }
}
