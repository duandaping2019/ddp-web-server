package ddp.service.security;

import ddp.entity.security.SysMenuEntity;
import ddp.ext.security.SysMenuExt;

import java.math.BigDecimal;
import java.util.List;

/**
 * 角色管理服务
 */
public interface SysMenuService {

    /*获取菜单信息*/
    SysMenuExt getExtInfo(SysMenuExt ext);

    /*获取菜单列表数据*/
    List<SysMenuExt> getExtListInfo(SysMenuExt ext);

    /*获取菜单树集合信息*/
    List<SysMenuExt> getMenuTreeData(SysMenuExt ext);

    /*存储菜单信息*/
    SysMenuEntity saveOrUpdate(SysMenuExt ext);

    /*菜单信息删除*/
    void delMenuInfo(List<BigDecimal> idsList);

}
