package com.base.base_level.c3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author:zhao.song
 * @Date:2019/10/17 17:25
 * @Description:
 */
public class CreateThread2 {
    static Object obj = new Object();

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        List<Integer> lockList = Collections.synchronizedList(list);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 5,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(4));
        for (int i = 0; i < 10; i++) {
            synchronized (obj) {
                executor.execute(() -> {
                    for (int j = 0; j < 5000; j++) {
                        lockList.add(j);
                        System.out.println("当前线程:" + Thread.currentThread().getName() + "当前集合:" + lockList);
                    }
                });
            }
        }
        executor.shutdown();


    }
}
