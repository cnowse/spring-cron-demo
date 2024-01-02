package cn.cnowse.redis.test;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Cacheable 测试 service
 * 
 * @author Jeong Geol 2024-01-02
 */
@Service
public class CacheableTestService {

    /**
     * 1.加上 @Cacheable 后，测试缓存的 key,value 格式是否符合自定义的格式 <br/>
     * 2.测试自定义的 tll 规则是否生效
     */
    @Cacheable(value = "pre_test#1d", key = "#id")
    public CacheableDTO test(Integer id) {
        CacheableDTO dto = new CacheableDTO();
        dto.setId(id);
        dto.setName("test");
        return dto;
    }

}
