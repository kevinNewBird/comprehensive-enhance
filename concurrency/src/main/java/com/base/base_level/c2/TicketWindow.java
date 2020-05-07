package com.base.base_level.c2;

/**
 * @Author:zhao.song
 * @Date:2019/10/16 11:51
 * @Description:
 */
public class TicketWindow implements Runnable {
    private final static int MAX = 100;
    private int index = 1;



    public void run() {
//        synchronized (lock) {
            while (index <= MAX) {
                System.out.println(Thread.currentThread().getName() + "的号码是:" + index++);
            }
//        }
    }
}
