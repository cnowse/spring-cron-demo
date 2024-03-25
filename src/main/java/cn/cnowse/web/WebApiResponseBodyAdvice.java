package cn.cnowse.web;

import java.lang.reflect.Type;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * 统一处理响应结果
 * 
 * @author Jeong Geol 2023-12-18
 */
@Slf4j
@RestControllerAdvice
public class WebApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter rt, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        boolean hasRestControllerAnnotation = rt.getDeclaringClass().getAnnotation(RestController.class) != null;
        boolean hasResponseBodyAnnotation = rt.getMethodAnnotation(ResponseBody.class) != null;
        boolean isJsonConverter = MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
        boolean isResponseEntity = ResponseEntity.class.isAssignableFrom(rt.getParameterType());
        if (log.isDebugEnabled()) {
            log.info(
                    "hasRestControllerAnnotation={}, hasResponseBodyAnnotation={}, isResponseEntity={}, isJsonConverter={}",
                    hasRestControllerAnnotation, hasResponseBodyAnnotation, isResponseEntity, isJsonConverter);
        }
        return (hasRestControllerAnnotation || hasResponseBodyAnnotation || isResponseEntity) && isJsonConverter;
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter rt, @NonNull MediaType mt,
            @NonNull Class<? extends HttpMessageConverter<?>> ht, @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response) {

        if (body instanceof BaseResult) {
            return body;
        }
        Type gpt = rt.getGenericParameterType();
        if (String.class.equals(gpt)) {
            return body;
        }
        if (Void.TYPE.equals(gpt)) {
            return BaseResult.ok();
        }
        return BaseResult.ok(body);
    }

}
