package com.base.util;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Description: redis分布式锁工具类
 */
public class RedisDistributedLock implements IDistributedLock {
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(RedisDistributedLock.class);
    //    private static Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);
    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;

    private volatile String SET_LOCK_STATUS = null;

    /**
     * 参数说明:
     * NX--not exists,只有key不存在时才把key value set 到redis中
     * XX--is exists,只有key存在时,才把key value set 到redis中
     */
    private static final String SET_IF_NOT_EXIST = "NX";

    /**
     * 参数说明:
     * EX--seconds秒
     * PX--milliseconds毫秒
     */
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * redis客户端
     */
    private Jedis jedis;


    /**
     * 分布式锁的键值
     */
    private String lockKey;

    /**
     * 锁的超时时间 10s
     */
    long expireTime = 100_000;

    /**
     * 锁等待, 防止线程饥饿
     */
    int acquireTimeOut = 10_000;


    /**
     * Description: 获取指定键值的锁
     *
     * @param jedis:redis客户端
     * @param lockKey:锁的键值
     */
    public RedisDistributedLock(Jedis jedis, String lockKey) {
        this.jedis = jedis;
        this.lockKey = lockKey;
    }

    /**
     * Description: 获取指定键值的锁,同时设置获取锁超时时间
     *
     * @param jedis
     * @param lockKey
     * @param acquireTimeOut
     */
    public RedisDistributedLock(Jedis jedis, String lockKey, int acquireTimeOut) {


        this.jedis = jedis;
        this.lockKey = lockKey;
        this.acquireTimeOut = acquireTimeOut;
    }

    /**
     * Description: 获取指定键值的锁, 同时设置获取锁超时时间和锁过期时间
     *
     * @param jedis
     * @param lockKey
     * @param expireTime
     * @param acquireTimeOut
     */
    public RedisDistributedLock(Jedis jedis, String lockKey, int expireTime, int acquireTimeOut) {
        this.jedis = jedis;
        this.lockKey = lockKey;
        this.expireTime = expireTime;
        this.acquireTimeOut = acquireTimeOut;
    }

    @Override
    public String acquire() {
        try {
            //获取锁的超时时间 ,超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeOut;
            //随机生成一个value
            String requireToken = UUID.randomUUID().toString();
            while (System.currentTimeMillis() < end) {
                SET_LOCK_STATUS = jedis.set(lockKey, requireToken, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
                if (LOCK_SUCCESS.equals(SET_LOCK_STATUS)) {
                    logger.info(String.format("acquire lock success,requestToken:%s", requireToken));
                    logger.info(Thread.currentThread()+"====获取锁成功!");
                    return requireToken;
                }
                /*try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }*/
            }
        } catch (Exception e) {
            logger.error("acquire lock due to error", e);
            logger.info(Thread.currentThread()+"====获取锁失败!");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean release(String indentifier) {
        if (StringUtils.isEmpty(indentifier)) {
            return false;
        }

        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
//        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return 1 else return 0 end";

        Object result = new Object();

        try {
            //优点:lua脚本是原子性的
            //Lua代码传到jedis.eval()方法里，并使参数KEYS[1]赋值为lockKey，ARGV[1]赋值为requestId。eval()方法是将Lua代码交给Redis服务端执行。
            //首先获取锁对应的value值，检查是否与requestId相等，如果相等则删除锁（解锁）
            result = jedis.eval(script, Collections.singletonList(lockKey)
                    , Collections.singletonList(indentifier));

            /**
             * tip: 错误解锁--判断加锁与解锁是不是同一个客户端
             *
             *  if (requestId.equals(jedis.get(lockKey))) {
             *                 // 若在此时，这把锁突然不是这个客户端的，则会误解锁（本身该锁已过期，但是被另一个重新设置了一把，此时已经过了上面的if判断，则会导致误删锁）
             *                 jedis.del(lockKey);
             *  }
             */
//            System.out.println("1".equalsIgnoreCase(result.toString()));
            if (RELEASE_SUCCESS.equals(result)) {
                logger.info(String.format("release lock success, requestToken:%s", indentifier));
                return true;
            }
        } catch (Exception e) {
            logger.error("release lock due to error", e);
        } finally {
            /*if (jedis != null) {
                jedis.close();
            }*/
        }

        logger.info(String.format("release lock failed, requestToken:%s,result:%s", indentifier, result));
        return false;
    }

    public static void main(String[] args) {

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "ssss");

        int[] arr = {1, 2, 3};
        List<Integer> collect = Arrays.stream(arr).boxed().collect(Collectors.toList());
        System.out.println(collect.stream().map(i -> map.get(i)).collect(Collectors.toList()));

    }
}
