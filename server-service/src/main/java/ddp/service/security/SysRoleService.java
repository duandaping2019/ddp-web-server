package ddp.service.security;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 角色管理服务
 */
public interface SysRoleService {

    /*获取用户角色配置信息*/
    List<Map<String, Object>> sysRoleSelect(BigDecimal userId);

}
