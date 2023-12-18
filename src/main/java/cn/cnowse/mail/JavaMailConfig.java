package cn.cnowse.mail;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * JavaMailSender 配置类。这个与 application-mail.properties 二选一
 * 
 * @author Jeong Geol 2023-12-18
 */
@Configuration
public class JavaMailConfig {

    @Bean
    public JavaMailSender configJavaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.qq.com");
        javaMailSender.setPort(465);
        javaMailSender.setUsername("cnowse@163.com");
        javaMailSender.setPassword("XUCDOOUUXTTGOJYV");
        javaMailSender.setDefaultEncoding("utf-8");
        javaMailSender.setProtocol("smtps");

        Properties properties = new Properties();
        properties.put("mail.smtp.connectiontimeout", 5000);
        properties.put("mail.smtp.timeout", 3000);
        properties.put("mail.smtp.writetimeout", "5000");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.starttls.required", true);
        properties.put("mail.debug", true);
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }

}
