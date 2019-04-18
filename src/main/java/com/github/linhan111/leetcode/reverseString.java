package com.github.linhan111.leetcode;

/**
 * 翻转字符串，问题描述：https://leetcode-cn.com/explore/learn/card/array-and-string/201/two-pointer-technique/783/
 * 注意：注意问题描述中不给另外数组分配内存空间，即只能在原数组上修改。使用双指针技巧，交换头尾数据（java中不存在指针，直接修改对应index上的元素）
 */
public class reverseString {
    public static void main(String[] args) {
        char[] s = "hello".toCharArray();
        reverseString(s);
        System.out.println(s);
    }

    private static void reverseString(char[] s) {
        for (int i = 0; i < s.length / 2; i++) {
            char change = s[i];
            s[i] = s[s.length - i - 1];
            s[s.length - i - 1] = change;
        }
    }

    // 使用双指针技巧的典型场景之一是你想要从两端向中间迭代数组,这时你可以使用双指针技巧： 一个指针从始端开始，而另一个指针从末端开始。
    // 值得注意的是，这种技巧经常在排序数组中使用。
}
