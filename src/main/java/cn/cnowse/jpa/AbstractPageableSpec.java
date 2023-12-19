package cn.cnowse.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import lombok.Data;

/**
 * JPA 分页
 *
 * @author Jeong Geol 2023-12-19
 */
@Data
public abstract class AbstractPageableSpec implements EntitySpec {

    static final String DEFAULT_SORT_BY_ID = "id";

    private int page = 1;

    private int size = 10;

    private String sort = DEFAULT_SORT_BY_ID;

    public Pageable toPageRequest() {
        return PageRequest.of(Math.max(0, page - 1), size, getSort());
    }

    protected Sort getSort() {
        if (sort == null || sort.isEmpty())
            return null;
        if (DEFAULT_SORT_BY_ID.equalsIgnoreCase(sort)) {
            return Sort.by(Direction.DESC, sort);
        }
        String[] sortFields = sort.split(",");
        if (sortFields.length == 1) {
            return Sort.by(Direction.ASC, sort);
        }
        List<Order> orderlist = new ArrayList<>();
        for (int i = 0; i < sortFields.length; i++) {
            String currProp = sortFields[i];
            String nextProp = i + 1 < sortFields.length ? sortFields[i + 1] : null;
            if ("DESC".equalsIgnoreCase(nextProp)) {
                orderlist.add(Order.desc(currProp));
                i += 1;
            } else if ("ASC".equalsIgnoreCase(nextProp)) {
                orderlist.add(Order.asc(currProp));
                i += 1;
            } else if (currProp != null && !currProp.isEmpty()) {
                orderlist.add(Order.asc(currProp));
            }
        }
        return Sort.by(orderlist);
    }

}
