package com.sommor.wechat.sdk;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuyu
 * @since 2019/2/5
 */
public class Parameters {

    private Map<String, String> parameters = new HashMap<>(16);

    public Parameters add(String key, String value) {
        this.parameters.put(key, value);
        return this;
    }

    public Parameters add(String key, Object value) {
        this.parameters.put(key, String.valueOf(value));
        return this;
    }

    public String toQueryString() {
        try {
            StringBuilder builder = new StringBuilder(128);
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                if (builder.length() > 0) {
                    builder.append("&");
                }

                builder.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }

            return builder.toString();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return toQueryString();
    }
}
