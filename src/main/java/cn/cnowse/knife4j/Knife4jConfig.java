package cn.cnowse.knife4j;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

/**
 * Spring Boot 3 下配置 knife4j 文档
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
                .description("cnowse spring example")
                .termsOfService("https://www.cnowse.cn/"));
    }

    @Bean
    public GroupedOpenApi defaultApi() {
        return GroupedOpenApi.builder().group("web端接口").packagesToScan("cn.cnowse.web").build();
    }

    @Bean
    public GroupedOpenApi systemApi() {
        return GroupedOpenApi.builder().group("外部API接口").packagesToScan("cn.cnowse.jpa").build();
    }

}
