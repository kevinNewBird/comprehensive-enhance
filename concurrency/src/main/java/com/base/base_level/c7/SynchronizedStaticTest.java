package com.base.base_level.c7;

/**
 * @Author:zhao.song
 * @Date:2019/12/12 16:56
 * @Description:
 */
public class SynchronizedStaticTest {

    public static void main(String[] args) {

        new Thread("T1"){
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                SynchronizedStatic.m1();
                long end = System.currentTimeMillis();
                System.out.println(end-start);
            }
        }.start();


        new Thread("T2"){
            @Override
            public void run() {
                SynchronizedStatic.m2();
            }
        }.start();
    }
}
