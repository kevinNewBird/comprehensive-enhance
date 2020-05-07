package com.base.base_level.c8;

/**
 * @Author:zhao.song
 * @Date:2019/12/12 17:29
 * @Description:
 */
public class DeadLock {
    private OtherService otherService;

    public void m1() {
        synchronized (DeadLock.class) {
            System.out.println("m1===============");
        }
    }

    public void m2(){
        synchronized (DeadLock.class) {
            System.out.println("m2===========");
            otherService.s2();
        }
    }

    public void setOtherService(OtherService otherService) {
        this.otherService = otherService;
    }
}
