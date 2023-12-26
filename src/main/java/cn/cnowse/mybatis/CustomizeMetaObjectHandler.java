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

    /** 创建时间，数据库字段名 */
    public static final String CREATE_TIME_FILED = "created";

    /** 修改时间，数据库字段名 */
    public static final String UPDATE_TIME_FILED = "modified";

    @Override
    public void insertFill(MetaObject metaObject) {
        // insert 插入时，自动填充 created 与 modified 字段
        if (metaObject.hasSetter(CREATE_TIME_FILED)) {
            setFieldValByName(CREATE_TIME_FILED, LocalDateTime.now(), metaObject);
        }
        if (metaObject.hasSetter(UPDATE_TIME_FILED)) {
            setFieldValByName(UPDATE_TIME_FILED, LocalDateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // update 修改时，自动填充 modified 字段
        if (metaObject.hasSetter(UPDATE_TIME_FILED)) {
            setFieldValByName(UPDATE_TIME_FILED, LocalDateTime.now(), metaObject);
        }
    }

}
