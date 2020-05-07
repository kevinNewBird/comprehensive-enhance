package com.base;

/**
 * Hello world!
 * Description: redis分布式锁<BR/>
 * 为了实现分布式锁，需要确保锁同时满足以下四个条件：
 * <BR/>
 *   1.互斥性。在任意时刻，只有一个客户端能持有锁
 *   2.不会发送死锁。即使一个客户端持有锁的期间崩溃而没有主动释放锁，也需要保证后续其他客户端能够加锁成功
 *   3.加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给释放了。
 *   4.容错性。只要大部分的Redis节点正常运行，客户端就可以进行加锁和解锁操作。
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
