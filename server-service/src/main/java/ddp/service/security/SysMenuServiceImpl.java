package ddp.service.security;

import ddp.mapper.security.SysMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysMenuServiceImpl implements SysMenuService{
    @Autowired
    private SysMenuMapper sysMenuMapper;


}
