package com.base.ibatis.exceptions;

/**
 * description  IncompleteElementException <BR>
 * <p>
 * author: zhao.song
 * date: created in 10:45  2022/9/20
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class IncompleteElementException extends BuilderException {
    private static final long serialVersionUID = -3697292286890900315L;

    public IncompleteElementException() {
        super();
    }

    public IncompleteElementException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncompleteElementException(String message) {
        super(message);
    }

    public IncompleteElementException(Throwable cause) {
        super(cause);
    }
}
