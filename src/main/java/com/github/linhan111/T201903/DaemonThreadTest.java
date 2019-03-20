package com.github.linhan111.T201903;

/**
 * @author lhan
 * @version 1.0.0
 * @className DarmonThreadTest
 * @date 19-3-19 上午10:41
 * @description Daemon Thread
 * @program ThreadTest
 */
public class DaemonThreadTest {
    // 注：守护线程在其他用户线程执行完成后自动销毁（jvm在只有守护线程无用户线程时会自动退出）
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) {
                System.out.println("I am alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "DaemonTest");
        // 若设置守护线程在线程start之后会抛出IllegalThreadStateException，同时设置守护线程失败，线程不会停止
        t.setDaemon(true);
        t.start();
        Thread.sleep(5000);
    }
}

