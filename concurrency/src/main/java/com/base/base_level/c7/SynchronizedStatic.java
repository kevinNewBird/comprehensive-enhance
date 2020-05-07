package com.base.base_level.c7;

/**
 * @Author:zhao.song
 * @Date:2019/12/12 16:51
 * @Description:
 */
public class SynchronizedStatic {

    static {
        synchronized (SynchronizedStatic.class) {
            System.out.println("static === " + Thread.currentThread().getName());
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public static synchronized void m1(){
        System.out.println("m1 === "+Thread.currentThread().getName());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static synchronized void m2(){
        System.out.println("m2 === "+Thread.currentThread().getName());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
