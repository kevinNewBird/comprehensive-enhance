package com.base.vavr;

import io.vavr.control.Option;

import java.util.Optional;

/**
 * @Description:java8函数式库vavr(引入外部依赖vavr)-- 对象容器Option(与Optional类似)
 * @Author:zhao.song
 * @Date:2019/12/13 11:57
 * @Version:1.0
 */
public class OptionTest {

    public static void main(String[] args) {

        Option<Object> option = Option.of(null);
        Object value = option.getOrElse("IS NULL VALUE");
        System.out.println(value);
    }
}
