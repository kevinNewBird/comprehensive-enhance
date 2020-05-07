
package com.base.vavr;

import io.vavr.control.Try;

import java.util.EmptyStackException;

/**
 * @Description:java8函数式库vavr(引入外部依赖vavr)  -- 容器Try
 * @Author:zhao.song
 * @Date:2019/12/13 13:34
 * @Version:1.0
 */
public class TryTest {


    /**
     * @description:
     *     1.Try是一个容器 , 来包装一段可能产生异常的代码.Option用来包装可能产生null的对象.
     *     2.可以用于检查是否产生了异常,亦可以在产生异常时获取一个默认值,或者根据具体需求在抛出一个异常
     * @param args
     */
    public static void main(String[] args) {
        Try<Integer> of = Try.of(()->1 / 0);
        try {
            Integer orElseThrow = of.getOrElseThrow(Throwable::getCause);
            boolean flag = of.isFailure();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println(throwable.getMessage());
        }
    }
}
