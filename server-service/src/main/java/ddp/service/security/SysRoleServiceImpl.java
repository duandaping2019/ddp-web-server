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
    public List<Map<String, Object>> querySuperRoleList() {
        return roleMapper.querySuperRoleList();
    }

    @Override
    public List<Map<String, Object>> queryUserRoleList(BigDecimal userId) {
        return roleMapper.queryUserRoleList(userId);
    }
}
