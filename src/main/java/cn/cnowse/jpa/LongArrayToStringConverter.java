package cn.cnowse.jpa;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StringUtils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Long 数组转以 , 分割的字符串
 *
 * @author Jeong Geol 2023-12-19
 */
@Converter
public class LongArrayToStringConverter implements AttributeConverter<Set<Long>, String> {

    /**
     * Set 转成以 , 分割的字符串
     *
     * @author Jeong Geol
     */
    @Override
    public String convertToDatabaseColumn(Set<Long> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        attribute.forEach(sid -> sb.append(sid).append(","));
        return sb.toString();
    }

    /**
     * 以 , 分割的字符串转 Set
     *
     * @author Jeong Geol
     */
    @Override
    public Set<Long> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return Collections.emptySet();
        }
        HashSet<Long> set = new HashSet<>();
        for (String strId : StringUtils.tokenizeToStringArray(dbData, ",", true, true)) {
            set.add(Long.parseLong(strId));
        }
        return Collections.unmodifiableSet(set);
    }

}
