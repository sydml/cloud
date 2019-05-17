package com.sydml.mybaits.Interceptor.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author Liuym
 * @date 2019/3/23 0023
 */
public class DefaultResultSetHandlerDelegate extends DefaultResultSetHandler {

    public DefaultResultSetHandlerDelegate(DefaultResultSetHandler handler, Integer maxRowCount) {
        super((Executor) getField(handler, "executor"), (MappedStatement) getField(handler, "mappedStatement"),
                (ParameterHandler) getField(handler, "parameterHandler"), (ResultHandler) getField(handler,
                        "resultHandler"), (BoundSql) getField(handler, "boundSql"), getRowBounds(handler,
                        "rowBounds", maxRowCount));
    }

    private static RowBounds getRowBounds(DefaultResultSetHandler handler, String field, Integer maxRowCount) {
        RowBounds rowBounds = (RowBounds) getField(handler, field);
        if (rowBounds != null) {
            Field limitField = ReflectionUtils.findField(rowBounds.getClass(), "limit");
            ReflectionUtils.makeAccessible(limitField);
            ReflectionUtils.setField(limitField, rowBounds, maxRowCount);
        }
        return rowBounds;
    }

    private static Object getField(DefaultResultSetHandler handler, String field) {
        Field f = ReflectionUtils.findField(DefaultResultSetHandler.class, field);
        f.setAccessible(true);
        Object value = ReflectionUtils.getField(f, handler);
        f.setAccessible(false);
        return value;
    }
}
