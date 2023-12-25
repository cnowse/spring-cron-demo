package cn.cnowse.satoken;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;

/**
 * SaToken 配置，开启注解鉴权
 *
 * @author Jeong Geol 2023-12-25
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> StpUtil.checkLogin())).addPathPatterns("/**")
                .excludePathPatterns("/user/login");
    }

    /**
     * 获取权限角色
     */
    @Bean
    public StpInterface stpInterface() {
        return new SaPermissionImpl();
    }

    /**
     * 自定义 dao 层存储
     */
    @Bean
    public SaTokenDao saTokenDao() {
        return new SaTokenDaoImpl();
    }

}
