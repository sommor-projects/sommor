package com.sommor.core.api.error;

import com.sommor.extensibility.ExtensionExecutor;

import java.util.Locale;

/**
 * @author wuyu
 * @since 2018/7/25
 */
public class ErrorMessageSource {
    private static final ErrorMessageResolver ERROR_MESSAGE_RESOLVER = ExtensionExecutor.proxyOf(ErrorMessageResolver.class);

    public static ErrorMessage getErrorMessage(String code, Locale locale, Object... params) {
        String message = ERROR_MESSAGE_RESOLVER.resolve(code, locale, params);
        return new ErrorMessage(code, message);
    }
}
