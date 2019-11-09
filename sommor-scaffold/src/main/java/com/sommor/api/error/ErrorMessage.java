package com.sommor.api.error;


import java.util.Locale;

/**
 * @author wuyu
 * @since 2018/7/25
 */
public class ErrorMessage {

    private static Locale displayedLocale = Locale.SIMPLIFIED_CHINESE;

    private String code;

    private String message;

    public ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorMessage of(String code, Object... params) {
        return ErrorMessageSource.getErrorMessage(code, displayedLocale, params);
    }

    public static ErrorMessage of(String code, Locale locale, Object... params) {
        return ErrorMessageSource.getErrorMessage(code, locale, params);
    }

    public static ErrorMessage get(String code, Object... params) {
        return ErrorMessageSource.getErrorMessage(code, displayedLocale, params);
    }

    public static ErrorMessage get(String code, Locale locale, Object... params) {
        return ErrorMessageSource.getErrorMessage(code, locale, params);
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
