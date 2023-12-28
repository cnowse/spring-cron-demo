package cn.cnowse.validation;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 手机号校验处理类
 * 
 * @author Jeong Geol 2023-12-28
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

    private final Pattern regx =
            Pattern.compile("^(?:(?:\\+|00)86)?1(?:3\\d|4[0,14-9]|5[0-3,5-9]|6[2,5-7]|7[0-8]|8\\d|9[0-3,5-9])\\d{8}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果手机号为空，默认不校验，即校验通过
        if (!StringUtils.hasText(value)) {
            return true;
        }
        // 校验手机
        return regx.matcher(value).matches();
    }

}
