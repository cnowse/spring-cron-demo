package cn.cnowse.web;

import java.util.List;

import cn.cnowse.interceptor.AuthInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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

    private final ObjectMapper om;
    private final MappingJackson2HttpMessageConverter jsonCvt;

    @Value("${spring-con-demo.excludePath}") // 这里可以直接将 Spring 拦截器要排除的路径，通过配置文件配置
    private List<String> excludePath;

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

    /**
     * 添加 Spring 拦截器，只有这里配置了，拦截器才会生效
     *
     * @author Jeong Geol
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(om)).excludePathPatterns(excludePath);
    }

}
