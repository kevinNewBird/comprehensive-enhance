package com.base.base_level.c1;

import java.util.concurrent.CountDownLatch;

/**
 * @Author:zhao.song
 * @Date:2019/10/11 23:01
 * @Description:
 */
public class TryConcurrency {


    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(2);

        System.out.println(Thread.currentThread().getName());
        new Thread(new Runnable() {
            public void run() {
                writeDataToDisk();
                latch.countDown();
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                readFromDB();
                latch.countDown();
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //模拟:读数据库的过程中 , 写一些磁盘的东西

    /**
     * 读数据库
     */
    private static void readFromDB(){
        //read data from DB
        try {
            println("Begin read data from db");
            Thread.sleep(10000);
            println("Read data done and start handle it!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        println("The data handle finish and success");
    }

    /**
     * 写磁盘
     */
    private static void writeDataToDisk(){
        try {
            println("Begin write data to disk");
            Thread.sleep(20000);
            println("Write data done and start handle it!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        println("The data handle finish and success");
    }


    private static  void println(String msg){
        System.out.println(msg);
    }



}
