package cn.cnowse.captcha;

import lombok.Data;

/**
 * @author Jeong Geol 2023-12-26
 */
@Data
public class CaptchaDTO {

    private String captchaCodeKey;
    private String captchaCodeImg;

}
