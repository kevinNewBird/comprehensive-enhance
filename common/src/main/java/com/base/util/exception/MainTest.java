package com.base.util.exception;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * description  MainTest <BR>
 * <p>
 * author: zhao.song
 * date: created in 14:01  2022/8/12
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class MainTest {


    public static void main(String[] args) {

        List<Integer> result = Stream.of(Stream.of(1, 2, 3).collect(Collectors.toList()), Stream.of(4, 5, 6).collect(Collectors.toList()))
                .skip(1).findFirst().orElse(Collections.emptyList());
        System.out.println(result);
        CustomException ex1 = new CustomException();
        ex1.add("1");
        System.out.println(ex1);

        CustomException ex2 = new CustomException();
        ex2.add("2");
        System.out.println(ex2);

    }
}
