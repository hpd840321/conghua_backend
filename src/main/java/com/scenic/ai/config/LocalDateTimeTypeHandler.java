package com.scenic.ai.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * LocalDateTime 类型处理器
 * 用于处理 Java 8 LocalDateTime 和数据库 TIMESTAMP 之间的转换
 */
@MappedTypes(LocalDateTime.class)
public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) 
        throws SQLException {
        if (parameter != null) {
            ps.setTimestamp(i, Timestamp.valueOf(parameter));
        } else {
            ps.setNull(i, Types.TIMESTAMP);
        }
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnIndex);
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp timestamp = cs.getTimestamp(columnIndex);
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
} 