package com.github.linhan111.leetcode;

/**
 * 问题描述：https://leetcode-cn.com/explore/learn/card/array-and-string/200/introduction-to-string/781/
 * 问题备注：求的是字符串数组的最长公共前缀？前缀和中间截取的字符串的区别搞清楚！！！
 */
public class longestCommonPrefix {
    public static void main(String[] args) {
        String[] x = {"asdf","sd","asd"}; // 这个数组应该不存在最长公共前缀？
        System.out.println("result + " + longestCommonPrefix(x));
    }

    // 求字符串数组的最长公共前缀
    private static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        if (strs.length == 1) return strs[0];
        // 最长元素的index与length
        int maxSizeElementIndex = 0;
        int maxSizeElementLength = strs[0].length();
        // 由于上面直接赋值了第0个元素的index与length，所有这个loop可直接从第1个元素开始循环
        for (int i = 1; i < strs.length; i++) {
            if ("".equals(strs[i])) {
                return "";
            }
            if (strs[i].length() > maxSizeElementLength) {
                maxSizeElementLength = strs[i].length();
                maxSizeElementIndex = i;
            }
        }

        for (int length = strs[maxSizeElementIndex].length(); length > 0; length--) {
            boolean flag = true;
            for (int i = 0; i < strs.length; i++) {
                // 取消loop对数组中length最长的元素
                if (i != maxSizeElementIndex) {
                    if (!strs[i].startsWith(strs[maxSizeElementIndex].substring(0, length))) {
                        flag = false;
                    }
                }
            }
            if (flag) {
                return strs[maxSizeElementIndex].substring(0, length);
            }
        }
        return "";
    }

    // 这种解法可能存在问题？参考链接：https://blog.csdn.net/fengpojian/article/details/81326781
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
