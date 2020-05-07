package com.base.base_level.c9;

import java.util.stream.Stream;

/**
 * @Description:线程间的通讯--多线程通信可能导致假死(reason[线程blocked放弃cpu执行权])
 * @Author:zhao.song
 * @Date:2019/12/30 18:51
 * @Version:1.0
 */
public class ProducerConsumerV1 {

    private int i = 0;

    final private Object LOCK = new Object();

    private volatile boolean isProduced = false;

    public void produce() {
        synchronized (LOCK) {
            if (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                i++;
                System.out.println("P->" + i);
                LOCK.notify();
                isProduced = true;
            }
        }
    }

    public void consume() {
        synchronized (LOCK) {
            if (isProduced) {
                System.out.println("C->" + i);
                LOCK.notify();
                isProduced = false;
            } else {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProducerConsumerV1 pc = new ProducerConsumerV1();
        Stream.of("P1","P2").forEach(n->{
            new Thread(n){
                @Override
                public void run() {
                    pc.produce();
                }
            }.start();
        });

        Stream.of("C1","C2").forEach(n->{
            new Thread(n){
                @Override
                public void run() {
                    pc.consume();
                }
            }.start();
        });
    }
}
