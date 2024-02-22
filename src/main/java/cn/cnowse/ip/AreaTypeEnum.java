package cn.cnowse.ip;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 区域类型枚举
 *
 * @author Jeong Geol
 */
@Getter
@AllArgsConstructor
public enum AreaTypeEnum {

    COUNTRY(1, "国家"),
    PROVINCE(2, "省份"),
    CITY(3, "城市"),
    /** 县、镇、区等 */
    DISTRICT(4, "地区");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(AreaTypeEnum::getType).toArray();

    /** 类型 */
    private final Integer type;
    /** 名字 */
    private final String name;

    public int[] array() {
        return ARRAYS;
    }

}
