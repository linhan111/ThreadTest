package com.github.linhan111.T201908;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试对List去重的方法，注意耗时
 */
public class DuplicateItemTest {
    public static void main(String[] args) {
        List<String> x = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            x.add("this idsf" + i);
        }
        int result = 0;
        for (int i = 0; i < x.size(); i++) {
            for (int j = x.size(); j > i; j--) {
                result++;
            }
        }
        System.out.println(result);
    }
    // 如果List中存放对象，对比下和HashMap耗时（equals方法耗时？）
}
