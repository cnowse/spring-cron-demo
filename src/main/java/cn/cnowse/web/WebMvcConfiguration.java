package cn.cnowse.web;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

/**
 * WebMvc 配置
 *
 * @author Jeong Geol 2023-12-19
 */
@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final MappingJackson2HttpMessageConverter jsonCvt;

    /**
     * 允许在Web应用程序中提供静态资源，访问 xxx.xxx.xxx/doc.html 时会访问到
     * classpath:/META-INF/resources/ 下的 doc.html
     *
     * @author Jeong Geol
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 跨域配置，允许跨域访问
     *
     * @author Jeong Geol
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowCredentials(true).allowedHeaders("*")
                .allowedOriginPatterns("*").allowedMethods("*").maxAge(3600);
    }

    /**
     * 替换 json 消息转换器，该转换器在 CommonConfig 中进行了重新配置
     *
     * @author Jeong Geol
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(MappingJackson2HttpMessageConverter.class::isInstance);
        // 替换自动配置的 MappingJackson2HttpMessageConverter
        converters.add(jsonCvt);
    }

}
