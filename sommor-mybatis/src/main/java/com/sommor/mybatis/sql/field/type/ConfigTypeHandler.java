package com.sommor.mybatis.sql.field.type;

import org.apache.commons.lang3.StringUtils;
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
@MappedTypes({Config.class})
public class ConfigTypeHandler extends BaseTypeHandler<Config> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Config parameter, JdbcType jdbcType)
        throws SQLException {
        String s = parameter.toPersistence();
        ps.setString(i, s);
    }

    @Override
    public Config getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String point = rs.getString(columnName);
        return parseResult(point);
    }

    private Config parseResult(String value) {
        if (StringUtils.isNoneBlank(value)) {
            return Config.from(value);
        }
        return new Config();
    }

    @Override
    public Config getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseResult(rs.getString(columnIndex));
    }

    @Override
    public Config getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseResult(cs.getString(columnIndex));
    }
}
