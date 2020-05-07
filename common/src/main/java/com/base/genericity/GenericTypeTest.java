package com.base.genericity;

import com.alibaba.fastjson.JSON;
import com.base.sort.MetaData;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:
 * @Author: zhao.song
 * @Date: 2020/3/23 15:11
 * @Version: 1.0
 */
public class GenericTypeTest {


    public static void main(String[] args) {
        /*new Cat().printEle("sharry");
        new Dog<Integer>().printEle(20);

        List<Integer> list = Stream.of("12", "23", "3", "5", "1").map(num->Integer.valueOf(num)).collect(Collectors.toList());
        Collections.sort(list);
        System.out.println(list);*/
        List<MetaData> list = new ArrayList<>();
        MetaData m1 = new MetaData();
        m1.setGdOrder(0);
        m1.setIndex(0);
        MetaData m2 = new MetaData();
        m2.setGdOrder(3);
        m2.setIndex(1);
        MetaData m3 = new MetaData();
        m3.setGdOrder(0);
        m3.setIndex(2);
        MetaData m4 = new MetaData();
        m4.setGdOrder(5);
        m4.setIndex(3);
        MetaData m5 = new MetaData();
        m5.setGdOrder(0);
        m5.setIndex(4);
        MetaData m6 = new MetaData();
        m6.setGdOrder(0);
        m6.setIndex(5);
        MetaData m7 = new MetaData();
        m7.setGdOrder(0);
        m7.setIndex(6);
        MetaData m8 = new MetaData();
        m8.setGdOrder(0);
        m8.setIndex(7);
        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m4);
        list.add(m5);
        list.add(m6);
        list.add(m7);
        list.add(m8);
        System.out.println(list);
        Vector<Integer> vIdList = new Vector<>();
        Collections.addAll(vIdList, 1, 2, 3, 4, 5, 6, 7, 8);
        list.sort(MetaData::compareTo);
        List<MetaData> result = aaa(list, vIdList);
        System.out.println(result);

    }


    private static List<MetaData> aaa(List<MetaData> orderInfoList, Vector<Integer> vIdList) {
        List<MetaData> resortOrderInfoList = new ArrayList<>();
        List<MetaData> resortTempOrderInfoList = new ArrayList<>();
        for (int i = 0; i < orderInfoList.size(); i++) {
            MetaData originOrderInfo = orderInfoList.get(i);
            //GDorder为0表示不需要进行固定位置,对应数据库chnlodc表中GDORDER字段为0
            if (originOrderInfo.getGdOrder() == 0) {
                resortOrderInfoList.add(originOrderInfo);
                continue;
            }
            resortOrderInfoList.add(originOrderInfo.getGdOrder() - 1, originOrderInfo);
        }
        return resortOrderInfoList;
    }
}
