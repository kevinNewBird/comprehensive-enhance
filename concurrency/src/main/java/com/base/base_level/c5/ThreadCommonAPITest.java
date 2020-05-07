package com.base.base_level.c5;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author:zhao.song
 * @Date:2019/11/18 19:04
 * @Description:线程常用API
 */
public class ThreadCommonAPITest {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                IntStream.range(1,1000).forEach(i-> System.out.println(Thread.currentThread().getName()+"---"+i));
            }
        };

        Thread t2 = new Thread(){
            @Override
            public void run() {
                IntStream.range(1,1000).forEach(i-> System.out.println(Thread.currentThread().getName()+"---"+i));
            }
        };

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        Optional.of("All tasks finish.").ifPresent(System.out::println);
        IntStream.range(1000,2000).forEach(i-> System.out.println(Thread.currentThread().getName()+"---"+i));
    }
}
