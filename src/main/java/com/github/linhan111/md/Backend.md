### 保存java多线程变成的相关知识点
*test*
- test
// 相关比较好的blog：https://blog.csdn.net/Evankaka/article/details/44153709
// cpu时间片解释：https://blog.csdn.net/qq_32812243/article/details/50590104
 Thread sayHello=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        });
        sayHello.start();

// lambda简化
 Thread thread=new Thread(()-> System.out.println("hello"));
        thread.start();

线程不安全原理详解：
1、先来了解一下：为什么多线程并发是不安全的？

  在操作系统中，线程是不拥有资源的，进程是拥有资源的。而线程是由进程创建的，一个进程可以创建多个线程，这些线程共享着进程中的资源。所以，当线程一起并发运行时，
同时对一个数据进行修改，就可能会造成数据的不一致性，看下面的例子：

    假设一个简单的int字段被定义和初始化：
    int counter = 0;
    该counter字段在两个线程A和B之间共享。假设线程A、线程B同时对counter进行计算，递增运算：
    counter ++；
    那么计算的结果应该是 2 。但是真实的结果却是 1 ，这是因为：线程A得到的运算结果是1，线程B的运算结果也是1，当线程A将结果写回到内存中的 count 后，线程B也将结果写回到内存中去，这就会把线程A的计算结果给覆盖了。

上面仅仅是一种简单的情况，还有更复杂的情况，本文不深入去了解。
2、多线程并发不安全的原因已经知道，那么针对这个种情况，java中有两种解决思路：

    给共享的资源加把锁，保证每个资源变量每时每刻至多被一个线程占用。
    让线程也拥有资源，不用去共享进程中的资源。

3、基于上面的两种思路，下面便是3种实施方案：

1. 多实例、或者是多副本（ThreadLocal）：对应着思路2，ThreadLocal可以为每个线程的维护一个私有的本地变量，可参考java线程副本–ThreadLocal；
2. 使用锁机制 synchronize、lock方式：为资源加锁
3. 使用 java.util.concurrent 下面的类库：有JDK提供的线程安全的集合类

可能说的还不太清楚，更新一下，以及给出一个线程安全模拟的例子：
上面说了，多线程之所以不安全，是因为共享着资源（如果没有资源变量共享，那么多线程一定是安全的）。比如，存在共享变量a，线程A在使用变量a时进行计算时，因为时间片的到来，导致线程不得不由运行中状态进入就绪状态，暂停运行。等该线程A又重新被调度，得以继续执行时，得到了最终的结果。但是此时内存中的变量a可能已经被其他线程改变了，但线程A的结果再写回到内存中时，就会覆盖了其他线程的计算结果，这就是多线程不安全的原理。
--------------------- 

cas算法：
    相关：https://www.cnblogs.com/hupu-jr/p/7927635.html
    性能考虑：cas为非阻塞算法，java.util.Concurrent.atomic类中大量使用了该算法来取代synchronized关键字，避免加锁操作，但是cas算法会有ABA的问题，这是cas算法的缺陷
    在轻度到中度的争用情况下,非阻塞算法的性能会超越阻塞算法,因为 CAS 的多数时间都在第一次尝试时就成功,而发生争用时的开销也不涉及线程挂起和上下文切换,只多了几个循环迭代。
    没有争用的 CAS 要比没有争用的锁便宜得多(这句话肯定是真的,因为没有争用的锁涉及 CAS 加上额外的处理),而争用的 CAS 比争用的锁获取涉及更短的延迟。
    在高度争用的情况下(即有多个线程不断争用一个内存位置的时候),基于锁的算法开始提供比非阻塞算法更好的吞吐率,因为当线程阻塞时,它就会停止争用,耐心地等候轮到自己,从而避免了进一步争用。
    但是,这么高的争用程度并不常见,因为多数时候,线程会把线程本地的计算与争用共享数据的操作分开,从而给其他线程使用共享数据的机会。
    (这么高的争用程度也表明需要重新检查算法,朝着更少共享数据的方向努力。)“流行的原子” 中的图在这方面就有点儿让人困惑,因为被测量的程序中发生的争用极其密集,看起来即使对数量很少的线程,锁定也是更好的解决方案。
    备注：cas算法中基于汇编指令的操作，保证读取到最新的值并set修改的值为原子操作，这一步通过jni-native来实现 
    存在的问题：cas算法一般会存在三个问题，aba问题，循环时间开销大（类似死循环）、只能保证一个共享变量的的原子操作
    
volatile: https://www.cnblogs.com/wq3435/p/6220751.html，https://www.cnblogs.com/zhengbin/p/5654805.html， 
            volatile保证变量可见性，禁止指令重排，指令重排会造成的问题需要看一下：https://www.2cto.com/kf/201601/487117.html
            
java中的ReentrantLock的使用：https://blog.csdn.net/i_am_kop/article/details/80958856，注意这个可重入锁和synchronize的区别！
Java中的ReadWriteLock（ReentrantReadWriteLock）的使用：https://blog.csdn.net/J080624/article/details/82790372，注意读写锁和互斥锁的区别，同时注意有参构造的入参，创建公平锁与非公平锁的区别
    及公平锁与非公平锁的实现区别等，读写锁与synchronize的执行效率区别：https://blog.csdn.net/liuchuanhong1/article/details/53539341