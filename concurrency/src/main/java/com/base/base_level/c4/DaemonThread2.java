package com.base.base_level.c4;

/**
 * @Author:zhao.song
 * @Date:2019/10/18 16:48
 * @Description:主线程中,在一个新建线程中开启一个守护线程
 */
public class DaemonThread2 {

    public static void main(String[] args) {

        Thread t = new Thread(){
            @Override
            public void run() {
                Thread innerThread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            System.out.println("InnerThread thread finish running.");
                            Thread.sleep(10_000);
                            System.out.println("InnerThread thread finish done.");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                innerThread.setDaemon(true);
                innerThread.start();

                try {
                    System.out.println("T thread finish running.");
                    Thread.sleep(1_000);
                    System.out.println("T thread finish done.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        };

        t.start();
        System.out.println("Main done");
    }
}
