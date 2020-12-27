package ddp.service.security;

import ddp.BaseService;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 用户管理服务
 */
public interface SysUserService extends BaseService<SysUserEntity> {
    /*获取用户实体信息【简单】*/
    SysUserExt getEntityInfo(SysUserExt ext);

    /*获取用户实体信息【复杂】*/
    SysUserExt getExtInfo(SysUserExt ext);

    /*获取用户分页信息*/
    List<SysUserExt> getExtListInfo(SysUserExt ext);

    /*获取当前用户可配置角色列表*/
    List<Map<String, Object>> userRoleSelect(BigDecimal userId);

    /*用户信息存储*/
    SysUserEntity saveOrUpdate(SysUserExt ext);

    /*用户信息删除*/
    void delUserInfo(List<BigDecimal> idsList);

}

