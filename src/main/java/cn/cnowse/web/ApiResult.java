package cn.cnowse.web;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回结果
 *
 * @author Jeong Geol 2023-12-18
 */
@Data
@NoArgsConstructor
public class ApiResult {

    /** 是否成功？true：成功；false：失败 */
    private boolean success;

    /** 返回内容 */
    private String errMsg;

    /** 错误代码 */
    private String errCode;

    /** 数据对象 */
    private Object data;

    /**
     * 初始化一个新创建的 ApiResult 对象
     *
     * @param success 是否成功？true：成功；false：失败
     * @param errMsg 返回内容
     */
    public ApiResult(boolean success, String errMsg, String errCode) {
        this.success = success;
        this.errMsg = errMsg;
        this.errCode = errCode;
    }

    /**
     * 初始化一个新创建的 ApiResult 对象
     *
     * @param success 是否成功？true：成功；false：失败
     * @param errMsg 返回内容
     * @param data 数据对象
     */
    public ApiResult(boolean success, String errMsg, String errCode, Object data) {
        this.success = success;
        this.errMsg = errMsg;
        this.errCode = errCode;
        if (data != null) {
            this.data = data;
        }
    }

    public static ApiResult ok() {
        return new ApiResult(true, null, null);
    }

    public static ApiResult ok(Object data) {
        return new ApiResult(true, null, null, data);
    }

    public static ApiResult err(Throwable ex) {
        return new ApiResult(false, ex.getClass().getName(), ex.getMessage());
    }

    public static ApiResult err(String errMsg, String errCode) {
        return new ApiResult(false, errMsg, errCode);
    }

}
