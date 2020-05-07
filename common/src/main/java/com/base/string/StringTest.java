package com.base.string;

import com.fasterxml.jackson.databind.ser.std.MapProperty;
import com.sun.deploy.util.ArrayUtil;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.base.clazz.impl.IResult;
import com.trs.common.utils.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:
 * @Author:zhao.song
 * @Date:2020/1/4 22:46
 * @Version:1.0
 */
public class StringTest implements IResult {


    public static void main(String[] args) {
        HashMap<String, Object> tempMap = new HashMap<>();
        tempMap.put("key1","2");
        tempMap.put("key2", "22");
        System.out.println(ArrayUtil.mapToString(tempMap));

        List<String> srcList = Stream.of("a", "b", "c").collect(Collectors.toList());
        String s1 = appendByTSeparator(",", srcList);
        System.out.println(s1);

        String target = "a,b;c/d:e";
        Optional<List<String>> optional = parseEleBySeparator(target, ",;/:");
        List<String> result = optional.isPresent() ? optional.get() : null;
        System.out.println(result);

        System.out.println((int)Math.ceil(Math.random() * 100_000));


        Map<String, Object> hparams = new HashMap<>();
        hparams.put("tenantId", 0);
        hparams.put("userId", "AAAABQAFYWRtaW4AFWFkbWluLDAsMTU3OTAxMzM4NDIyMgAAAC8wLQIULmSCNx1LFW-WuurRNRs3xui9Td4CFQCLBX2tAcmriuuSzjmUQ0EvsSaVPw..");
        hparams.put("videoId", 1);
        String resultFromat = String.format("%s", hparams);
        System.out.println(resultFromat);

    }

    public static String appendByTSeparator(String separator, List<String> srcList) {
        if (StringUtils.isEmpty(separator)) {
            separator = ";";
        }

        if (srcList == null || srcList.isEmpty()) {
            return "";
        }

        return String.join(separator, srcList);
    }

    public static Optional<List<String>> parseEleBySeparator( String target,String delim) {
        ArrayList<String> list = new ArrayList<>();
        Optional<List<String>> optional = Optional.ofNullable(list);
        if (StringUtils.isEmpty(delim)) {
            list.add("");
            return optional;
        }
        StringTokenizer tokenizer = new StringTokenizer(target,delim);

        while (tokenizer.hasMoreElements()) {
            list.add(tokenizer.nextToken());
        }
        return optional;

    }

}
