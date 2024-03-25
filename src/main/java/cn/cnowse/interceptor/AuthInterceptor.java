package cn.cnowse.interceptor;

import static cn.cnowse.web.ResultCodeEnum.AUTH_ERROR;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.cnowse.web.BaseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器后，需要在实现 {@link WebMvcConfigurer} 的配置类中，进行拦截器配置。
 * 
 * @author Jeong Geol
 */
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    /** Token key */
    public static final String TOKEN_KEY = "X-Auth-Token";

    /** Token 到期时间 key，值为 LocalDateTime */
    public static final String TOKEN_EXPIRED_KEY = "X-Auth-Token-Expired";

    /** Token 有效时间，默认 12 小时 */
    public static final int TOKEN_VALIDITY_PERIOD = 12;

    private final ObjectMapper om;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull Object handler) throws Exception {

        String tokenInSession = (String)request.getSession().getAttribute(TOKEN_KEY);
        LocalDateTime expired = (LocalDateTime)request.getSession().getAttribute(TOKEN_EXPIRED_KEY);
        String tokenInHeader = request.getHeader(TOKEN_KEY);
        if (tokenInHeader == null || !tokenInHeader.equals(tokenInSession) || LocalDateTime.now().isAfter(expired)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
            response.getWriter().write(om.writeValueAsString(BaseResult.err(AUTH_ERROR)));
            return false;
        }
        // 刷新 Token 有效期
        request.getSession().setAttribute(TOKEN_EXPIRED_KEY, LocalDateTime.now().plusHours(TOKEN_VALIDITY_PERIOD));
        return true;
    }

}
