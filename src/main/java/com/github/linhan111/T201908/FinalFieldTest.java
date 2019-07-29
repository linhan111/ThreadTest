package com.github.linhan111.T201908;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 为什么java中匿名内部类引用的外部变量必须是final的
 * 参考回答：https://www.zhihu.com/question/28190927/answer/39786939
 */
public class FinalFieldTest {
    // 由于java的lambda表达式实际上是一个匿名内部类的实现，可用来模拟这种情况
    public static void main(String[] args) {

        /**
         * 第一种情况，使用原子引用来达到 修改对匿名内部类可见 的作用（注意AtomicReference类中的set方法并不是原子操作）
         * AtomicReference的使用详见：https://www.jianshu.com/p/882d0e2c3ea6 ，由此衍生出很多原子类，比如
         * @see java.util.concurrent.atomic.AtomicInteger
         *
         */
        AtomicReference<String> x = new AtomicReference<>("ete");
        Arrays.asList("s", "b").forEach(p -> {
            x.set("test");
        });
        System.out.println(x.get());

        // 第二种方式，由于java只限制了被lambda表达式引用的变量，但并未限制对象地址指向的对象实例变化，则使用length = 1的数组变相解决
        final String[] y = {"ete"};
        Arrays.asList("s", "b").forEach(p -> {
            y[0] = "test";
        });
        System.out.println(y[0]);
    }
}
