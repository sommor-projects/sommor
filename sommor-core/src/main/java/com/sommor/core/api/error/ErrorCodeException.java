package com.sommor.core.api.error;

/**
 * @author wuyu
 * @since 2019/1/21
 */
public class ErrorCodeException extends RuntimeException {

    private ErrorCode errorCode;

    public ErrorCodeException(ErrorCode errorCode) {
        super(errorCode.toString());
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
