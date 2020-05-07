package com.base.base_level.c2;

/**
 * @Author:zhao.song
 * @Date:2019/10/16 11:51
 * @Description:
 */
public class Bank {

    public static void main(String[] args) {
        TicketWindow table1 = new TicketWindow();
        new Thread(table1,"一号窗口").start();
        new Thread(table1,"二号窗口").start();
        new Thread(table1,"三号窗口").start();
    }
}
