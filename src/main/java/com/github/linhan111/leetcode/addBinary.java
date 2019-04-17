package com.github.linhan111.leetcode;

/**
 * 问题描述：https://leetcode-cn.com/explore/learn/card/array-and-string/200/introduction-to-string/779/
 */
public class addBinary {
    public String addBinary(String a, String b) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int da,db;
        int adv = 0;
        StringBuilder result = new StringBuilder();
        while (i >= 0 && j >= 0) {
            da = a.charAt(i--) == '0' ? 0 : 1;
            db = b.charAt(j--) == '0' ? 0 : 1;
            int d = da + db + adv;
            result.append(d % 2 == 0 ? '0' : '1');
            adv = d >> 1;
        }
        if (i >= 0) {
            while (i >= 0) {
                da = a.charAt(i--) == '0' ? 0 : 1;
                int d = da + adv;
                result.append(d % 2 == 0 ? '0' : '1');
                adv = d >> 1;
            }
        } else if (j >= 0) {
            while (j >= 0) {
                db = b.charAt(j--) == '0' ? 0 : 1;
                int d = db + adv;
                result.append(d % 2 == 0 ? '0' : '1');
                adv = d >> 1;
            }
        }
        // 如果最后一位还存在1则直接在首位添加1
        if (adv == 1) {
            result.append('1');
        }
        // 翻转
        return result.reverse().toString();
    }
}
