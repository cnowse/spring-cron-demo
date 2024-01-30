package cn.cnowse.web;

/**
 * 工具类自定义异常
 * 
 * @author Jeong Geol
 */
public class UtilException extends RuntimeException {

    public UtilException() {
        super();
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(Throwable cause) {
        super(cause);
    }

}
