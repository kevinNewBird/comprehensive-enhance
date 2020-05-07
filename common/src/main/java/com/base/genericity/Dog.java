package com.base.genericity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 泛型的使用方式: 不确定泛型类型 , 泛型跟着接口
 * @Author: zhao.song
 * @Date: 2020/3/23 15:14
 * @Version: 1.0
 */
public class Dog<E> implements IAnimal<E> {
    @Override
    public void printEle( E e) {
        List<Object> list = new ArrayList<>();

        System.out.println("小狗:"+e);
    }
}
