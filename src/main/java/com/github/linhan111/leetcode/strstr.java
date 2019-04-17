package com.github.linhan111.leetcode;

/**
 * 问题简介：https://leetcode-cn.com/explore/learn/card/array-and-string/200/introduction-to-string/780/
 */
public class strstr {
    public int strStr(String haystack, String needle) {
        if ("".equals(needle)) return 0;
        if (haystack.length() < needle.length()) return -1;
        if (haystack.length() == needle.length()) {
            if (haystack.equals(needle)) {
                return 0;
            }
            return -1;
        }
        //查看haystack字符串是否包含有needle字符串，每次截取needle.length()长度的字符串来比较
        for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
            if (haystack.substring(i, i + needle.length()).equals(needle)) {
                return i;
            }
        }
        return -1;
    }
}
