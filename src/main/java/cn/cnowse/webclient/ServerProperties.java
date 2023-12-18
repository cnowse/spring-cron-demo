package cn.cnowse.webclient;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * WebClient 配置类
 * 
 * @author Jeong Geol 2023-12-18
 */
@Data
@ConfigurationProperties(prefix = "server")
public class ServerProperties {

    /** 业务端 URL 不能以 / 结尾 */
    private String url = "http://127.0.0.1:8090";

    /** 连接超时时间 单位：毫秒 */
    private int connectTimeout = 5000;

    /** 接收从服务器返回的数据超时时间 单位：毫秒 */
    private int responseTimeout = 5000;

    /** 读取超时时间 单位：毫秒 */
    private int readTimeout = 5000;

    /** 写入超时时间 单位：毫秒 */
    private int writeTimeout = 5000;

}
