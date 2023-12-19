package cn.cnowse.jpa;

import java.io.Serial;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * 查询条件生成
 *
 * @author Jeong Geol 2023-12-19
 */
public class SpecGenerator {

    private static final Predicate[] zero_predicate_array = new Predicate[0];

    public static <T> Specification<T> matchAll() {
        return new Specification<>() {

            @Serial
            private static final long serialVersionUID = -1901710137968341638L;

            @Override
            public Predicate toPredicate(@NonNull Root<T> root, @NonNull CriteriaQuery<?> query,
                    @NonNull CriteriaBuilder cb) {

                return null;
            }

        };
    }

    public static <T> Specification<T> generate(final Object cond) {
        return new Specification<>() {

            @Serial
            private static final long serialVersionUID = -6829692497566757391L;

            @Override
            public Predicate toPredicate(@NonNull Root<T> root, @NonNull CriteriaQuery<?> query,
                    @NonNull CriteriaBuilder cb) {

                return cb.and(generate(cond, cond.getClass(), root, cb));
            }

        };
    }

    @SuppressWarnings("rawtypes")
    private static Predicate[] generate(Object cond, Class<?> clazz, Root<?> root, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            Object fieldValue;
            try {
                fieldValue = Objects.requireNonNull(BeanUtils.getPropertyDescriptor(cond.getClass(), fieldName))
                        .getReadMethod().invoke(cond);
            } catch (Exception ex) {
                throw new SpecException("Error occurred when parsing the specification fields, fieldName=" + fieldName
                        + ", errMsg=" + ex.getMessage());
            }
            Where where = AnnotationUtils.findAnnotation(field, Where.class);
            if (where == null) {
                continue;
            }
            String property = StringUtils.hasText(where.property()) ? where.property() : fieldName;
            Where.Logic logic = where.logic();
            ValueHandler valueHandler = where.valueHandler();
            Object pvalue = fieldValue != null && valueHandler != null && valueHandler != ValueHandler.NONE
                    ? valueHandler.handle(fieldValue)
                    : fieldValue;

            if (where.ignoreNullValue()
                    && (pvalue == null || (pvalue instanceof String && pvalue.toString().isEmpty()))) {
                continue;
            }
            final Path path = parsePropertyPath(property, root);
            if (pvalue != null) {
                predicates.add(generatePredicate(cb, logic, path, pvalue));
            } else {
                predicates.add(cb.isNull(path));
            }
        }
        return predicates.toArray(zero_predicate_array);
    }

    @SuppressWarnings({"rawtypes"})
    private static Path parsePropertyPath(String property, Root<?> root) {
        Path path = null;
        if (property.contains(".")) {
            final String[] propertyPaths = StringUtils.tokenizeToStringArray(property, ".");
            if (propertyPaths.length >= 3) {
                Join join = null;
                for (int i = 0; i < propertyPaths.length - 2; i++) {
                    join = join == null ? root.join(propertyPaths[i], JoinType.INNER)
                            : join.join(propertyPaths[i], JoinType.INNER);
                }
                path = join.get(propertyPaths[propertyPaths.length - 2]).get(propertyPaths[propertyPaths.length - 1]);
            } else if (propertyPaths.length == 2) {
                path = root.get(propertyPaths[0]).get(propertyPaths[1]);
            }
        } else {
            path = root.get(property);
        }
        return path;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Predicate generatePredicate(CriteriaBuilder cb, Where.Logic logic, Path path, Object pvalue) {
        switch (logic) {
            case LT:
                return cb.lessThan(path, (Comparable)pvalue);
            case LTE:
                return cb.lessThanOrEqualTo(path, (Comparable)pvalue);
            case GT:
                return cb.greaterThan(path, (Comparable)pvalue);
            case GTE:
                return cb.greaterThanOrEqualTo(path, (Comparable)pvalue);
            case LIKE:
                return cb.like(path, "%" + pvalue.toString() + "%");
            case STARTS_WITH:
                return cb.like(path, pvalue.toString() + "%");
            case IS_NULL:
                if (object2boolean(pvalue))
                    return cb.isNull(path);
                else
                    return cb.isNotNull(path);
            case IS_NOT_NULL:
                if (object2boolean(pvalue))
                    return cb.isNotNull(path);
                else
                    return cb.isNull(path);
            case IN:
                return cb.in(path).value(pvalue);
            case BETWEEN:
                if (pvalue.getClass().isArray() && Array.getLength(pvalue) == 2) {
                    return cb.between(path, (Comparable)Array.get(pvalue, 0), (Comparable)Array.get(pvalue, 1));
                }
            case NOT_IN:
                return cb.in(path).value(pvalue).not();
            case NOT_EQ:
                return cb.notEqual(path, pvalue);
            default:
                return cb.equal(path, pvalue);
        }
    }

    private static boolean object2boolean(Object obj) {
        if (obj == null)
            return false;
        if (obj instanceof Boolean)
            return (Boolean)obj;
        if (obj instanceof CharSequence)
            return Boolean.parseBoolean(obj.toString());
        if (obj instanceof Number) {
            return ((Number)obj).intValue() > 0;
        }
        return false;
    }

}
