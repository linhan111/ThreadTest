package com.github.linhan111.leetcode;

import java.util.Arrays;

/**
 * 问题描述：翻转字符串里面的单词，https://leetcode-cn.com/explore/learn/card/array-and-string/202/conclusion/793/
 */
public class reverseWords {
    public static void main(String[] args) {
        String s = "asdf  sdf sdf     s";
        // split(" +")代表使用一个或多个空格来分割
        System.out.println(Arrays.toString(s.split(" +")));
    }

    private static String reverseWords(String s) {
        // 思路：先首尾去空格，然后使用正则表达式匹配一个或多个空格，分割为字符串数组，然后翻转
        String[] words = s.trim().split(" +");
        for (int i = 0; i < words.length / 2; i++) {
            String tmp = words[i];
            words[i] = words[words.length - i - 1];
            words[words.length - i - 1] = tmp;

        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i == 0) {
                builder.append(words[i]);
            } else {
                builder.append(" ").append(words[i]);
            }
        }
        return builder.toString();
    }
}
