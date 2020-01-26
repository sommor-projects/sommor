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

/**
 * @author yanguanwei@qq.com
 * @since 2019/2/13
 */
@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({Array.class})
public class ArrayTypeHandler extends BaseTypeHandler<Array> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Array parameter, JdbcType jdbcType)
        throws SQLException {
        String s = getValueString(parameter);
        ps.setString(i, s);
    }

    private String getValueString(Array parameter) {
        return parameter.toString();
    }

    @Override
    public Array getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String point = rs.getString(columnName);
        return parseResult(point);
    }

    private Array parseResult(String value) {
        if (null != value) {
            ArrayList list = new ArrayList();
            String[] a = value.split(",");
            for (String s : a) {
                if (s.startsWith("'") && s.endsWith("'")) {
                    list.add(s.substring(1, s.length()-1));
                } else {
                    list.add(Integer.valueOf(s));
                }
            }

            return new Array(list);
        }

        return null;
    }

    @Override
    public Array getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseResult(rs.getString(columnIndex));
    }

    @Override
    public Array getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseResult(cs.getString(columnIndex));
    }
}
