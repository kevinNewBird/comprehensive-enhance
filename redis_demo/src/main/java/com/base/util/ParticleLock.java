package com.base.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/9/24 10:15
 * @version: 1.0
 ***********************/
public class ParticleLock {

    private static volatile Map<Integer, Object> LOCK_MONITOR_MAP = new HashMap<>();

    /**
     * Description: 获取颗粒锁对象(只适用单应用,集群失效) <BR>
     *
     * @param oChnlId:
     * @author zhao.song    2020/9/23 15:51
     */
    public static synchronized Object getParticleLockMonitor(int oChnlId) {

        if (LOCK_MONITOR_MAP.get(oChnlId) == null) {
            final Object lockMonitor = new Object();
            LOCK_MONITOR_MAP.put(oChnlId, lockMonitor);

            return lockMonitor;
        }
        return LOCK_MONITOR_MAP.get(oChnlId);
    }


    public static void demo(){
        System.out.println(Unsafe.getUnsafe().getAddress(1));
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        final Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        final Unsafe unsafe = (Unsafe) field.get(Unsafe.class);
        System.out.println(unsafe.getAddress(1));

    }
}
