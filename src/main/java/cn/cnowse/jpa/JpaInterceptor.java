package cn.cnowse.jpa;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.stereotype.Component;

/**
 * Jpa 执行 sql 前进行拦截，可以在此做数据权限
 *
 * @author Jeong Geol
 */
@Component
public class JpaInterceptor implements StatementInspector {

    @Override
    public String inspect(String sql) {
        String ids = "1, 2, 3";
        sql = sql + "where id in (" + ids + ")";
        return sql;
    }

}
