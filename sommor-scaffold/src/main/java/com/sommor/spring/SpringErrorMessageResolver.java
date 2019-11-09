package com.sommor.spring;

import com.sommor.api.error.ErrorMessageResolver;
import com.sommor.extensibility.config.Implement;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/26
 */
@Implement
public class SpringErrorMessageResolver implements ErrorMessageResolver {

    @Resource
    private ResourceBundleMessageSource errorMessageSource;

    private static final String ERROR_MESSAGE_PREFIX = "error.";

    private static final String ERROR_UNKNOWN = ERROR_MESSAGE_PREFIX + "unknown";

    @Override
    public String resolve(String errorCode, Locale locale, Object... params) {
        errorCode = ERROR_MESSAGE_PREFIX + errorCode;
        try {
            return errorMessageSource.getMessage(errorCode, params, locale);
        } catch (NoSuchMessageException e) {
            if (! ERROR_UNKNOWN.equals(errorCode)) {
                return errorMessageSource.getMessage(ERROR_UNKNOWN, params, locale);
            }
            throw e;
        }
    }
}
