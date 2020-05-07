package com.base.base_level.c7;

/**
 * @Author:zhao.song
 * @Date:2019/10/16 11:51
 * @Description:
 */
public class BankVersion3 {

    public static void main(String[] args) {
        SynchronizedRunnable table1 = new SynchronizedRunnable();
        new Thread(table1,"一号窗口").start();
        new Thread(table1,"二号窗口").start();
        new Thread(table1,"三号窗口").start();
    }
}
