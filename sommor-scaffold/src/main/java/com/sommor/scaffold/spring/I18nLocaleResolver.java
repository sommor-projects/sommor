package com.sommor.scaffold.spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author wuyu
 * @since 2019/1/20
 */
public class I18nLocaleResolver extends AcceptHeaderLocaleResolver {

    private String langKey = "_lang";

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String lang = request.getHeader(this.langKey);
        if (StringUtils.isBlank(lang)) {
            lang =request.getParameter(this.langKey);
        }

        Locale locale = null;

        if (StringUtils.isNotBlank(lang)) {
            locale = Locale.forLanguageTag(lang);
        }

        if (null == locale) {
            locale = super.resolveLocale(request);
        }

        return locale;
    }
}
