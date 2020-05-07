package com.base.base_level.c7;

import com.base.base_level.c2.TicketWindow;

/**
 * @Author:zhao.song
 * @Date:2019/10/16 11:51
 * @Description:
 */
public class BankVersion2 {

    public static void main(String[] args) {
        TicketWindowVersion2 table1 = new TicketWindowVersion2();
        new Thread(table1,"一号窗口").start();
        new Thread(table1,"二号窗口").start();
        new Thread(table1,"三号窗口").start();
    }
}
