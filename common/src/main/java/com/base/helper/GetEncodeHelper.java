package com.base.helper;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Author:zhao.song
 * @Date:2019/11/23 16:51
 * @Description:获取编码格式
 */
public class GetEncodeHelper {



    public static String getEncode(String source, Collection<String> charsetList) {

        if (charsetList == null || charsetList.size() == 0) {
            return null;
        }
        for (String verifyCharset : charsetList) {
            String charset = isEncode(source, verifyCharset);
            if (charset != null) {
                return charset;
            }
        }

        return null;
    }

    /**
     * @Description:
     * @param: [source, charset]
     * @return: java.lang.String
     * @Date: 2019/12/12 21:46
     */
    public static String isEncode(String source, String charset) {

        try {
            if (source.equals(new String(source.getBytes(charset),charset))) {
                return charset;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
/*        Set<String> charsetList = new HashSet<>();
        Collections.addAll(charsetList,  "utf-8","gbk","iso8859-1", "gb2312");
        Collection<String> syncCharsetList = Collections.synchronizedCollection(charsetList);
        String target = URLEncoder.encode("中文", "utf-8");
        target = URLDecoder.decode(target, "utf-8");
//        String target = "中文";
        String encode = getEncode(target, syncCharsetList);
        System.out.println(target);
        System.out.println(new String(target.getBytes(encode),encode));
        System.out.println(encode);*/


        /*String s = "000000000000000000000000000000000000000000000000001111111111000000000000000000000011111111111110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001000000010000000000000000000000000000000000000000000000000001000000000110000000100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000100110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001010000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001110000000000000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        System.out.println(s.charAt(738 - 1));*/

        String content = " ";
        String substring = content.substring(0,1);
        System.out.println(substring);

    }
}
