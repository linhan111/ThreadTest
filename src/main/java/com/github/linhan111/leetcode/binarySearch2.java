package com.github.linhan111.leetcode;

/**
 * 问题描述：搜索旋转有序数组，并要求时间复杂度为O(log n)
 * leetcode：https://leetcode-cn.com/explore/learn/card/binary-search/209/template-i/838/
 * 解题思路：https://blog.csdn.net/qq_17550379/article/details/83339465
 *         https://www.cnblogs.com/keeya/p/9689927.html
 */
public class binarySearch2 {
    private static int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        if (nums.length == 0) return -1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] == target) return mid;
            // 如果数组中间的元素大于最左边的元素，说明left到mid这个区间内顺序是正常的升序
            if (nums[mid] >= nums[left]) {
                // 若target在[left,mid)范围内，则在[left,mid]范围内二分查找
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                    // 若target不在[left,mid)范围内，则在(mid,right]中二分查找
                } else {
                    left = mid + 1;
                }
            }
            if (nums[mid] <= nums[right]) {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(search(new int[] {4,5,6,7,0,1,2}, 0));
    }
}
