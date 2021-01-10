package ddp.service.security;

import ddp.ext.security.SysRoleExt;
import ddp.mapper.security.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService{

    @Autowired
    private SysRoleMapper roleMapper;

    @Override
    public List<SysRoleExt> getExtListInfo(SysRoleExt ext) {
        return roleMapper.getExtListInfo(ext);
    }


}
