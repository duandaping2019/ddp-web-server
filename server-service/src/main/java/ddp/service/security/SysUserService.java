package ddp.service.security;

import ddp.BaseService;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;

import java.util.List;

/**
 * 用户管理服务
 */
public interface SysUserService extends BaseService<SysUserEntity> {
    /*获取用户实体信息*/
    SysUserExt getExtInfo(SysUserExt ext);

    /*获取用户分页信息*/
    List<SysUserExt> getExtListInfo(SysUserExt ext);

    /*用户信息存储*/
    SysUserEntity saveOrUpdate(SysUserExt ext, SysUserExt operator);

}

