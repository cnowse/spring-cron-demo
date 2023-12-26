package cn.cnowse.captcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;

import com.google.code.kaptcha.Producer;

import cn.cnowse.redis.RedisHelper;

import jakarta.annotation.Resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 如何使用 Captcha
 * 
 * @author Jeong Geol 2023-12-26
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UseCaptcha {

    private static final String MATH_TYPE = "math";
    private static final String CHAR_TYPE = "char";
    private static final String JPG = "jpg";

    private final RedisHelper redis;

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    public CaptchaDTO example(String captchaType) {
        String expression;
        String answer = null;
        BufferedImage image = null;
        if (MATH_TYPE.equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            String[] expressionAndAnswer = capText.split("@");
            expression = expressionAndAnswer[0];
            answer = expressionAndAnswer[1];
            image = captchaProducerMath.createImage(expression);
        }
        if (CHAR_TYPE.equals(captchaType)) {
            expression = answer = captchaProducer.createText();
            image = captchaProducer.createImage(expression);
        }
        if (image == null) {
            throw new RuntimeException("验证码生成失败");
        }
        String imgKey = UUID.randomUUID().toString();
        CaptchaDTO captcha = getCaptcha(image, imgKey);
        // redis.valueSet(imgKey, answer);
        return captcha;
    }

    private CaptchaDTO getCaptcha(Image image, String imgKey) {
        image = new ImageIcon(image).getImage();
        BufferedImage biMage =
                new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = biMage.createGraphics();
        graphics.setColor(null);
        graphics.fillRect(0, 0, biMage.getWidth(), biMage.getHeight());
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
            ImageIO.write(biMage, JPG, os);
            CaptchaDTO captchaDTO = new CaptchaDTO();
            captchaDTO.setCaptchaCodeKey(imgKey);
            captchaDTO.setCaptchaCodeImg(Base64.getEncoder().encodeToString(os.toByteArray()));
            return captchaDTO;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
