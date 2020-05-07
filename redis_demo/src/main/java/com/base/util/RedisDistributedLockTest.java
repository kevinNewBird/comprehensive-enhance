package com.base.util;

import redis.clients.jedis.Jedis;

import java.util.concurrent.CountDownLatch;

public class RedisDistributedLockTest {

    volatile static int n = 500;

    public static void secskill() {
        System.out.println(--n);
    }

    public static void main(String[] args) {
        Runnable runnable = () -> {
            RedisDistributedLock lock = null;
            String unLockIdentify = null;
            try {
                Jedis conn = new Jedis("127.0.0.1", 6379);
                lock = new RedisDistributedLock(conn, "test1");
                unLockIdentify = lock.acquire();
                System.out.println(Thread.currentThread().getName() + "正在运行");
                secskill();
            }finally {
                if (lock != null) {
                    lock.release(null);
                }
            }
        };
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(runnable,"线程-"+i);
            t.start();
        }
    }
}
