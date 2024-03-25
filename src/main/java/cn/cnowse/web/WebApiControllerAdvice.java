package cn.cnowse.web;

import static cn.cnowse.web.ResultCodeEnum.INTERNAL_ERROR;
import static cn.cnowse.web.ResultCodeEnum.LOGIN_ERROR;
import static cn.cnowse.web.ResultCodeEnum.METHOD_NOT_ALLOWED_ERROR;
import static cn.cnowse.web.ResultCodeEnum.NO_RESOURCE_ERROR;
import static cn.cnowse.web.ResultCodeEnum.NO_SUCH_ERROR;
import static cn.cnowse.web.ResultCodeEnum.PARAM_VERIFY_ERROR;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.security.auth.message.AuthException;
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

    private static final String ERROR_LOG_PREFIX = "URI=[{} {}]，msg=[{}]";

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseResult exceptionHandler(AuthException ex, HttpServletRequest r) {
        log.error(ERROR_LOG_PREFIX, r.getMethod(), r.getRequestURI(), ex.getMessage());
        return BaseResult.err(LOGIN_ERROR);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResult exceptionHandler(NoSuchElementException ex, HttpServletRequest r) {
        log.error(ERROR_LOG_PREFIX, r.getMethod(), r.getRequestURI(), ex.getMessage());
        return BaseResult.err(NO_SUCH_ERROR);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResult exceptionHandler(ServiceException ex, HttpServletRequest r) {
        log.error(ERROR_LOG_PREFIX, r.getMethod(), r.getRequestURI(), ex.getResultCodeEnum().toString());
        return BaseResult.err(ex.getResultCodeEnum());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResult handleException(MethodArgumentNotValidException ex, HttpServletRequest req) {
        StringBuilder errMsg = new StringBuilder();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errMsg.append("，").append(fieldError.getDefaultMessage());
        }
        log.error(ERROR_LOG_PREFIX, req.getMethod(), req.getRequestURI(), errMsg.substring(1));
        return BaseResult.err(PARAM_VERIFY_ERROR, errMsg.substring(1));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResult handleException(HandlerMethodValidationException ex, HttpServletRequest req) {
        StringBuilder errMsg = new StringBuilder();
        ex.getAllErrors().forEach(error -> errMsg.append(",").append(error.getDefaultMessage()));
        log.error(ERROR_LOG_PREFIX, req.getMethod(), req.getRequestURI(), errMsg.substring(1));
        return BaseResult.err(PARAM_VERIFY_ERROR, errMsg.substring(1));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResult exceptionHandler(NoResourceFoundException ex, HttpServletRequest req) {
        log.error(ERROR_LOG_PREFIX, req.getMethod(), req.getRequestURI(), ex.getMessage());
        return BaseResult.err(NO_RESOURCE_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public BaseResult exceptionHandler(HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
        log.error(ERROR_LOG_PREFIX, req.getMethod(), req.getRequestURI(), ex.getMessage());
        return BaseResult.err(METHOD_NOT_ALLOWED_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResult exceptionHandler(Exception ex, HttpServletRequest req) {
        log.error(ERROR_LOG_PREFIX, req.getMethod(), req.getRequestURI(), ex.getMessage(), ex);
        return BaseResult.err(INTERNAL_ERROR);
    }

}
