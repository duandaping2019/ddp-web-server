package ddp.service.security;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 角色管理服务
 */
public interface SysRoleService {

    /*获取超级管理员角色列表*/
    List<Map<String, Object>> querySuperRoleList();

    /*获取普通用户角色列表*/
    List<Map<String, Object>> queryUserRoleList(BigDecimal userId);

}
