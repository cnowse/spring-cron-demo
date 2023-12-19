package cn.cnowse.jpa;

import org.springframework.data.jpa.domain.Specification;

/**
 * 查询条件
 *
 * @author Jeong Geol 2023-12-19
 */
public interface EntitySpec {

    default <T> Specification<T> toSpecification() {
        return SpecGenerator.generate(this);
    }

}
