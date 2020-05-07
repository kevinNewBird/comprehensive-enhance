package com.base.base_level.c8;

/**
 * @Author:zhao.song
 * @Date:2019/12/12 18:50
 * @Description:
 */
public class DeadLockTest {


    public static void main(String[] args) {
        DeadLock deadLock = new DeadLock();
        OtherService otherService = new OtherService(deadLock);
        deadLock.setOtherService(otherService);

        new Thread("T1"){
            @Override
            public void run() {
                while (true)
                deadLock.m2();
            }
        }.start();

        new Thread("T2"){
            @Override
            public void run() {
                while (true)
                otherService.s1();
            }
        }.start();
    }
}
