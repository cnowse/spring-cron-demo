package cn.cnowse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import cn.cnowse.webclient.ServerProperties;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(value = ServerProperties.class)
public class SpringConDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringConDemoApplication.class, args);
    }

}
