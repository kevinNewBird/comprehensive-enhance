package com.base.base_level.c4;

/**
 * @Author:zhao.song
 * @Date:2019/10/18 17:18
 * @Description:
 */
public class DaemonThread4 {

    public static void main(String[] args) {

        System.out.println("00000");


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("111111");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

//        System.out.println(Thread.currentThread().getId());

        System.out.println("22222");

    }
}
