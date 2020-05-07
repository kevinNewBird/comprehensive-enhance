package com.base.base_level.c3;

/**
 * @Author:zhao.song
 * @Date:2019/10/16 14:15
 * @Description:构造线程注意的细节点
 */
public class CreateThread {
    public static void main(String[] args) {
        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };  //可执行状态的线程

        t.start();

        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        System.out.println("正在执行的线程数:"+threadGroup.activeCount());

        Thread[] threads = new Thread[threadGroup.activeCount()];
        int enumerate = threadGroup.enumerate(threads);

        System.err.println("=================================");
        System.out.println("正在执行的线程名:");
        for (Thread thread : threads) {
            System.out.println(thread.getName());
        }

        /**
         * 输出结果:
         * main
         * Monitor Ctrl-Break: 监听线程
         * Thread-0
         */

    }
}
