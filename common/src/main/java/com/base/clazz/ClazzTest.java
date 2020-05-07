package com.base.clazz;

import com.base.clazz.impl.IResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:获取接口(或父类)的所有实现类(或子类)
 * @Author:zhao.song
 * @Date:2020/1/13 11:58
 * @Version:1.0
 */
public class ClazzTest {

    public static void main(String[] args) {
        /*long startV3 = System.currentTimeMillis();
        ArrayList<Class> list = AcquireIClazzImplementsV3.getAllClassByInterface(IResult.class);
        System.out.println(list);
        long endV3 = System.currentTimeMillis();
        System.out.println(AcquireIClazzImplementsV3.class.getName() +
                "的getAllClassInterface()查找所有接口实现或子类,花费时间:" + (endV3 - startV3));*/

        /*try {
            Thread.sleep(30_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long startV4 = System.currentTimeMillis();
        List<Class> list2 = IDiscoveryFactory.getAllClassByInterface(IResult.class);
        System.out.println(list2);
        long endV4 = System.currentTimeMillis();
        System.out.println(IDiscoveryFactory.class.getName() +
                "的getAllClassInterface()查找所有接口实现或子类,花费时间:" + (endV4 - startV4));*/

        String source = "23;1223";
        String[] split = source.split(";");
        String s = String.format("[%s TO %s]", split[0], split[1]);
        System.out.println(s);
    }
}
