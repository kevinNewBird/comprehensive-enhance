package com.base.base_level.c5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author:zhao.song
 * @Date:2019/12/1 16:30
 * @Description:
 */
public class ThreadJoinUsage2 {

    private static Logger logger = LoggerFactory.getLogger(ThreadJoinUsage.class);

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5, 1000L
                , TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(100));

        executor.execute(()->{
            logger.info("Thread "+Thread.currentThread().getName()+" read data is running.");
            try {
                Thread.sleep(1000L);
                logger.info("Thread "+Thread.currentThread().getName()+" read data is done!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        //shutdown只是起到通知的作用
        executor.shutdown();
        //只需要等待awaitTermination方法里第一个参数指定的时间。
        //如果在指定的时间内所有的任务都结束的时候，返回true，反之返回false
        if (executor.awaitTermination(10000L, TimeUnit.MILLISECONDS)) {
            executor.shutdownNow();
        }
        logger.info("Save data successfully.");

    }
}
