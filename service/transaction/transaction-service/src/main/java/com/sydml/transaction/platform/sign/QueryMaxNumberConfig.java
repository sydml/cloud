package com.sydml.transaction.platform.sign;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryMaxNumberConfig {
    /**
     * 是否忽略系统默认最大查询条数限制
     *
     * @return 否忽略系统默认最大查询条数限制
     */
    boolean ignore() default false;

    /**
     * 设置最大查询条数
     *
     * @return 大查询条数
     */
    long maxQueryNumber() default 20000L;
}
