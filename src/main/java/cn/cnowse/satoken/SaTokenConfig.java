package cn.cnowse.satoken;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;

/**
 * SaToken 配置，开启注解鉴权 <br/>
 * 基于拦截器实现，实现了 {@link WebMvcConfigurer}，可以直接写到该实现类中，添加一个拦截器即可，无需单独创建一个类
 *
 * @author Jeong Geol 2023-12-25
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    /** 不被 SaToken 拦截的路径，可以做成配置项，也可以直接在这样在项目中写死 */
    private final List<String> ignoreUrls = Arrays.asList("/favicon.ico", "/*.html", "/**/*.html", "/**/*.css",
            "/**/*.js", "/doc.html", "/*/api-docs", "/*/api-docs/**");

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> StpUtil.checkLogin())).addPathPatterns("/**")
                .excludePathPatterns(ignoreUrls);
    }

}
