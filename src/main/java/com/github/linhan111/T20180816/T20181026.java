package com.github.linhan111.T20180816;

/**
 * @author lhan
 * @version 1.0.0
 * @className T20181026
 * @date 18-10-26 上午11:06
 * @description 创建线程的两种方式（注意都可使用lambda表达式来简化编程）：http://ifeve.com/creating-and-starting-java-threads/
 * @program ThreadTest
 */
public class T20181026 {
    public static void main(String[] args) {
        // 使用lambda表达式简化多线程编程
        /*Runnable runnable = () -> {
            int i = 1;
        };*/
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int i = 1;
            }
        };

        for (int i = 0; i < 10; i++) {
            // ali编码规范推荐使用线程池来代替显式创建线程
            new Thread(runnable, "这是测试的线程名字").start();
        }

        // 使用Thread的子类(匿名子类)来创建线程（这里也可以使用lambda来简化）
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread Running");
            }
        };
        thread.start();
    }
    // 可见性关键字：volatile，同步关键字:synchronized！！！注意一下两者的使用场景（http://ifeve.com/volatile/）
    // volatile与synchronized两者的区别：https://blog.csdn.net/zhang199416/article/details/68921960?utm_source=blogxgwz0
    // 使用volatile时需要额外小心，volatile只保证可见性，而且volatile会禁止vm优化，对性能形象不小；
    // volatile只能将每次修改值后将最新值写入内存能被其他线程立即获取到，但是不能保证是原子操作


    // 两种创建线程的方式都可以使用匿名内部类的方式结合lambda表达式来简化代码，推荐使用实现runnable接口的方式来使用线程，ali编码规范上推荐使用线程池的方式来代替手动创建线程

    // 线程的可见性与有序性
    // 多个线程之间是不能直接传递数据进行交互的，它们之间的交互只能通过共享变量来实现。拿上面的例子来说明，在多个线程之间共享了Count类的一个实例，
    // 这个对象是被创建在主内存（堆内存）中，每个线程都有自己的工作内存（线程栈），工作内存存储了主内存count对象的一个副本，当线程操作count对象时，
    // 首先从主内存复制count对象到工作内存中，然后执行代码count.count()，改变了num值，最后用工作内存中的count刷新主内存的 count。
    // 当一个对象在多个工作内存中都存在副本时，如果一个工作内存刷新了主内存中的共享变量，其它线程也应该能够看到被修改后的值，此为可见性。

    // 多个线程执行时，CPU对线程的调度是随机的，我们不知道当前程序被执行到哪步就切换到了下一个线程，
    // 一个最经典的例子就是银行汇款问题，一个银行账户存款100，这时一个人从该账户取10元，同时另一个人向该账户汇10元，那么余额应该还是100。
    // 那么此时可能发生这种情况，A线程负责取款，B线程负责汇款，A从主内存读到100，B从主内存读到100，A执行减10操作，并将数据刷新到主内存，
    // 这时主内存数据100-10=90，而B内存执行加10操作，并将数据刷新到主内存，最后主内存数据100+10=110，显然这是一个严重的问题，
    // 我们要保证A线程和B线程有序执行，先取款后汇款或者先汇款后取款，此为有序性。
}
