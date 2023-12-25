package cn.cnowse.mybatis;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * Mybatis Plus 元数据自动填充。例如表字段中的创建时间，修改时间，创建人，修改人等信息
 * 
 * @author Jeong Geol 2023-12-20
 */
public class CustomizeMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // insert 插入时，自动填充 created 与 modified 字段
        setFieldValByName("created", LocalDateTime.now(), metaObject);
        setFieldValByName("modified", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // update 修改时，自动填充 modified 字段
        setFieldValByName("modified", LocalDateTime.now(), metaObject);
    }

}
