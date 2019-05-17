package com.sydml.mybaits.Interceptor.mybatis;

import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.Properties;

/**
 * 统计sql时间
 *
 * @author Liuym
 * @date 2019/3/23 0023
 */
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "update", args = {Statement.class}), @Signature(type
        = StatementHandler.class, method = "batch", args = {Statement.class}), @Signature(type = StatementHandler
        .class, method = "query", args = {Statement.class, ResultHandler.class})})

public class MyBatisInterceptor implements Interceptor {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyBatisInterceptor.class);

    private Integer maxRowCount = Integer.MAX_VALUE;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Thread t = Thread.currentThread();
        Field f = ReflectionUtils.findField(RoutingStatementHandler.class, "delegate");
        f.setAccessible(true);
        BaseStatementHandler bsh = (BaseStatementHandler) ReflectionUtils.getField(f, invocation.getTarget());
        f = ReflectionUtils.findField(BaseStatementHandler.class, "mappedStatement");
        f.setAccessible(true);
        MappedStatement ms = (MappedStatement) ReflectionUtils.getField(f, bsh);
        LOGGER.info(t.getName() + "    " + ms.getId() + ",the sql is:" + bsh.getBoundSql().getSql().replace('\n', ' '));
        long begin = System.currentTimeMillis();
        Object result = invocation.proceed();
        long end = System.currentTimeMillis();
        LOGGER.info(t.getName() + "    " + ms.getId() + ",the sql execute time is " + ((end - begin) / 1000.0) + " s.");
        return result;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof RoutingStatementHandler) {
            return Plugin.wrap(target, this);
        } else if (target instanceof ResultSetHandler) {
            return new DefaultResultSetHandlerDelegate((DefaultResultSetHandler) target, maxRowCount);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public Integer getMaxRowCount() {
        return maxRowCount;
    }

    public void setMaxRowCount(Integer maxRowCount) {
        this.maxRowCount = maxRowCount;
    }
}
