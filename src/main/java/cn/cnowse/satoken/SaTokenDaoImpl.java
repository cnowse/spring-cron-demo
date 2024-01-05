package cn.cnowse.satoken;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.dev33.satoken.dao.SaTokenDao;

/**
 * SaToken 持久化 token，建议使用自己 Redis 序列化配置，保持项目统一风格
 * 
 * @author Jeong Geol 2023-12-25
 */
@Component
public class SaTokenDaoImpl implements SaTokenDao {

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public void set(String key, String value, long timeout) {

    }

    @Override
    public void update(String key, String value) {

    }

    @Override
    public void delete(String key) {

    }

    @Override
    public long getTimeout(String key) {
        return 0;
    }

    @Override
    public void updateTimeout(String key, long timeout) {

    }

    @Override
    public Object getObject(String key) {
        return null;
    }

    @Override
    public void setObject(String key, Object object, long timeout) {

    }

    @Override
    public void updateObject(String key, Object object) {

    }

    @Override
    public void deleteObject(String key) {

    }

    @Override
    public long getObjectTimeout(String key) {
        return 0;
    }

    @Override
    public void updateObjectTimeout(String key, long timeout) {

    }

    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size, boolean sortType) {
        return null;
    }

}
