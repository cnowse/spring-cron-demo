package cn.cnowse.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JavaMailSender 增强
 *
 * @author Jeong Geol 2023-12-18
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JavaMailSenderHelper {

    private final JavaMailSender javaMailSender;

    /** 通过 application.properties 的方式配置 JavaMailSender 可以通过这样的方式取 from 账户 */
    /* @Value("${spring.mail.username}")
    private String from; */

    public void sendMail(String email, String msg) {
        String from = "cnowse@163.com";
        try {
            // 创建一个邮件消息
            MimeMessage message = javaMailSender.createMimeMessage();
            // 创建 MimeMessageHelper，指定 boolean multipart 参数为 true
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            // 发件人邮箱和名称
            helper.setFrom(from, "cnowse服务");
            // 收件人邮箱
            helper.setTo(email);
            // 邮件标题
            helper.setSubject("cnowse验证码");
            // 邮件正文，第二个参数表示是否是HTML正文
            helper.setText(msg, false);
            // 发送
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("邮件发送失败 [From={}, To={}, msg={}]", from, email, msg);
        }
    }

}
