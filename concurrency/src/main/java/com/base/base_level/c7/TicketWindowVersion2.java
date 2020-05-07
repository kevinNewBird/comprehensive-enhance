package com.base.base_level.c7;

/**
 * @Author:zhao.song
 * @Date:2019/10/16 11:51
 * @Description:
 */
public class TicketWindowVersion2 implements Runnable {
    private final static int MAX = 1000;
    private int index = 1;

    /**
     * @Description: 
     * @param: 
     * @return: 
     * @Date: 2019/12/12 
    */
    public void run() {

        while (index <= MAX) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "的号码是:" + index++);
        }
    }
}
