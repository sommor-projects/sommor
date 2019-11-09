package com.sommor.mybatis.entity.naming;

import com.sommor.extensibility.Priorities;
import com.sommor.extensibility.config.Implement;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@Implement(priority = Priorities.LOWEST)
public class DefaultFieldName2ColumnNameParser implements NamingParseStrategy {

    @Override
    public String parseFieldName2ColumnName(String fieldName) {
        if (hasUpperChar(fieldName)) {
            StringBuilder sb = new StringBuilder(fieldName.length()+4);

            for (int i=0; i<fieldName.length(); i++) {
                char c = fieldName.charAt(i);
                if (isUpperChar(c)) {
                    if (i > 0) {
                        sb.append("_");
                    }
                    sb.append((char)(c+32));
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        }

        return fieldName;
    }

    private boolean hasUpperChar(String fieldName) {
        for (int i=0; i<fieldName.length(); i++) {
            char c = fieldName.charAt(i);
            if (isUpperChar(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean isUpperChar(char c) {
        return 'A' <= c && c <= 'Z';
    }
}
