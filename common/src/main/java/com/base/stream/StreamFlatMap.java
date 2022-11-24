package com.base.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author:zhao.song
 * @Date:2019/11/20 23:26
 * @Description:
 */
public class StreamFlatMap {

    public static void main(String[] args) {
//        String s1 = "SELECT upper(c3) 测试c3,lower(c7) 测试c7,rowid ro_new from ORACLE19_MATER_VIEW.test_ma_view1;";
        String s1 = "rowid";
//        System.out.println(s1.matches("\\b(?i)old.id\\s*\\S*"));

//        System.out.println(s1.matches("(?i)select\\s+[^;]*\browid\b((?!;)[.\\r\\n])*;"));

        System.out.println(s1.matches("\\b(?i)rowid\\s*\\S*"));


        /*String[] strArr = new String[]{"heLlo", "world"};
        List<String> list = Arrays.stream(strArr)
                .map(word -> word.split(""))
                .flatMap(wordArr -> Arrays.stream(wordArr))
                .map(element->element.toLowerCase())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(list);*/


        List<String> list = Stream.of("12", "323").collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append("?,");
        }
        String newStr = sb.substring(0, sb.lastIndexOf(","))+") ";
        System.out.println(newStr);
    }
}
