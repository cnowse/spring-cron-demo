package cn.cnowse.redis;

import java.time.Duration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.NonNull;

/**
 * 支持自定义过期时间的 {@link RedisCacheManager} 实现类 在 {@link Cacheable#cacheNames()} 格式为
 * "key#ttl" 时，# 后面的 ttl 为过期时间。 单位为最后一个字母（支持的单位有：d 天，h 小时，m 分钟，s 秒），默认单位为 s 秒
 *
 * @author Jeong Geol 2024-01-02
 */
public class TimeoutRedisCacheManager extends RedisCacheManager {

    private static final String SPLIT = "#";

    public TimeoutRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @NonNull
    @Override
    protected RedisCache createRedisCache(@NonNull String name, RedisCacheConfiguration cacheConfig) {
        if (StringUtils.isEmpty(name)) {
            return super.createRedisCache(name, cacheConfig);
        }
        // 如果使用 # 分隔，大小不为 2，则说明不使用自定义过期时间
        String[] names = StringUtils.split(name, SPLIT);
        if (names.length != 2) {
            return super.createRedisCache(name, cacheConfig);
        }

        // 核心：通过修改 cacheConfig 的过期时间，实现自定义过期时间
        if (cacheConfig != null) {
            // 移除 # 后面的 : 以及后面的内容，避免影响解析
            names[1] = StringUtils.substringBefore(names[1], RedisConfig.COLON);
            // 解析时间
            Duration duration = parseDuration(names[1]);
            cacheConfig = cacheConfig.entryTtl(duration);
        }
        return super.createRedisCache(name, cacheConfig);
    }

    /**
     * 解析过期时间 Duration
     *
     * @param ttlStr 过期时间字符串
     * @return 过期时间 Duration
     */
    private Duration parseDuration(String ttlStr) {
        String timeUnit = StringUtils.substring(ttlStr, -1);
        return switch (timeUnit) {
            case "d" -> Duration.ofDays(removeDurationSuffix(ttlStr));
            case "h" -> Duration.ofHours(removeDurationSuffix(ttlStr));
            case "m" -> Duration.ofMinutes(removeDurationSuffix(ttlStr));
            case "s" -> Duration.ofSeconds(removeDurationSuffix(ttlStr));
            default -> Duration.ofSeconds(Long.parseLong(ttlStr));
        };
    }

    /**
     * 移除多余的后缀，返回具体的时间
     *
     * @param ttlStr 过期时间字符串
     * @return 时间
     */
    private Long removeDurationSuffix(String ttlStr) {
        return Long.parseLong(StringUtils.substring(ttlStr, 0, ttlStr.length() - 1));
    }

}
