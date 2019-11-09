package com.sommor.api.error;

import java.util.Locale;

/**
 * @author wuyu
 * @since 2019/1/20
 */
public class ErrorCode {

    private String code;

    private Object[] params;

    public static ErrorCode of(String code, Object...params) {
        ErrorCode errorCode = new ErrorCode();
        errorCode.code = code;
        errorCode.params = params;

        return errorCode;
    }

    public ErrorMessage toErrorMessage(Locale locale) {
        return ErrorMessage.of(code, locale, params);
    }
}
