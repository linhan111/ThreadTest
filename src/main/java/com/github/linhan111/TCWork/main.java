package com.github.linhan111.TCWork;

/**
 * main
 *
 * @author 林焓53411
 * @since 2021/3/31
 */
public class main {
    public static void main(String[] args) {
        int[][] test = new int[][]{{1, 2, 3}, {3, 1, 2}, {3, 2, 1}};
        for (int[] ints : test) {
            for (int anInt : ints) {
                System.out.println(anInt);
            }
        }
    }
}
