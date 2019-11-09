package com.sommor.mybatis.sql.field.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yanguanwei@qq.com
 * @since 2019/2/13
 */
@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({Location.class})
public class LocationTypeHandler extends BaseTypeHandler<Location> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Location parameter, JdbcType jdbcType)
        throws SQLException {
        String s = "POINT("+parameter.getLongitude() + "," + parameter.getLatitude() + ")";
        ps.setString(i, s);
    }

    @Override
    public Location getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String point = rs.getString(columnName);
        return parseResult(point);
    }

    private Location parseResult(String value) {
        if (null != value) {
            String[] s = value.substring(value.indexOf("(")+1,value.length()-1).split(" ");
            return new Location(Double.parseDouble(s[0]), Double.parseDouble(s[1]));
        }

        return null;
    }

    @Override
    public Location getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseResult(rs.getString(columnIndex));
    }

    @Override
    public Location getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseResult(cs.getString(columnIndex));
    }
}
