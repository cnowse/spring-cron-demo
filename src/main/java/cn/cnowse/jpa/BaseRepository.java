package cn.cnowse.jpa;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * JPA 基础 Repository，所有 Entity 继承此类
 *
 * @author Jeong Geol 2023-12-19
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    int MAX_ALL_SIZE = 1000;

    default Page<T> listBySpec(AbstractPageableSpec spec, Pageable pageable) {
        return this.findAll(spec.toSpecification(), spec.toPageRequest());
    }

    default List<T> listAllBySpec(AbstractPageableSpec spec) {
        spec.setPage(0);
        spec.setSize(MAX_ALL_SIZE);
        return this.findAll(spec.toSpecification(), spec.toPageRequest()).getContent();
    }

    default Page<T> listBySpec(EntitySpec spec, Pageable pageable) {
        return this.findAll(spec.toSpecification(), pageable);
    }

}
