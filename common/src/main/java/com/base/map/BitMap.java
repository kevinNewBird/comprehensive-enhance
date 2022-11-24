package com.base.map;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * description  BitMap <BR>
 * <p>
 * author: zhao.song
 * date: created in 17:13  2022/9/28
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class BitMap {


    // 位数
//    private final int stateBitNum;

    public static void main(String[] args) throws InterruptedException {
        final BigInteger bigInteger = new BigInteger("12222222222222222222222222");
        System.out.println(1>>>16);
        System.out.println(1>>8);
        System.out.println(Character.digit("1+222".charAt(1), 10));
        long num = 1L<<63;
        System.out.println(num);

        TimeUnit.SECONDS.sleep(20);

    }
}
