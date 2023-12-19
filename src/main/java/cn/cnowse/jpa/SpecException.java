package cn.cnowse.jpa;

import java.io.Serial;

/**
 * 查询条件异常
 *
 * @author Jeong Geol 2023-12-19
 */
public class SpecException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 750855345938953838L;

    public SpecException() {
        super();
    }

    public SpecException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SpecException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpecException(String message) {
        super(message);
    }

    public SpecException(Throwable cause) {
        super(cause);
    }

}
