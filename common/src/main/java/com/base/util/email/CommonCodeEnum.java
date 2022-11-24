package com.base.util.email;

/**
 * description  CommonCodeEnum <BR>
 * <p>
 * author: zhao.song
 * date: created in 11:02  2022/8/11
 * company: TRS信息技术有限公司
 * version 1.0
 */

public enum CommonCodeEnum implements StatusCode {
    ADD(1, "ssss");

    int code;

    String msg;

    CommonCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
