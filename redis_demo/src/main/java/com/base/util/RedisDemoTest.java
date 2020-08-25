package com.base.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

import java.util.Optional;
import java.util.Random;


/**
 * Description: TODO <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/1 22:34
 * @Version: 1.0
 */
public class RedisDemoTest {

    public static void main(String[] args) throws InterruptedException {
        String key1 = "test1";
//        String key2 = new String("test1");
//        System.out.println(key1 == key2);
        Jedis jedis = new Jedis("127.0.0.1", 6379);
//        System.out.println(jedis.set(key1, String.valueOf(System.currentTimeMillis()), "NX", "PX", 10 * 1000));
        /*for (int i = 0; i < 50; i++) {
            Thread.sleep(10_000);
            jedis.set(key1+ System.currentTimeMillis()+"_1", String.valueOf(System.currentTimeMillis()), "NX", "PX", 100_000);
        }*/

        ScanResult<String> scan = jedis.scan(0);
        System.out.println("result:" + scan.getResult());
        System.out.println("cursor:" + scan.getStringCursor());

//        System.out.println(jedis.set(key2, String.valueOf(System.currentTimeMillis()), "NX", "PX", 10 * 1000));
        jedis.close();
    }
}
