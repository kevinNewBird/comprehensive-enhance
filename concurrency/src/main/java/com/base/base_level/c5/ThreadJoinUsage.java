package com.base.base_level.c5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author:zhao.song
 * @Date:2019/12/1 16:22
 * @Description:join()方法应用:可用于多线程读取数据
 */
public class ThreadJoinUsage {

    private static Logger logger = LoggerFactory.getLogger(ThreadJoinUsage.class);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()-> {
            logger.info("Thread t1 read data is running.");
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("Thread t1 read data is done!");
        });

        Thread t2 = new Thread(()-> {
            logger.info("Thread t2 read data is running.");
            try {
                Thread.sleep(15000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("Thread t2 read data is done!");
        });


        Thread t3 = new Thread(()-> {
            logger.info("Thread t3 read data is running.");
            try {
                Thread.sleep(30000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("Thread t3 read data is done!");
        });


        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        logger.info("Save data successfully.");

    }
}
