package ddp.service.security;

import ddp.ext.security.SysMenuExt;
import ddp.mapper.security.SysMenuMapper;
import ddp.service.tools.MessageSourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
public class SysMenuServiceImpl implements SysMenuService{
    @Autowired
    private SysMenuMapper sysMenuMapper;


    @Override
    public List<SysMenuExt> getExtListInfo(SysMenuExt ext) {
        return sysMenuMapper.getExtListInfo(ext);
    }

    @Override
    public List<SysMenuExt> getMenuTreeData() {
        List<SysMenuExt> menuList  = sysMenuMapper.getMenuTreeData();
        SysMenuExt root = new SysMenuExt();
        root.setMenuId(new BigDecimal(0));
        root.setMenuName(MessageSourceUtils.getSourceFromCache("menu_a", Locale.getDefault()));
        root.setParentId(new BigDecimal(-1));
        root.setOpen(true);
        menuList.add(root);

        return menuList;
    }

}