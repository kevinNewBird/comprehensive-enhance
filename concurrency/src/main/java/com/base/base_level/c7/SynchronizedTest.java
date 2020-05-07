package com.base.base_level.c7;

/**
 * @Author:zhao.song
 * @Date:2019/12/11 23:25
 * @Description:
 */
public class SynchronizedTest {

    public static void main(String[] args) {

        Runnable runnable = ()->{

            synchronized (SynchronizedTest.class) {
                try {
                    Thread.sleep(200_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);
        t1.start();
        t2.start();
        t3.start();
    }
}
