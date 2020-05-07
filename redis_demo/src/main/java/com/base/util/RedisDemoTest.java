package com.base.util;

import redis.clients.jedis.Jedis;


/**
 * Description: TODO <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/1 22:34
 * @Version: 1.0
 */
public class RedisDemoTest {

    public static void main(String[] args) {
        String key1 = "test1";
        String key2 = new String("test1");
        System.out.println(key1 == key2);
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println(jedis.set(key1, String.valueOf(System.currentTimeMillis()), "NX", "PX", 10 * 1000));
        System.out.println(jedis.set(key2, String.valueOf(System.currentTimeMillis()), "NX", "PX", 10 * 1000));
        jedis.close();
    }
}
