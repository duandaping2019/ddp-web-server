package ddp.service.security;

import ddp.ext.security.SysMenuExt;
import ddp.mapper.security.SysMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService{
    @Autowired
    private SysMenuMapper sysMenuMapper;


    @Override
    public List<SysMenuExt> getExtListInfo(SysMenuExt ext) {
        return sysMenuMapper.getExtListInfo(ext);
    }
}