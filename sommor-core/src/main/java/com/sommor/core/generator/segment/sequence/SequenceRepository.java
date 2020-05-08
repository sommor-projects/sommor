package com.sommor.core.generator.segment.sequence;

import com.google.common.collect.Lists;
import com.sommor.core.utils.DateTimeUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/10
 */
@Slf4j
public class SequenceRepository {

    private static final String INSERT_UPDATE_SQL_TEMPLATE = "INSERT INTO sequence_{dbIndex} (name, prefix, value, create_time, update_time) VALUES (?,?,?,?,?)"
            + " ON DUPLICATE KEY UPDATE value = value + ?, update_time = ?";

    private static final String SELECT_SQL_TEMPLATE = "SELECT value FROM sequence_{dbIndex} WHERE name = ? and prefix = ?";

    private static final String SELECT_LATEST_SQL_TEMPLATE = "SELECT value, prefix FROM sequence_{dbIndex} WHERE name = ? order by prefix DESC LIMIT 1";

    private DataSource dataSource;

    private int dbIndex;

    private String insertUpdateSql;

    private String selectSql;

    private String selectLatestSql;

    public SequenceRepository(DataSource dataSource, int dbIndex) {
        this.dataSource = dataSource;
        this.dbIndex = dbIndex;

        String dbIndexStr = String.valueOf(dbIndex);
        this.insertUpdateSql = INSERT_UPDATE_SQL_TEMPLATE.replace("{dbIndex}", dbIndexStr);
        this.selectSql = SELECT_SQL_TEMPLATE.replace("{dbIndex}", dbIndexStr);
        this.selectLatestSql = SELECT_LATEST_SQL_TEMPLATE.replace("{dbIndex}", dbIndexStr);
    }

    public SequenceEntity querySequence(String name) {
        Connection conn = null;
        PreparedStatement selectPstmt = null;
        ResultSet queryResult = null;

        try {
            conn = dataSource.getConnection();
            List<Object> queryParams = Lists.newArrayList(name);
            selectPstmt = assemblePreparedStatement(conn, selectLatestSql, queryParams);
            queryResult = selectPstmt.executeQuery();

            if (queryResult.next()) {
                long value = queryResult.getLong("value");
                long prefix = queryResult.getLong("prefix");
                return new SequenceEntity(name, prefix, value);
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException("execute updateOffset fail, sequenceName is " + name, e);
        } finally {
            closeResultSet(queryResult);
            closePreparedStatement(selectPstmt);
            closeConnection(conn);
        }
    }

    public SequenceCache updateSequence(SequenceId sequenceId, long prefix) {
        Connection conn = null;
        PreparedStatement updatePstmt = null;
        PreparedStatement selectPstmt = null;
        ResultSet queryResult = null;

        String name = sequenceId.getName();
        long startValue = sequenceId.getStartValue();
        long stepValue = sequenceId.getStepValue();

        SequenceCache sequenceCache;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            long now = DateTimeUtil.now();
            int dbIndex = this.dbIndex;

            long initValue = startValue + stepValue;
            List<Object> updateParams = Lists.newArrayList(name, prefix, initValue, now, now, stepValue, now);
            updatePstmt = assemblePreparedStatement(conn, insertUpdateSql, updateParams);
            updatePstmt.execute();

            List<Object> queryParams = Lists.newArrayList(name, prefix);
            selectPstmt = assemblePreparedStatement(conn, selectSql, queryParams);
            queryResult = selectPstmt.executeQuery();
            long maxValue = 0;
            if (queryResult.next()) {
                maxValue = queryResult.getLong("value");
            }
            long minValue = maxValue - stepValue;
            sequenceCache = new SequenceCache(name, dbIndex, prefix, minValue, maxValue);
            conn.commit();
        } catch (SQLException e) {
            if (null != conn) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    log.error("rollback fail, sequenceName is " + name, ex);
                }
            }

            throw new RuntimeException("execute updateOffset fail, sequenceName is " + name, e);
        } finally {
            closeResultSet(queryResult);
            closePreparedStatement(updatePstmt);
            closePreparedStatement(selectPstmt);
            closeConnection(conn);
        }

        return sequenceCache;
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("close Connection fail.", e);
            }
        }
    }

    private void closeResultSet(ResultSet queryResult) {
        if (queryResult != null) {
            try {
                queryResult.close();
            } catch (SQLException e) {
                log.error("close ResultSet fail.", e);
            }
        }
    }

    private void closePreparedStatement(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                log.error("close PreparedStatement fail.", e);
            }
        }
    }

    private PreparedStatement assemblePreparedStatement(Connection connection, String sql, List<Object> params) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(sql);
        if (CollectionUtils.isNotEmpty(params)) {
            for (int index = 0; index < params.size(); index++) {
                pstmt.setObject((index + 1), params.get(index));
            }
        }
        return pstmt;
    }
}
