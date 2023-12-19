package cn.cnowse.jpa;

import java.util.Collection;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询条件使用方式：<br/>
 * 写一个 spec 对象继承 AbstractPageableSpec。使用 Repository 对象 findAll 方法，直接传入该对象即可
 *
 * @author Jeong Geol 2023-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminSpec extends AbstractPageableSpec {

    @ApiModelProperty(value = "id")
    @Where(property = "id", logic = Where.Logic.EQ)
    private Long id;

    @Where(property = "name", logic = Where.Logic.LIKE)
    private String name;

    @Where(property = "operatorId", logic = Where.Logic.LIKE)
    private Long operatorId;

    @Where(property = "operatorId", logic = Where.Logic.IN)
    private Collection<Long> operatorIdIn;

    @Where(property = "mobile", logic = Where.Logic.STARTS_WITH)
    private String mobile;

}