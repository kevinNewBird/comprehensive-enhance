package com.base.base_level.c6;

/**
 * @Author:zhao.song
 * @Date:2019/12/1 18:02
 * @Description:如何优雅的关闭线程
 */
public class ThreadCloseGraceful1 {

    private static class Worker extends Thread{

        private static volatile boolean flag = true;

        @Override
        public void run() {
            while(flag){
                System.out.println(Thread.currentThread().getName()+" running..");
            }
        }

        public  void shutdown(){
            this.flag = false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        worker.start();

        Thread.sleep(100L);

        worker.shutdown();
    }
}
