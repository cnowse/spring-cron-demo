package cn.cnowse.jpa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 查询条件处理
 *
 * @author Jeong Geol 2023-12-19
 */
public enum ValueHandler {

    NONE() {

        @Override
        public Object handle(Object value) {
            return value;
        }

    },

    AD_CODE() {

        @Override
        public Object handle(Object value) {
            if (value == null)
                return null;
            if (value instanceof CharSequence) {
                String adCode = value.toString();
                if (adCode.equals("100000")) {
                    return null;
                }
                if (adCode.length() >= 6) {
                    if (adCode.endsWith("0000")) {
                        return adCode.substring(0, 2);
                    } else if (adCode.endsWith("00")) {
                        return adCode.substring(0, 4);
                    } else {
                        return adCode;
                    }
                }
                return adCode;
            }
            return null;
        }

    },

    END_OF_DATE() {

        @Override
        public Object handle(Object value) {
            if (LocalDateTime.class.equals(value.getClass())) {
                return ((LocalDateTime)value).toLocalDate().atTime(LocalTime.MAX);
            } else if (LocalDate.class.equals(value.getClass())) {
                return ((LocalDate)value).atTime(LocalTime.MAX);
            } else {
                return value;
            }

        }

    },

    BEGIN_OF_DATE() {

        @Override
        public Object handle(Object value) {
            if (LocalDateTime.class.equals(value.getClass())) {
                return ((LocalDateTime)value).toLocalDate().atTime(LocalTime.MIN);
            } else if (LocalDate.class.equals(value.getClass())) {
                return ((LocalDate)value).atTime(LocalTime.MIN);
            } else {
                return value;
            }
        }

    };

    ValueHandler() {}

    public abstract Object handle(Object value);

}
