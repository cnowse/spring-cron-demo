package cn.cnowse.ip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * 区域工具类
 *
 * @author Jeong Geol
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AreaUtils {

    /** Area 内存缓存，提升访问速度 */
    private static final Map<Integer, Area> AREAS;

    static {
        long now = System.currentTimeMillis();
        AREAS = new HashMap<>();
        AREAS.put(Area.ID_GLOBAL, new Area(Area.ID_GLOBAL, "全球", 0, null, new ArrayList<>()));
        // 从 csv 中加载数据
        List<CsvRow> rows = CsvUtil.getReader().read(ResourceUtil.getUtf8Reader("area.csv")).getRows();
        // 删除 header
        rows.remove(0);
        for (CsvRow row : rows) {
            // 创建 Area 对象
            Area area = new Area(Integer.valueOf(row.get(0)), row.get(1),
                    Integer.valueOf(row.get(2)), null, new ArrayList<>());
            // 添加到 areas 中
            AREAS.put(area.getId(), area);
        }

        // 构建父子关系：因为 Area 中没有 parentId 字段，所以需要重复读取
        for (CsvRow row : rows) {
            // 自己
            Area area = AREAS.get(Integer.valueOf(row.get(0)));
            // 父
            Area parent = AREAS.get(Integer.valueOf(row.get(3)));
            Assert.isTrue(area != parent, "{}:父子节点相同", area.getName());
            area.setParent(parent);
            parent.getChildren().add(area);
        }
        log.info("启动加载 AreaUtils 成功，耗时 ({}) 毫秒", System.currentTimeMillis() - now);
    }

    /**
     * 获得指定编号对应的区域
     *
     * @param id 区域编号
     * @return 区域
     */
    public static Area getArea(Integer id) {
        return AREAS.get(id);
    }

    /**
     * 格式化区域
     *
     * @param id 区域编号
     * @return 格式化后的区域
     */
    public static String format(Integer id) {
        return format(id, " ");
    }

    /**
     * 格式化区域 <br/>
     * 例如说：
     * <ol>
     * <li>id = “静安区”时：上海 上海市 静安区</li>
     * <li>id = “上海市”时：上海 上海市</li>
     * <li>id = “上海”时：上海</li>
     * <li>id = “美国”时：美国 当区域在中国时，默认不显示中国</li>
     * </ol>
     *
     * @param id 区域编号
     * @param separator 分隔符
     * @return 格式化后的区域
     */
    public static String format(Integer id, String separator) {
        // 获得区域
        Area area = AREAS.get(id);
        if (area == null) {
            return null;
        }

        // 格式化
        StringBuilder sb = new StringBuilder();
        // 避免死循环
        for (int i = 0; i < AreaTypeEnum.values().length; i++) {
            sb.insert(0, area.getName());
            // “递归”父节点
            area = area.getParent();
            if (area == null || ObjectUtils.equalsAny(area.getId(), Area.ID_GLOBAL, Area.ID_CHINA)) {
                // 跳过父节点为中国的情况
                break;
            }
            sb.insert(0, separator);
        }
        return sb.toString();
    }

    /**
     * 获取指定类型的区域列表
     *
     * @param type 区域类型
     * @param func 转换函数
     * @param <T> 结果类型
     * @return 区域列表
     */
    public static <T> List<T> getByType(AreaTypeEnum type, Function<Area, T> func) {
        return CollectionUtils.convertList(AREAS.values(), func, area -> type.getType().equals(area.getType()));
    }

    /**
     * 根据区域编号、上级区域类型，获取上级区域编号
     *
     * @param id 区域编号
     * @param type 区域类型
     * @return 上级区域编号
     */
    public static Integer getParentIdByType(Integer id, @NonNull AreaTypeEnum type) {
        for (int i = 0; i < Byte.MAX_VALUE; i++) {
            Area area = AreaUtils.getArea(id);
            if (area == null) {
                return null;
            }
            // 情况一：匹配到，返回它
            if (type.getType().equals(area.getType())) {
                return area.getId();
            }
            // 情况二：找到根节点，返回空
            if (area.getParent() == null || area.getParent().getId() == null) {
                return null;
            }
            // 其它：继续向上查找
            id = area.getParent().getId();
        }
        return null;
    }

}
