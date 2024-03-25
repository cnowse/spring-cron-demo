package cn.cnowse.web;

import lombok.Getter;

/**
 * 业务异常
 * 
 * @author Jeong Geol
 */
@Getter
public class ServiceException extends RuntimeException {

    private final ResultCodeEnum resultCodeEnum;

    public ServiceException(ResultCodeEnum resultCodeEnum, Throwable cause) {
        super(resultCodeEnum.getMsg(), cause);
        this.resultCodeEnum = resultCodeEnum;
    }

    public ServiceException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMsg());
        this.resultCodeEnum = resultCodeEnum;
    }

}
