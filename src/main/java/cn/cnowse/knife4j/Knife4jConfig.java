package cn.cnowse.knife4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

/**
 * Spring Boot 3 下配置 knife4j 文档，搭配 application-doc.properties 使用
 *
 * @author Jeong Geol 2023-12-18
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("cnowse spring").version("1.0")
                .contact(new Contact().name("Jeong Geol"))
                .description("充电管理平台接口文档")
                .termsOfService("https://www.cnowse.cn/"));
    }

}
