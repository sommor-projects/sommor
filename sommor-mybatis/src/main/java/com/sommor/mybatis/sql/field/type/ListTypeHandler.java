package com.sommor.mybatis.sql.field.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/2/13
 */
@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({List.class})
public class ListTypeHandler extends BaseTypeHandler<List<?>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<?> parameter, JdbcType jdbcType)
        throws SQLException {
        String s = getValueString(parameter);
        ps.setString(i, s);
    }

    private String getValueString(List<?> parameter) {
        StringBuilder builder = new StringBuilder();

        for (Object value : parameter) {
            if (builder.length() > 0) {
                builder.append(",");
            }

            if (value instanceof Number) {
                builder.append(value);
            } else {
                builder.append("'").append(value).append("'");
            }
        }

        return builder.toString();
    }

    @Override
    public List<?> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String point = rs.getString(columnName);
        return parseResult(point);
    }

    private List<?> parseResult(String value) {
        if (null != value) {
            List list = new ArrayList<>();
            String[] a = value.split(",");
            for (String s : a) {
                if (s.startsWith("'") && s.endsWith("'")) {
                    list.add(s.substring(1, s.length()-1));
                } else {
                    list.add(Integer.valueOf(s));
                }
            }

            return list;
        }

        return null;
    }

    @Override
    public List<?> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseResult(rs.getString(columnIndex));
    }

    @Override
    public List<?> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseResult(cs.getString(columnIndex));
    }
}
