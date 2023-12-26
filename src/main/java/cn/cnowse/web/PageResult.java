package cn.cnowse.web;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.Data;

/**
 * Mybatis Plus 分页查询结果返回格式
 * 
 * @author Jeong Geol 2023-12-25
 */
@Data
public class PageResult<T> {

    /** 总数 */
    private long total;

    /** 每页显示条数，默认 10 */
    private long size;

    /** 当前页 */
    private long current;

    /** 数据 */
    private List<T> records;

    public PageResult(IPage<T> page) {
        this.total = page.getTotal();
        this.size = page.getSize();
        this.current = page.getCurrent();
        this.records = page.getRecords();
    }

}
