package ddp.service.security;

import ddp.mapper.security.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService{

    @Autowired
    private SysRoleMapper roleMapper;

    @Override
    public List<Map<String, Object>> sysRoleSelect(BigDecimal userId) {
        List<Map<String ,Object>> roleList = null;

        if (userId.compareTo(new BigDecimal("1")) == 0){ //超级管理员
            roleList = roleMapper.querySuperRoleList();
        } else { // 自己所拥有的角色列表
            roleList = roleMapper.queryUserRoleList(userId);
        }

        return roleList;
    }

}
