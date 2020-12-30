package ddp.service.security;

import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysMenuExt;
import ddp.ext.security.SysUserExt;
import org.apache.shiro.subject.Subject;

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

    /*用户密码修改*/
    String updateSysUserPassword(SysUserExt ext);

    /*记录在线用户登陆信息*/
    void recorderUserLoginInfo(Subject subject);

}
