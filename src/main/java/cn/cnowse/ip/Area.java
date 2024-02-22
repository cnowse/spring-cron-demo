package cn.cnowse.ip;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 区域节点，包括国家、省份、城市、地区等信息 <br/>
 * 数据可见 resources/area.csv 文件
 *
 * @author Jeong Geol
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area {

    /** 编号 - 全球，即根目录 */
    public static final Integer ID_GLOBAL = 0;

    /** 编号 - 中国 */
    public static final Integer ID_CHINA = 1;

    /** 编号 */
    private Integer id;

    /** 名字 */
    private String name;

    /** 类型 枚举 {@link AreaTypeEnum} */
    private Integer type;

    /** 父节点 */
    private Area parent;

    /** 子节点 */
    private List<Area> children;

}
