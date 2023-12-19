package cn.cnowse.jpa;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 查询条件
 *
 * @author Jeong Geol 2023-12-19
 */
@Retention(RUNTIME)
@Target(FIELD)
@Inherited
@Documented
public @interface Where {

    String property() default "";

    Logic logic() default Logic.EQ;

    ValueHandler valueHandler() default ValueHandler.NONE;

    boolean ignoreNullValue() default true;

    enum Logic {
        EQ,
        LT,
        GT,
        LTE,
        GTE,
        IN,
        BETWEEN,
        LIKE,
        STARTS_WITH,
        NOT_EQ,
        NOT_IN,
        IS_NULL,
        IS_NOT_NULL
    }

}
