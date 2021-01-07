package ddp.service.security;

import ddp.ext.security.SysMenuExt;

import java.util.List;

/**
 * 角色管理服务
 */
public interface SysMenuService {

    List<SysMenuExt> getExtListInfo(SysMenuExt ext);

}
