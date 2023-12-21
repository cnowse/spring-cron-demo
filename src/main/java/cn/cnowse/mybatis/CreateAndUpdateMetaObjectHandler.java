package cn.cnowse.mybatis;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * Mybatis Plus 元数据自动填充。例如表字段中的创建时间，修改时间，创建人，修改人等信息
 * 
 * @author Jeong Geol 2023-12-20
 */
public class CreateAndUpdateMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }

}
