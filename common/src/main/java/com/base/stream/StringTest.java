package com.base.stream;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.List;

/**
 * @Author:zhao.song
 * @Date:2019/12/3 11:21
 * @Description:
 */
public class StringTest {


    public static void main(String[] args) {
        /*String sSource = "a";
        int endIndex = !sSource.contains("-") ? sSource.length() : sSource.indexOf("-");
        String sSourceNameJq = sSource.substring(0,endIndex);
        System.out.println(sSourceNameJq);*/
        int[] groupIdArray = new int[]{1, 2, 3, 14};
        List<Integer> list = JSON.parseArray(Arrays.toString(groupIdArray), Integer.class);
        System.out.println(list);
    }
}
