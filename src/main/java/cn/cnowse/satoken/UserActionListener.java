package cn.cnowse.satoken;

import org.springframework.stereotype.Component;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;

/**
 * SaToken 监听用户行为，可以在发生登录，退出等一系列行为时做一些操作
 *
 * @author Jeong Geol 2023-12-25
 */
@Component
public class UserActionListener implements SaTokenListener {

    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {

    }

    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {

    }

    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {

    }

    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {

    }

    @Override
    public void doDisable(String loginType, Object loginId, String service, int level, long disableTime) {

    }

    @Override
    public void doUntieDisable(String loginType, Object loginId, String service) {

    }

    @Override
    public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {

    }

    @Override
    public void doCloseSafe(String loginType, String tokenValue, String service) {

    }

    @Override
    public void doCreateSession(String id) {

    }

    @Override
    public void doLogoutSession(String id) {

    }

    @Override
    public void doRenewTimeout(String tokenValue, Object loginId, long timeout) {

    }

}
