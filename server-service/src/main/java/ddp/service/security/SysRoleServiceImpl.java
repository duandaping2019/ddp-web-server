package ddp.service.security;

import ddp.mapper.security.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl implements SysRoleService{

    @Autowired
    private SysRoleMapper roleMapper;

}
