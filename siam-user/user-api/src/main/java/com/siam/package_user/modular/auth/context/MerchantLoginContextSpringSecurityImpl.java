
package com.siam.package_user.modular.auth.context;

import cn.hutool.core.util.ObjectUtil;
import com.siam.package_common.context.login.MerchantLoginContext;
import com.siam.package_common.exception.AuthException;
import com.siam.package_common.exception.enums.AuthExceptionEnum;
import com.siam.package_common.pojo.login.SysLoginMerchant;
import com.siam.package_user.auth.cache.MerchantSessionManager;
import com.siam.package_user.entity.Merchant;
import com.siam.package_user.service.MerchantService;
import com.siam.package_user.util.TokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 登录用户上下文实现类
 *
 * @author
 * @date 2020/3/13 12:19
 */
@Component
public class MerchantLoginContextSpringSecurityImpl implements MerchantLoginContext {

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    private MerchantLoginContextSpringSecurityImpl() {

    }

    /**
     * 获取当前登录用户
     *
     * @author
     * @date 2020/3/13 14:42
     */
    @Override
    public SysLoginMerchant getSysLoginMerchant() {
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());
        if (ObjectUtil.isEmpty(loginMerchant)) {
            throw new AuthException(AuthExceptionEnum.LOGIN_EXPIRED);
        } else {
            SysLoginMerchant sysLoginMerchant = new SysLoginMerchant();
            BeanUtils.copyProperties(loginMerchant, sysLoginMerchant);
            return sysLoginMerchant;
        }
    }

    /**
     * 管理员类型（0超级管理员 1非管理员）
     * 判断当前登录用户是否是超级管理员
     *
     * @author
     * @date 2020/3/23 17:51
     */
    @Override
    public boolean isSuperAdmin() {
        //TODO - 临时限制
        /*return this.isMerchant(MerchantTypeEnum.SUPER_ADMIN.getCode());*/
        return getSysLoginMerchant().getUsername().startsWith("admin-");
    }
}