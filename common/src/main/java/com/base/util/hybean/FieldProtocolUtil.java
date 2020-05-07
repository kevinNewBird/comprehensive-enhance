package com.base.util.hybean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author:zhao.song
 * @Date:2020/1/16 13:13
 * @Version:1.0
 */
public class FieldProtocolUtil {
    public static Map<String, String> hyFieldsMap = null;


    /**
     * Description: 获取参数对应的海贝字段名
     * @param paramName
     * @return
     */
    public static String getPaperHyField(String paramName) {
        if (hyFieldsMap == null) {
            initPaperHyFieldsMap();
        }
        return hyFieldsMap.get(paramName);
    }

    /**
     * Description: 初始化读报评报的参数和海贝数据库字段对应的集合
     */
    public static void initPaperHyFieldsMap() {
        if (hyFieldsMap != null) {
            return;
        }
        synchronized (FieldProtocolUtil.class) {
            if (hyFieldsMap != null) {
                return;
            }
            HashMap<String, String> tempMap = new HashMap<>();
            tempMap.put("", "");
            tempMap.put("", "");
            tempMap.put("", "");
            tempMap.put("", "");
            tempMap.put("", "");
            tempMap.put("", "");
            hyFieldsMap = tempMap;
        }
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }
}
