package com.base.sort;

import java.util.ArrayList;
import java.util.List;

public class GDOrderTest {

    public static void main(String[] args) {
        List<MetaData> list = new ArrayList<>();

        //1.模拟出数据库查询出的第一页的结果集
        int n = 0;
        for (int i = 0; i < 20; i++) {
            MetaData gdOrder = new MetaData();
            gdOrder.setIndex(i);

            if (i % 6 == 0) {
                gdOrder.setGdOrder(n);
                n++;
            }

            list.add(gdOrder);
        }

        System.out.println(list);

        //处理集合数据
        List<MetaData> orderList = new ArrayList<>();
        for (int m = 0; m < list.size(); m++) {
            MetaData gdOrder = list.get(m);
            int nGdOrder = gdOrder.getGdOrder();
            if (nGdOrder == 0) {
                orderList.add(gdOrder);
                continue;
            }

            orderList.add(nGdOrder, gdOrder);
        }

        System.out.println(orderList);

    }

}
