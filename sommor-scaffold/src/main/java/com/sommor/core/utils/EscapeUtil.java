package com.sommor.core.utils;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/4
 */
public class EscapeUtil {

    public static String escapeHtml(String s) {
        s = s.trim();

        if (s.contains("<") && s.contains(">")) {
            int n = s.length();
            StringBuilder builder = new StringBuilder(n);
            boolean filter = false;
            for (int i=0; i<n; i++) {
                char c = s.charAt(i);
                if (c == '<') {
                    filter = true;
                } else if (c == '>') {
                    filter = false;
                } else {
                    if (! filter) {
                        builder.append(c);
                    }
                }
            }

            return builder.toString();
        }

        return s;
    }

}
