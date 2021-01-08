package ddp.service.security;

import ddp.ext.security.SysMenuExt;

import java.util.List;

/**
 * 角色管理服务
 */
public interface SysMenuService {
    /*获取菜单列表数据*/
    List<SysMenuExt> getExtListInfo(SysMenuExt ext);

    /*获取菜单树集合信息*/
    List<SysMenuExt> getMenuTreeData();

}
