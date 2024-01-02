package cn.cnowse.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

/**
 * Redis 常用命令封装。由于重新配置了
 * {@link RedisConfig#redisTemplate(RedisConnectionFactory, ObjectMapper)}，该类主要使用该
 * template 进行缓存，主要用于测试配置是否生效，也可以对该类进一步封装，方便使用
 * 
 * @author Jeong Geol 2023-12-28
 */
@Component
@RequiredArgsConstructor
public class RTHelper {

    private final RedisTemplate<String, Object> redis;

    public void set(String key, Object value) {
        redis.opsForValue().set(key, value);
    }

}
