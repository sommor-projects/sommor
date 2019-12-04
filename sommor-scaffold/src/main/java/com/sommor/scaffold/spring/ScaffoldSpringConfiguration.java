package com.sommor.scaffold.spring;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/26
 */
@Configuration
public class ScaffoldSpringConfiguration {

    @Bean
    public LocaleResolver localeResolver() {
        I18nLocaleResolver localeResolver = new I18nLocaleResolver();
        localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        localeResolver.setSupportedLocales(Lists.newArrayList(Locale.SIMPLIFIED_CHINESE, Locale.ENGLISH));

        return localeResolver;
    }

    @Bean
    public ResourceBundleMessageSource errorMessageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setBasenames("i18n/errors");
        return resourceBundleMessageSource;
    }
}
