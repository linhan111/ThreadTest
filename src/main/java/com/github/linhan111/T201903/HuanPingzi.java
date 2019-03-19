package com.github.linhan111.T201903;

import java.util.Scanner;

/**
 * @author lhan
 * @version 1.0.0
 * @className HuanPingzi
 * @date 19-3-18 下午4:45
 * @description 问题描述：一瓶饮料两块钱，2个空瓶可以换一瓶饮料，问X元钱可以换多少饮料
 * @program ThreadTest
 */
public class HuanPingzi {
    private static int count = 0;
    private static int ping_zi = 0;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入金额：");
        int money = Integer.parseInt(s.next());

        count += money / 2;
        ping_zi += count;
        count = recursive(count, ping_zi);
        System.out.println("饮料数：" + count);
    }

    private static int recursive(int count, int ping_zi) {
        if (ping_zi == 2) {
            return count++;
        }
        if (ping_zi > 2) {
            count += ping_zi / 2;
            ping_zi = ping_zi / 2 + ping_zi % 2;
            count = recursive(count, ping_zi);
        }
        return count;
    }
}

