package com.base.genericity;

/**
 * @Description: 泛型的使用方式: 直接在继承接口时确认泛型类型
 * @Author: zhao.song
 * @Date: 2020/3/23 15:12
 * @Version: 1.0
 */
public class Cat implements IAnimal<String> {

    @Override
    public void printEle(String oCatName) {
        System.out.println("小猫的名字:"+oCatName);
    }
}
