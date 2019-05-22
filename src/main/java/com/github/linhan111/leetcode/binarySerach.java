package com.github.linhan111.leetcode;

/**
 * 问题描述：https://leetcode-cn.com/explore/learn/card/binary-search/208/background/833/
 * 个人理解：二分查找需要给定数组有序，乱序不适用二分查找，可先排序后使用二分查找，排序可参考排序算法部分，注意时间/空间复杂度
 * 二分查找时间复杂度为：O(log2n) ，对比其他方法时间复杂度较低，对于乱序数组等可先选择时间复杂度相对低的算法排序后使用二分查找
 */
public class binarySerach {
    private static int binarySerach(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        // 注意这里的<=逻辑
        while (left <= right) {
            // 这里加上对最大元素的判断，在每次遍历时最大元素都小于target直接跳出循环节省时间
            if (nums[right] < target) {
                return -1;
            }
            // 防止精度丢失
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(binarySerach(new int[] {1,2,3,5,6,7,10}, 4));
    }
}
