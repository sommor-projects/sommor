package com.sommor.taxonomy.utils;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class SlugParser {

    public static String parse(String name) {
        StringBuilder sb = new StringBuilder(name.length()+4);

        for (int i=0; i<name.length(); i++) {
            char c = name.charAt(i);
            if (isUpperChar(c)) {
                if (i > 0 && sb.charAt(sb.length()-1) != '-') {
                    sb.append("-");
                }
                sb.append((char)(c+32));
            } else if (' ' == c && sb.charAt(sb.length()-1) != '-') {
                sb.append("-");
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private static boolean hasUpperChar(String fieldName) {
        for (int i=0; i<fieldName.length(); i++) {
            char c = fieldName.charAt(i);
            if (isUpperChar(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isUpperChar(char c) {
        return 'A' <= c && c <= 'Z';
    }
}
