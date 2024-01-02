package cn.cnowse.redis.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * Cacheable 测试 controller
 *
 * @author Jeong Geol 2024-01-02
 */
@RestController
@RequiredArgsConstructor
public class CacheableTest {

    private final CacheableTestService cacheableTestService;

    @GetMapping("/cacheable")
    public CacheableDTO cacheableTest() {
        return cacheableTestService.test(1);
    }

}
