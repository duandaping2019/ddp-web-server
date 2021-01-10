package ddp.service.security;

import ddp.ext.security.SysRoleExt;

import java.util.List;

/**
 * 角色管理服务
 */
public interface SysRoleService {

    /*获取角色列表*/
    List<SysRoleExt> getExtListInfo(SysRoleExt ext);

}
