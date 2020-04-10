package com.sommor.bundles.wine.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/6
 */
@Getter
@Setter
public class WineSearchRequest {

    /**
     * 中文标题
     */
    private String wineTitle;
    /**
     * 英文名称
     */
    private String wineName;

    private String year;

    public static WineSearchRequest of(String keywords) {
        String[] a = keywords.split(" ");

        int end = a.length-1;
        String year = null;
        String wineName = null;

        if (isYear(a[0])) {
            year = a[0];
            wineName = concat(a, 1, end);
        } else if (isYear(a[end])) {
            year = a[end];
            wineName = concat(a, 0, end-1);
        } else {
            wineName = keywords;
        }

        WineSearchRequest request = new WineSearchRequest();
        request.setYear(year);
        request.setWineName(wineName);

        return request;
    }

    private static boolean isYear(String s) {
        if (s.length() == 4 && StringUtils.isNumeric(s)
                && (s.startsWith("19") || s.startsWith("20"))) {
            return true;
        }

        return false;
    }

    private static String concat(String[] a, int i, int j) {
        StringBuilder builder = new StringBuilder();

        for (int k=i; k<=j; k++) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(a[k].trim());
        }

        return builder.toString();
    }
}
