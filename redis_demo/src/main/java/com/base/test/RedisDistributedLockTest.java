package com.base.test;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/11/2 17:40
 * @version: 1.0
 ***********************/
public class RedisDistributedLockTest {

    final static AtomicInteger atomic = new AtomicInteger(0);

    private Logger logger = Logger.getLogger(getClass());
    private static Random random = new Random();
    Jedis redis = new Jedis("127.0.0.1", 6379);

    private long getSleepTime() {
        return (random.nextInt(100) * 20L + 1000L);
    }

    /**
     * 统一获取加锁的KEY，方便统一处理<BR>
     */
    private String getLockKey() throws Exception {
        return RedisDistributedLockTest.class.getName() + "-" + getClass().getName() + "-" + makeLockKey();
    }

    final protected void execute() throws Exception {
        if (checkJobIsLock()) {
            logger.info("该任务已经被一个节点锁定，KEY为" + getLockKey());
        } else {
            try {
                Thread.sleep(getSleepTime());
                if (lockJob()) {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println("执行任务结果");
                        atomic.incrementAndGet();
                    } catch (Exception ex) {
                        logger.error("定时任务运行出错！", ex);
                        throw ex;
                    } finally {
                        unLockJob();
                    }
                } else {
                    logger.info("该节点对任务加锁失败，休眠时被节点[" + getLockValue().orElse("未能成功获取节点信息！") + "]加锁成功！KEY为" + getLockKey());
                }
            } catch (InterruptedException e) {
                logger.error("线程休眠失败！", e);
            }
        }
    }


    /**
     * 给相关任务加锁<BR>
     */
    private boolean lockJob() throws Exception {
        if (checkJobIsLock()) {
            return false;
        }

        String result = redis.set(getLockKey(), new Date().toString(), "NX", "EX", getLockLiveTime());
        logger.info(String.format("加锁任务！KEY为%s,Redis返回了%s", getLockKey(), result));
        return result != null && result.equals("OK");
    }

    /**
     * 获取给任务加锁的节点的HostName-IP<BR>
     */
    private Optional<String> getLockValue() throws Exception {
        if (checkJobIsLock()) {
            return Optional.empty();
        }
        return Optional.ofNullable(redis.get(getLockKey()));
    }

    /**
     * 解锁相关任务<BR>
     */
    protected boolean unLockJob() throws Exception {
        Long result = redis.del(getLockKey());
        logger.info("解锁任务！KEY为" + getLockKey() + ",Redis返回了" + result);
        return true;
    }

    /**
     * 判断任务是否被加锁了<BR>
     */
    private boolean checkJobIsLock() throws Exception {
        return redis.exists(getLockKey());
    }

    /**
     * 获取相关锁默认的有效期（默认一小时）<BR>
     */
    protected int getLockLiveTime() {
        return 60 * 60;
    }

    /**
     * 各个定时器去自行定义锁的Key<BR>
     */
    protected String makeLockKey() throws Exception {
        return "test";
    }

    public static void main(String[] args) throws InterruptedException {


        IntStream.range(0, 10).forEach((num) -> {
            new Thread(() -> {
                try {
                    new RedisDistributedLockTest().execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });

        TimeUnit.SECONDS.sleep(60);

        System.out.println(atomic.get());
    }

}
