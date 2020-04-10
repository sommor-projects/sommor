package com.sommor.core.api.error;

/**
 * @author yanguanwei@qq.com
 * @since 2019/1/22
 */
public class ErrorDetail {
    private String field;

    private String errorCode;

    private String errorMsg;

    public ErrorDetail(String field, ErrorMessage errorMessage) {
        this(field, errorMessage.getCode(), errorMessage.getMessage());
    }

    public ErrorDetail(String field, String errorCode, String errorMsg) {
        this.field = field;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
