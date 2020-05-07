package com.base.vavr;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.vavr.API;
import org.springframework.util.StringUtils;

import static io.vavr.API.$;
import static io.vavr.API.Case;

/**
 * @Description:java8函数式库vavr(引入外部依赖vavr)-- 模式匹配Pattern Matching
 * @Author:zhao.song
 * @Date:2019/12/14 17:34
 * @Version:1.0
 */
public class PatternMatchingTest {


    /**
     * @decription:模式匹配初步运用
     *      1.当我们执行一个计算或根据输入返回一个满足条件的值时,我们通常会用到if语句.大量使用if语句 , 会使整体结构膨胀
     *      2.在Vavr中, 我们通过Match方法替换switch块.每个条件检查都通过Case方法调用来替换.$()来替换条件并完成表达式计算得到结果
     * @param args
     */
    public static void main(String[] args) {

        int input = 2;
        String output = API.Match(input).of(
                Case($(1), "one"),
                Case($(2), "two"),
                Case($(), "?")
        );
        System.out.println(output);
       /*
        String userId = "0243241";
        boolean flag = StringUtils.isEmpty(userId) || (userId.length() > 1 && userId.startsWith("0"))
                || !userId.matches("[0-9]*");
        System.out.println(flag);

        String sflag = "false";
        boolean matches = sflag.matches("[true]|[false]");
        System.out.println(matches);

        String s = "[\n{\n\"RIGHTINDEX\" : \"775\",\n\"OPERNAME\" : \"sharedmaterial.audios.upload\"\n},\n{\n\"RIGHTINDEX\" : \"776\",\n\"OPERNAME\" : \"sharedmaterial.audios.newdocument\"\n},\n{\n\"RIGHTINDEX\" : \"777\",\n\"OPERNAME\" : \"sharedmaterial.audios.takedoc\"\n},\n{\n\"RIGHTINDEX\" : \"778\",\n\"OPERNAME\" : \"sharedmaterial.audios.movepersonal\"\n},\n{\n\"RIGHTINDEX\" : \"779\",\n\"OPERNAME\" : \"sharedmaterial.audios.movefile\"\n},\n{\n\"RIGHTINDEX\" : \"780\",\n\"OPERNAME\" : \"sharedmaterial.audios.delete\"\n}\n]";
        JSONArray objects = JSON.parseArray(s);
        System.out.println(objects);*/
        String classify = "ssss.kkkk";
        System.out.println(classify.split("\\.")[0]);
    }
}
