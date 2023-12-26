package cn.cnowse.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * 自定义注解校验，枚举校验注解
 *
 * @author Jeong Geol 2023-12-26
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
    ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValueValidator.class})
public @interface EnumValue {

    /** 默认错误消息 */
    String message() default "必须为指定值";

    String[] strValues() default {};

    int[] intValues() default {};

    /** 分组 */
    Class<?>[] groups() default {};

    /** 负载 */
    Class<? extends Payload>[] payload() default {};

    /** 指定多个时使用 */
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {

        EnumValue[] value();

    }

}