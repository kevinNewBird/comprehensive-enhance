package com.base.base_level.c7;

/**
 * @Author:zhao.song
 * @Date:2019/12/12 15:54
 * @Description:
 */
public class SynchronizedRunnable implements Runnable{
    private static final int ALL_TICKETS = 500;
    private int index = 1;

    @Override
    public void run() {
        while(ticket()){

        };
    }



    private synchronized boolean ticket(){
        if (index <= ALL_TICKETS) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"的号码是:" +index++);
            return true;
        }
        return false;

    }
}
