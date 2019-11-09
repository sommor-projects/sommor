package com.sommor.mybatis.sql.select;


/**
 * @author wuyu
 * @since 2019/2/11
 */
public enum OrderType {
    /**
     * 升序
     */
    ASC,
    /**
     * 降序
     */
    DESC
    ;

    public static OrderType of(String type) {
        if (null == type) {
            return null;
        }

        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
