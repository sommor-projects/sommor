package com.sommor.core.api.error;

import java.util.Map;

/**
 * @author wuyu
 * @since 2018/8/10
 */
public class MessageProperties {
    private Map<String, String> properties;

    public MessageProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getMessage(String code) {
        return this.properties.get(code);
    }
}
