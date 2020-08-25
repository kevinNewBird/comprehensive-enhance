
package com.base.vavr;

import io.vavr.control.Try;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @Description:java8函数式库vavr(引入外部依赖vavr)  -- 容器Try
 * @Author:zhao.song
 * @Date:2019/12/13 13:34
 * @Version:1.0
 */
public class TryTest {

    //    private static final Logger logger = LoggerFactory.getLogger(TryTest.class);
    private static final Logger logger = Logger.getLogger(TryTest.class);


    /**
     * @description:
     *     1.Try是一个容器 , 来包装一段可能产生异常的代码.Option用来包装可能产生null的对象.
     *     2.可以用于检查是否产生了异常,亦可以在产生异常时获取一个默认值,或者根据具体需求在抛出一个异常
     * @param args
     */
    public static void main(String[] args) {
        /*Try<Integer> of = Try.of(()->1 / 0);
        try {
            Integer orElseThrow = of.getOrElseThrow(Throwable::getCause);
            boolean flag = of.isFailure();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println(throwable.getMessage());
        }*/

        ArrayList<Object> list = new ArrayList<>();


        Try.run(()->{
            Collections.addAll(list, 1,8,3, 5);
        }).andFinally(()->{
            list.stream().forEach(System.out::println);
        });


        Try.run(()->{
            logger.info("start generate a exception...");
            int i = 1 / 0;
            logger.info("end generate a exception...");
        });

        logger.info("===========================================");

        Integer orElseGet1 = Try.of(() -> {
            String oFilterDay = "8";
            return Integer.parseInt(oFilterDay);
        }).getOrElseGet((e) -> 7);
        System.out.println(-orElseGet1);

        Boolean orElseGet = Try.of(() -> {
            //3.记录日志
            int i = 1 / 0;
            return true;
        }).getOrElseGet((e) -> {
            /*System.out.println(e.getMessage());
            logger.info("网络资源下载记录和缓存发生异常!", e);*/
            return false;
        });
        System.out.println(orElseGet);
    }
}
