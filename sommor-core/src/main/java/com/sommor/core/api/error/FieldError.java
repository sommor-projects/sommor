package com.sommor.core.api.error;

/**
 * @author yanguanwei@qq.com
 * @since 2019/1/27
 */
public class FieldError {

    private String fieldName;

    private ErrorCode errorCode;

    public FieldError(String fieldName, ErrorCode errorCode) {
        this.fieldName = fieldName;
        this.errorCode = errorCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
