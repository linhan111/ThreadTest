package com.github.linhan111.leetcode;

/**
 * 问题描述：https://leetcode-cn.com/explore/learn/card/array-and-string/200/introduction-to-string/781/
 */
public class longestCommonPrefix {
    public static void main(String[] args) {
        String[] x = {"asdf", "sd", "asd"};
        System.out.println("result + " + longestCommonPrefix(x));
    }

    // 求字符串数组的最长公共前缀
    private static String longestCommonPrefix(String[] strs) {
        // 最长元素的index与length
        int minSizeElementIndex = 0;
        int minSizeElementLength = strs[0].length();
        // 由于上面直接赋值了第0个元素的index与length，所有这个loop可直接从第1个元素开始循环
        for (int i = 1; i < strs.length; i++) {
            if ("".equals(strs[i])) {
                return "";
            }
            if (strs[i].length() < minSizeElementLength) {
                minSizeElementLength = strs[i].length();
                minSizeElementIndex = i;
            }
        }

        for (int length = strs[minSizeElementIndex].length(); length > 0; length--) {
            for (String s : strs) {
                if (s.contains(strs[minSizeElementIndex].substring(0, length))) {
                    return strs[minSizeElementIndex].substring(length);
                }
            }
        }
        return "";
    }

    // 这种解法可能存在问题，参考链接：https://blog.csdn.net/fengpojian/article/details/81326781
    private static String theOtherTest(String[] strs) {
        int count = strs.length;
        String prefix = "";
        if (count != 0) {
            prefix = strs[0];
        }
        for (int i = 0; i < count; i++) {
            //关键代码，不断的从后往前截取字符串，然后与之相比，直到startsWith()返回true
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
            }
        }
        return prefix;
    }
}
