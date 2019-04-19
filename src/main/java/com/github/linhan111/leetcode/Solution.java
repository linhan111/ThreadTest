package com.github.linhan111.leetcode;

/**
 * 问题描述：两数之和，https://leetcode-cn.com/explore/learn/card/array-and-string/201/two-pointer-technique/785/
 */
public class Solution {

    public static void main(String[] args) {
        System.out.println(twoSum1(new int[]{0, 1, 9, 10}, 100));
    }

    // 这种解法提交上去是超出时间限制，若数组为全是0和9组成，但target为5，则永远不可能有输出，但是我还是两层循环，这种解法很笨！
    private static int[] twoSum(int[] numbers, int target) {
        // 两个循环，一个从头部开始，一个从尾部开始
        for (int i = 0; i < numbers.length; i++) {
            for (int j = numbers.length - 1; j >= 0; j--) {
                // 不能使用相同元素
                if (numbers[i] + numbers[j] == target) {
                    return new int[]{i + 1, j + 1};
                }
            }
        }
        return null;
    }

    private static int[] twoSum1(int[] numbers, int target) {
        // 思路：由于有序数组，定义双指针，一个在头部，一个在尾部，若两数之后比target大，则后面指针前移，若小，则前面指针前移
        int i = 0;
        int j = numbers.length - 1;
        // 由于一个元素只能使用一个，则i只能小于j来做循环
        while (i < j) {
            if (numbers[i] + numbers[j] < target) {
                i++;
                continue;
            }
            if (numbers[i] + numbers[j] > target) {
                j--;
                continue;
            }
            if (numbers[i] + numbers[j] == target) {
                return new int[]{i + 1, j + 1};
            }
        }
        return null;
    }
}
