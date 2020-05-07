package com.base.base_level.c8;

/**
 * @Author:zhao.song
 * @Date:2019/12/12 17:29
 * @Description:
 */
public class OtherService {

    private DeadLock deadLock;

    public OtherService(DeadLock deadLock) {
        this.deadLock = deadLock;
    }

    public void s1(){
        synchronized (OtherService.class){
            System.out.println("s1================");
            deadLock.m1();
        }

    }

    public void s2() {
        synchronized (OtherService.class) {
            System.out.println("s2================");
        }
    }
}
