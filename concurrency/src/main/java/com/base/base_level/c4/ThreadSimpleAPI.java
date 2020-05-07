package com.base.base_level.c4;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author:zhao.song
 * @Date:2019/10/18 17:30
 * @Description:
 */
public class ThreadSimpleAPI {

    public static void main(String[] args) throws InterruptedException {

        int[] arr = new int[]{1,2,3};
        Optional<int[]> optional = Optional.of(arr);
        int[] value = optional.get();
        System.out.println(Arrays.toString(value));
        Thread.sleep(200);


        Thread t1 = new Thread(()->{
                Optional.of("hello").ifPresent(System.out::println);
        },"t1");
        t1.setPriority(Thread.MAX_PRIORITY);//优先级设置
        t1.start();
        Optional.of(t1.getName()).ifPresent(System.out::println);
        Optional.of(t1.getId()).ifPresent(System.out::println);//线程id
        Optional.of(t1.getPriority()).ifPresent(System.out::println);//默认优先级,企图改变线程执行的优先顺序

    }


}
