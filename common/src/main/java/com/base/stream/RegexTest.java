package com.base.stream;

import javax.lang.model.element.NestingKind;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author:zhao.song
 * @Date:2019/11/22 23:01
 * @Description:
 */
public class RegexTest {

    public static void main(String[] args) {
        String s = "kevin偷偷3131 <iframe wechat=\"\" frameborder=\"0\" src=\"https://v.qq.com/txp/iframe/player.html?vid=p0327aca\" allowFullScreen=\"true\"></iframe>2314141";
        s +=s;
        s +=s;
        s +=s;
        String vid = "";
        String replace = "<iframe class=\"video_iframe rich_pages\" data-vidtype=\"1\" style=\"position: relative;z-index:1;height:240px;width:320px;\" scrolling=\"no\" src=\"https://v.qq.com/iframe/preview.html?width=500&height=375&auto=0&vid="+vid+" allowfullscreen=\"1\" frameborder=\"0\"></iframe>";
//        String patterns="/<iframe\\s+wechat=[^>]*(src=\\\"[^\\\"]*\\\")[^>]*><\\/iframe>/g";
//        String patterns="(\\d+)";
//        String patterns="<\\s*iframe\\s+([^>]*)\\s*>";
        String patterns="\\s*\\S*\\s*<iframe\\s+wechat([^>]*)>\\s*\\S*\\s*</iframe>";
        String elePatterns="<iframe\\s+wechat([^>]*)>\\s*\\S*\\s*</iframe>";
//        String patterns="<iframe.*></iframe>";
        Pattern pattern = Pattern.compile(patterns);
        Pattern elePattern = Pattern.compile(elePatterns);

        Matcher m = pattern.matcher(s);
        StringBuffer sb = new StringBuffer();
        ArrayList<String> list = new ArrayList<String>();
        while (m.find()){

            String str = m.group();
            list.add(str);
            int i1 = str.indexOf(".html?vid=");
            String substring1 = str.substring(i1 + 1);
            int i2 = substring1.indexOf('"',9);
            Matcher ele = elePattern.matcher(str);
            String s1 = ele.find() ? ele.group() : "";
            vid = substring1.substring(9, i2);
            replace = "<iframe class=\"video_iframe rich_pages\" data-vidtype=\"1\" style=\"position: relative;z-index:1;height:240px;width:320px;\" scrolling=\"no\" src=\"https://v.qq.com/iframe/preview.html?width=500&height=375&auto=0&vid="+vid+"\" allowfullscreen=\"1\" frameborder=\"0\"></iframe>";
            String result = str.replace(s1, replace);
            sb.append(result);
        }
        System.out.println(list);
        System.out.println(list.size());
        /*String substring = s.substring(index, s.length());*/
//        sb.append(substring);
        System.out.println(s);
        System.out.println(sb.toString());
    }



}
