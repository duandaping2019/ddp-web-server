package ddp.service.security;

import ddp.BaseService;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;

/**
 * 用户管理服务
 */
public interface SysUserService extends BaseService<SysUserEntity> {

    /*通过登陆ID获取用户信息*/
    SysUserExt findByLoginId(String loginId);

    /*获取登陆用户信息*/
    SysUserExt getLoginUserInfo(SysUserEntity user);

}
