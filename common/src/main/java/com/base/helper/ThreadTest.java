package com.base.helper;

/**
 * description  ThreadTest <BR>
 * <p>
 * author: zhao.song
 * date: created in 10:08  2022/10/19
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class ThreadTest {


    public static void main(String[] args) {
        String s = "a\0\0\0";
        final String s1 = s.replaceAll("\0", "");
        System.out.println(s1);
    }
}
