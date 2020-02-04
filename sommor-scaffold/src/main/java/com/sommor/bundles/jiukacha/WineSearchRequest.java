package com.sommor.bundles.jiukacha;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/3
 */
@Getter
@Setter
public class WineSearchRequest {

    private String wineTitle;

    private String year;

    public static WineSearchRequest of(String keywords) {
        String[] a = keywords.split(" ");

        int end = a.length-1;
        String year = null;
        String wineTitle = null;

        if (isYear(a[0])) {
            year = a[0];
            wineTitle = concat(a, 1, end);
        } else if (isYear(a[end])) {
            year = a[end];
            wineTitle = concat(a, 0, end-1);
        } else {
            wineTitle = keywords;
        }

        WineSearchRequest request = new WineSearchRequest();
        request.setYear(year);
        request.setWineTitle(wineTitle);

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
