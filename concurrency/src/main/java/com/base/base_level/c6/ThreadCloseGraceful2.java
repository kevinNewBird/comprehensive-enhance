package com.base.base_level.c6;

/**
 * @Author:zhao.song
 * @Date:2019/12/1 18:07
 * @Description:如何优雅的关闭线程
 */
public class ThreadCloseGraceful2 {

    private static class Worker extends Thread{


        @Override
        public void run() {
            while(true){
                /*try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    break;
                }*/
                if (isInterrupted()){
                    break;
                }
            }
        }

    }

    public static void main(String[] args){
        Worker worker = new Worker();
        worker.start();

        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        worker.interrupt();

    }

}
