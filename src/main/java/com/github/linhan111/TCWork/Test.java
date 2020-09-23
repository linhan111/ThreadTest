package com.github.linhan111.TCWork;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        GlobalEntity t1 = new GlobalEntity("1", "", null);
        GlobalEntity t2 = new GlobalEntity("2", "", null);
        GlobalEntity t3 = new GlobalEntity("3", "", null);
        List<GlobalEntity> testList1 = Lists.newArrayList(t1, t2, t3);

        List<GlobalEntity> testList2 = testList1.stream().filter(p -> "1".equals(p.getField1())).collect(Collectors.toList());
        testList2.get(0).setField2("1");

        // 这里两个List的内存地址不同，但是List中的集合元素的内存地址相同，所以修改testList2中的t1会影响testList1中的t1，这里需要注意！
        System.out.println(testList1);
    }
}
