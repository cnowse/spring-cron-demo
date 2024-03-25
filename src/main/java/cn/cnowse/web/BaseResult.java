package cn.cnowse.web;

import static cn.cnowse.web.ResultCodeEnum.SUCCESS;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回结果
 *
 * @author Jeong Geol
 */
@Data
@NoArgsConstructor
public class BaseResult {

    /** 是否成功？true：成功；false：失败 */
    private boolean success;

    /** 返回内容 */
    private String msg;

    /** 错误代码 */
    private Integer code;

    /** 数据对象 */
    private Object data;

    /** 当前请求时间戳 */
    private long timestamp;

    /**
     * 初始化一个新创建的 ApiResult 对象
     *
     * @param success 是否成功？true：成功；false：失败
     * @param msg 返回内容
     */
    public BaseResult(boolean success, String msg, Integer code) {
        this.timestamp = System.currentTimeMillis();
        this.success = success;
        this.msg = msg;
        this.code = code;
    }

    /**
     * 初始化一个新创建的 ApiResult 对象
     *
     * @param success 是否成功？true：成功；false：失败
     * @param msg 返回内容
     * @param data 数据对象
     */
    public BaseResult(boolean success, String msg, Integer code, Object data) {
        this.timestamp = System.currentTimeMillis();
        this.success = success;
        this.msg = msg;
        this.code = code;
        if (data != null) {
            this.data = data;
        }
    }

    /**
     * 成功，没有数据返回
     *
     * @author Jeong Geol
     */
    public static BaseResult ok() {
        return new BaseResult(true, SUCCESS.getMsg(), SUCCESS.getCode());
    }

    /**
     * 成功，有数据返回
     *
     * @author Jeong Geol
     */
    public static BaseResult ok(Object data) {
        return new BaseResult(true, SUCCESS.getMsg(), SUCCESS.getCode(), data);
    }

    /**
     * 失败，指定错误码
     *
     * @author Jeong Geol
     */
    public static BaseResult err(ResultCodeEnum error) {
        return new BaseResult(false, error.getMsg(), error.getCode());
    }

    /**
     * 失败，自定义错误信息
     *
     * @author Jeong Geol
     */
    public static BaseResult err(ResultCodeEnum error, String msg) {
        return new BaseResult(false, msg, error.getCode());
    }

}
