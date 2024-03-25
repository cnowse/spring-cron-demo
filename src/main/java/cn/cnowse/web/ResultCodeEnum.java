package cn.cnowse.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应码
 *
 * @author Jeong Geol
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {

    SUCCESS(2000, "成功"),

    PARAM_VERIFY_ERROR(4000, "参数校验错误"),
    LOGIN_ERROR(4001, "账号或密码错误"),
    AUTH_ERROR(4002, "登录失效，请重新登录"),

    NO_SUCH_ERROR(4004, "指定对象不存在"),

    NO_BUS_ERROR(4010, "指定的总线不存在"),
    NO_BMS_DEVICE_ERROR(4011, "指定的BMS设备不存在"),
    NO_AC_DEVICE_ERROR(4012, "指定的风冷设备不存在"),
    NO_CABIN_DEVICE_ERROR(4013, "指定的设备舱设备不存在"),
    NO_LC_DEVICE_ERROR(4014, "指定的设风冷设备不存在"),
    NO_METER_DEVICE_ERROR(4015, "指定的设电表设备不存在"),
    NO_IO_DEVICE_ERROR(4016, "指定的设IO设备不存在"),
    NO_MAIN_DEVICE_ERROR(4017, "指定的主侧设备不存在"),
    NO_SECOND_DEVICE_ERROR(4018, "指定的副侧设备不存在"),
    NO_DEVICE_TYPE_ERROR(4019, "未知设备类型"),
    NO_DEVICE_CONF_ERROR(4020, "必要的设备特有配置不存在"),
    NO_API_GROUP_ERROR(4021, "必要的API分组不存在"),
    NO_RESOURCE_ERROR(4022, "请求的资源不存在"),
    NO_API_ERROR(4023, "指定的API不存在"),

    HAS_DEVICE_BIND_ERROR(4100, "已被设备绑定，无法删除"),
    HAS_GROUP_ID_ERROR(4101, "API分组ID已存在，请更换"),
    HAS_API_ID_ERROR(4102, "API_ID已存在，请更换"),
    HAS_BESS_ERROR(4103, "已存在储能电站，无需重复添加"),
    HAS_ROLE_BIND_ERROR(4104, "已存在角色绑定，无法删除"),

    INTERNAL_ERROR(5000, "系统错误"),
    LOAD_CSV_ERROR(5001, "读取csv失败，请联系管理员"),
    ROLE_ID_CANNOT_LE_255_ERROR(5002, "被操作的角色，标识不能大于等于255"),
    METHOD_NOT_ALLOWED_ERROR(5003, "HTTP方法不被允许");

    private final Integer code;
    private final String msg;

}
