package com.base.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * description  ThreadTest <BR>
 * <p>
 * author: zhao.song
 * date: created in 18:18  2022/9/16
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class ThreadTest {

    public static void main(String[] args) {
        new Thread(()->{
            try {
                ExecutorService es = Executors.newFixedThreadPool(3);
                List<Future> futureList = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    int[] index = {i};
                    Future<Integer> f = es.submit(() -> {
                        try {
                            if (index[0] != 2) {
                                return index[0];
                            } else {
                                return 1 / 0;
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(index[0] + "线程异常:" + e.getMessage());
                        }
                    });
                    futureList.add(f);
                }
                for (int i = 0; i < futureList.size(); i++) {
                    Future future = futureList.get(i);
                    System.out.println(future.get());
                }
            } catch (Throwable e) {
                System.out.println("1111111");
            } finally {
                System.out.println("22222");
            }

        }).start();
    }
}
