package com.base.vavr;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple8;

/**
 * @Description:java8函数式库vavr(引入外部依赖vavr)  -- 元组Tuple
 * @Author:zhao.song
 * @Date:2019/12/13 13:07
 * @Version:1.0
 */
public class TupleTest {

    /**
     * @description:
     *    1.Tuple是函数式编程中一种常见的概念.
     *    2.Tuple是一个不可变,并且能够以类型安全的形式保存多个不同类型的对象.
     *    3.Tuple中最多只能有8个元素
     *    4.引用元素时,从1开始,而不是0
     *    5.Tuple中的元素必须是所声明的类型
     * @scene:
     *    1.需要返回多个对象时, 可以考虑使用Tuple(同样的还是Either)
     * @param args
     */
    public static void main(String[] args) {
        Tuple2<String, String> tuple2 = Tuple.of("tom", "trs");
        System.out.println(tuple2._1);
        System.out.println(tuple2._2);
    }

}
