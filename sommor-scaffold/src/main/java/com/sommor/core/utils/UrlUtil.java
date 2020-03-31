package com.sommor.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/4
 */
public class UrlUtil {

    public static Map<String, String> parseQueryString(String url) {
        Map<String, String> map = new HashMap<>();

        if (StringUtils.isNotBlank(url)) {
            int i = url.indexOf('?');
            if (i > 0) {
                String qs = url.substring(i+1);
                String[] a = qs.split("&");
                for (String s : a) {
                    String[] aa = s.split("=");
                    map.put(aa[0], aa[1]);
                }
            }
        }

        return map;
    }
}
