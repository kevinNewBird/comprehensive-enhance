package com.base.base_level.c4;

/**
 * @Author:zhao.song
 * @Date:2019/10/18 16:26
 * @Description:主线程中开启一个守护线程
 */
public class DaemonThread {


    public static void main(String[] args) {//main ruuning
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " running");
                    Thread.sleep(10_000);
                    System.out.println(Thread.currentThread().getName() + " done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };//new

        //runnable -> running|dead|blocked->terminated
        t.setDaemon(true);
        t.start();
        //main terminated , threads is managed by threadGroup.
        System.out.println(Thread.currentThread().getName());
    }
}
