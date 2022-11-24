package com.base.util.exception;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * description  CustomException <BR>
 * <p>
 * author: zhao.song
 * date: created in 13:58  2022/8/12
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class CustomException extends RuntimeException {

    private final Set<String> exCache = new LinkedHashSet<>();

    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }

    public void add(String message) {
        exCache.add(message);
    }

    public Set<String> getExCache() {
        return this.exCache;
    }

    @Override
    public String getMessage() {
        return "异常：" + getExCache();
    }
}
