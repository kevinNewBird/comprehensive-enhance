package com.base.base_level.c4;

/**
 * @Author:zhao.song
 * @Date:2019/10/18 16:48
 * @Description:主线程中,在一个新建守护线程中开启一个线程
 */
public class DaemonThread3 {


    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                Thread innerThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("InnerThread thread finish running.");
                            Thread.sleep(100_000);
                            System.out.println("InnerThread thread finish done.");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

//            innerThread.setDaemon(true);
                innerThread.start();

                try {
                    System.out.println("T thread finish running.");
                    Thread.sleep(1_000);
                    System.out.println("T thread finish done.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        };

        t.setDaemon(true);//守护线程必须在start()之前调用,否则:1.不生效;2.如果该线程是活跃的,抛异常

        t.start();
//        Thread.sleep(100);
        System.out.println("Main thread is done");//如果没有该输出语句 , 主线程相当于没有任何执行单元,直接结束
    }
}
