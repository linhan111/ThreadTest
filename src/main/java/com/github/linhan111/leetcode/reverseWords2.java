package com.github.linhan111.leetcode;

/**
 * 问题描述：翻转字符串中的单词中的字母顺序，https://leetcode-cn.com/explore/learn/card/array-and-string/202/conclusion/794/
 */
public class reverseWords2 {
    public static void main(String[] args) {
        reverseWords2("Let's take LeetCode contest");
    }
    private static String reverseWords2(String s) {
        String[] words = s.split(" ");
        StringBuilder builder1 = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            StringBuilder builder2 = new StringBuilder(words[i].length());
            for (int j = words[i].toCharArray().length - 1; j >= 0; j--) {
                builder2.append(words[i].toCharArray()[j]);
            }
            if (i == 0) {
                builder1.append(builder2.toString());
            } else {
                builder1.append(" ").append(builder2.toString());
            }
        }
        return builder1.toString();
    }
}
