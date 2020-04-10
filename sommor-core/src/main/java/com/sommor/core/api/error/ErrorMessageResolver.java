package com.sommor.core.api.error;

import com.sommor.extensibility.config.Extension;

import java.util.Locale;

/**
 * @author wuyu
 * @since 2018/8/10
 */
@Extension(name = "错误码文案解析器")
public interface ErrorMessageResolver {

    String resolve(String errorCode, Locale locale, Object... params);

}
