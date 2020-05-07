package com.base.util;

public interface IDistributedLock {
    /**
     * 获取锁
     * @return:锁标识
     */
    String acquire();

    /**
     * 释放锁
     * @param indentifier
     * @return
     */
    boolean release(String indentifier);
}
