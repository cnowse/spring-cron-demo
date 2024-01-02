package cn.cnowse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 通用配置
 *
 * @author Jeong Geol 2023-12-18
 */
@Slf4j
@Configuration
public class CommonConfig {

    /** 默认日期时间格式 */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** 默认日期格式 */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /** 默认时间格式 */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    @Bean(name = "asyncServiceExecutor")
    public ThreadPoolTaskScheduler asyncServiceExecutor() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        // 配置核心线程数
        executor.setPoolSize(5);
        // 配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("AsyncExec-");
        executor.setErrorHandler(t -> log.error("", t));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 执行初始化
        executor.initialize();
        return executor;
    }

    @Bean
    public ObjectMapper configObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        /*
         设置属性的可见性，这里将所有属性都设置为可见
         所有类的所有属性都将被序列化和反序列化，即使它们是私有的。这确保了在序列化和反序列化时，所有属性都会被处理
         */
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.DEFAULT);
        // 禁用在反序列化时对未知属性的错误检查，这允许 JSON 中包含 Java 对象中没有的额外属性
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        /*
         禁用默认的类型处理，提高序列化和反序列化的安全性
         默认情况下，Jackson 会在序列化时添加类型信息，以便在反序列化时正确还原对象
         禁用默认类型处理可以防止潜在的安全风险，如 Java 反序列化漏洞
         */
        om.deactivateDefaultTyping();
        // 所有日期时间值在序列化和反序列化时都将使用 GMT+8 时区
        om.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // 设置输出格式为缩进，使得生成的JSON更易读
        om.configure(SerializationFeature.INDENT_OUTPUT, true);
        // 在序列化时，只有非空属性会被包含在 JSON 中，为空的属性将被忽略。这有助于减少输出 JSON 的大小，同时保留实际有值的属性
        om.setDefaultPropertyInclusion(
                JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL));
        // 自动发现所有的模块
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        om.registerModule(javaTimeModule);

        SimpleModule numberModule = new SimpleModule();
        numberModule.addSerializer(BigDecimal.class, BigDecimalJsonSerializer.INSTANCE);
        numberModule.addSerializer(Double.class, DoubleJsonSerializer.INSTANCE);
        numberModule.addSerializer(double.class, DoubleJsonSerializer.INSTANCE);
        // long 转 String，解决前端 long 溢出问题
        numberModule.addSerializer(Long.class, LongJsonSerializer.INSTANCE);
        numberModule.addSerializer(long.class, LongJsonSerializer.INSTANCE);
        // 反序列化时删除 String 中的 html 标签
        numberModule.addDeserializer(String.class, JsonHtmlXssTrimDeSerializer.INSTANCE);
        om.registerModule(numberModule);
        return om;
    }

    @Bean
    public MappingJackson2HttpMessageConverter configMappingJackson2HttpMessageConverter(ObjectMapper om) {
        return new MappingJackson2HttpMessageConverter(om);
    }

    /**
     * BigDecimal 保存 4 位小数
     *
     * @author Jeong Geol
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class BigDecimalJsonSerializer extends JsonSerializer<BigDecimal> {

        public static final BigDecimalJsonSerializer INSTANCE = new BigDecimalJsonSerializer();

        @Override
        public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value == null) {
                gen.writeNull();
            } else {
                gen.writeNumber(String.format("%1$1.4f", value));
            }
        }

    }

    /**
     * double Double 保存 4 位小数
     *
     * @author Jeong Geol
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class DoubleJsonSerializer extends JsonSerializer<Double> {

        public static final DoubleJsonSerializer INSTANCE = new DoubleJsonSerializer();

        @Override
        public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value == null) {
                gen.writeNull();
            } else {
                gen.writeNumber(String.format("%1$1.4f", value));
            }
        }

    }

    /**
     * Long 转成 String
     *
     * @author Jeong Geol
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class LongJsonSerializer extends JsonSerializer<Long> {

        public static final LongJsonSerializer INSTANCE = new LongJsonSerializer();

        @Override
        public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value == null) {
                gen.writeNull();
            } else {
                gen.writeString(String.valueOf(value));
            }
        }

    }

    /**
     * 判断 long 范围是否在 JS Number.MAX_SAFE_INTEGER 与 Number.MIN_SAFE_INTEGER 范围内，
     * 如果在范围内，仍序列化为 long，不在范围内则序列化为 string
     *
     * @author Jeong Geol
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class CheckLongRangeJsonSerializer extends JsonSerializer<Long> {

        public static final CheckLongRangeJsonSerializer INSTANCE = new CheckLongRangeJsonSerializer();
        private static final long MAX_SAFE_INTEGER = 9007199254740991L;
        private static final long MIN_SAFE_INTEGER = -9007199254740991L;

        @Override
        public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            // 超出范围 序列化位字符串
            if (value > MIN_SAFE_INTEGER && value < MAX_SAFE_INTEGER) {
                gen.writeNumber(value);
            } else {
                gen.writeString(value.toString());
            }
        }

    }

    /**
     * 反序列化时，去除 String 类型数据中的 html 标签防止 XSS 注入 例如：
     * <h1>replace</h1> hello 会被替换为 replace hello
     *
     * @author Jeong Geol
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class JsonHtmlXssTrimDeSerializer extends JsonDeserializer<String> {

        public static final JsonHtmlXssTrimDeSerializer INSTANCE = new JsonHtmlXssTrimDeSerializer();
        private static final String RE_HTML_MARK = "(<[^<]*?>)|(<\\s*?/[^<]*?>)|(<[^<]*?/\\s*?>)";

        @Override
        public String deserialize(JsonParser p, DeserializationContext context) throws IOException {
            String value = p.getValueAsString();
            if (value != null) {
                return value.replaceAll(RE_HTML_MARK, "");
            }
            return null;
        }

    }

}
