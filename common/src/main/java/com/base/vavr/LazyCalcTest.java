package com.base.vavr;

import io.vavr.Lazy;
import io.vavr.collection.List;

import java.util.function.Supplier;

/**
 * @Description:java8函数式库vavr(引入外部依赖vavr)  -- Lazy延迟计算
 * @Author:zhao.song
 * @Date:2019/12/14 17:13
 * @Version:1.0
 */
public class LazyCalcTest {

    /**
     * @description: Lazy是一个容器
     *     1.表示一个延迟计算的值.计算被推迟 ,直到需要时才计算. 此外,计算的值被缓存或存储起来,当需要时被返回,而不需要重复计算
     * @param args
     */
    public static void main(String[] args) {

/*        Lazy<Double> of = Lazy.of(Math::random);
        Lazy<Double> of = Lazy.of(
                new Supplier<Double>() {
                    @Override
                    public Double get() {
                        return calc(1,232,4);
                    }
                }
        );*/

        Lazy<Double> of = Lazy.of(() -> calc(1, 23, 45));
        //判断是否进行了计算
        boolean flag = of.isEvaluated();
        System.out.println("是否进行了计算:"+flag);
        Double random = of.get();
        boolean newFlag = of.isEvaluated();
        System.out.println("是否进行了计算:"+newFlag);
        System.out.println(random);

    }

    public static double calc(Integer...ele){
        return List.of(ele).sum().intValue();
    }
}
