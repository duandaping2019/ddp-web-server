package ddp.service.security;

import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysMenuExt;

import java.util.List;
import java.util.Set;

/**
 * 系统首页服务类
 */
public interface SysIndexService {

    /*获取用户菜单信息*/
    List<SysMenuExt> selectMenuList(SysUserEntity userEntity);

    /*获取用户权限信息*/
    Set<String> selectPermissions(SysUserEntity userEntity);

}
