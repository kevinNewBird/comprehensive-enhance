package com.base.vavr;

import io.vavr.collection.List;

import java.io.Serializable;

/**
 * @Description:java8函数式库vavr(引入外部依赖vavr)-- 集合Collections
 * @Author:zhao.song
 * @Date:2019/12/14 15:42
 * @Version:1.0
 */
public class CollectionsTest {


    /**
     * @description:集合Colletions
     *      1.传统的java中的集合通常是可变集合,这通常是造成错误的根源.特别是在并发场景下.此外jdk中
     *         集合类存在一些不足.例如jdk中的集合接口提供的一个方法clear,该方法删除所有元素后而且没有返回值.
     *         在并发场景下大多集合都会产生问题,因此有了诸如ConcurrentHashMap这样的类.此外jdk还通过一些其它的
     *         方法创建不可变集合,但误用某些方法时会产生异常.如下,创建不可修改List(Arrays工具类转化的List为Arrays
     *         的内部类),在误调用add的情况下会产生UnsupportedOperationException异常.
     *      2.Vavr中的集合则会避免这些问题,并且保证了线程安全、不可变等特性. 在Vavr中创建一个list,
     *         实例并且不包含那些会导致UnsupportedOperationException异常的方法,且不可变,这样避免误用,造成
     *         错误. 此外还可以通过提供的API执行计算任务
     *         Vavar提供了在java集合框架中绝大多数常见的类,并且实现了其所有特征.Vavr提供的集合工具使得编写的代码更加
     *         紧凑,健壮,并且提供了丰富的功能
     * @param args
     */
    public static void main(String[] args) {

        List<Integer> list = List.of(1, 2, 3);
        System.out.println(list.filter(a->a>2));
        //执行计算,判断集合中的元素是否具有唯一性
        boolean unique = list.existsUnique((a) -> {
            Integer newA = Integer.valueOf(a);
            int count = 0;
            if (newA == 3) {
                count++;
            }
            if (count == 1) {
                return true;
            }
            return false;
        });
        System.out.println(unique);

    }
}
