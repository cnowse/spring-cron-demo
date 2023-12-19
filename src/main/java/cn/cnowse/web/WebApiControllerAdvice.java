package cn.cnowse.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * 统一异常处理
 *
 * @author Jeong Geol 2023-12-18
 */
@Slf4j
@RestControllerAdvice
public class WebApiControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK) // 如果需要修改 HTTP 状态码，可以使用这种方式
    public ApiResult exceptionHandler(Exception ex, HttpServletRequest request) {
        log.error("Error when handle [uri={} {}]", request.getMethod(), request.getRequestURI(), ex);
        return ApiResult.err(ex);
    }

}
