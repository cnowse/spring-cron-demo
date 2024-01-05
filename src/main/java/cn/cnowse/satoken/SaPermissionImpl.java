package cn.cnowse.satoken;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.dev33.satoken.stp.StpInterface;

/**
 * SaToken 登录时获取权限列表与角色列表
 * 
 * @author Jeong Geol 2023-12-25
 */
@Component
public class SaPermissionImpl implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return null;
    }

}
