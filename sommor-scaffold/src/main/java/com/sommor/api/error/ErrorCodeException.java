package com.sommor.api.error;

/**
 * @author wuyu
 * @since 2019/1/21
 */
public class ErrorCodeException extends RuntimeException {

    private ErrorCode errorCode;

    public ErrorCodeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCodeException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
