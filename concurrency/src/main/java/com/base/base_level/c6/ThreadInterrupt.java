package com.base.base_level.c6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author:zhao.song
 * @Date:2019/12/1 17:01
 * @Description:
 */
public class ThreadInterrupt {

    private static Logger logger = LoggerFactory.getLogger(ThreadInterrupt.class);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                /*logger.info("Thread " + Thread.currentThread().getName() + " is running.");
                try {
//                Thread.sleep(10000L);
                    logger.info("Thread " + Thread.currentThread().getName() + " is done.");
                } catch (Exception e) {
                    logger.info(String.format("Thread " + Thread.currentThread().getName() + "  fail.ERROR[%s]", e.getMessage()));
                    e.printStackTrace();
                }*/
            }
        });

        t1.start();
        Thread.sleep(100L);
        logger.info("Thread t1 is interrupting? status:"+t1.isInterrupted());
        t1.interrupt();
        logger.info("Thread t1 is interrupting? status:"+t1.isInterrupted());

    }
}
