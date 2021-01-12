package com.github.linhan111.TCWork;

/**
 * SingleNode
 *
 * @author linhan111
 * @since 2021-01-06
 */
public class SingleNode {
    public static class Node {
        // 数据域
        public int value;
        // 指针域
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }
}
