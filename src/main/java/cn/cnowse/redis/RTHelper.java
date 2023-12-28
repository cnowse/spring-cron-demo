package cn.cnowse.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * @author Jeong Geol 2023-12-28
 */
@Component
public class RTHelper {

    @Resource(name = "rt")
    private RedisTemplate<String, Object> redis;

    public void set(String key, Object value) {
        redis.opsForValue().set(key, value);
    }

}
