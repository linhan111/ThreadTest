package com.github.linhan111.leetcode;

/**
 * 移动零（原数组上操作，尽量内存与操作次数少）
 * 问题详情：https://leetcode-cn.com/explore/learn/card/array-and-string/202/conclusion/796/
 */
public class moveZeroes {
    public static void main(String[] args) {
        moveZeroes(new int[] {1,2,0,1,0});
    }

    private static void moveZeroes(int[] nums) {
        int k = 0;
        // 思路：非零元素前移，完毕后非零元素末尾添加0
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[k] = nums[i];
                k++;
            }
        }
        while (k < nums.length) {
            nums[k] = 0;
            k++;
        }
    }
}
