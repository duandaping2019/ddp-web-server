package ddp.service.security;

import ddp.beans.CommConstants;
import ddp.entity.security.SysMenuEntity;
import ddp.ext.security.SysMenuExt;
import ddp.ext.security.SysUserExt;
import ddp.mapper.security.SysMenuMapper;
import ddp.service.tools.MessageSourceUtils;
import ddp.service.tools.ShiroUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class SysMenuServiceImpl implements SysMenuService{
    @Autowired
    private SysMenuMapper menuMapper;

    @Override
    public SysMenuExt getExtInfo(SysMenuExt ext) {
        SysMenuExt menu = menuMapper.getExtInfo(ext);
        return menu;
    }

    @Override
    public List<SysMenuExt> getExtListInfo(SysMenuExt ext) {
        return menuMapper.getExtListInfo(ext);
    }

    @Override
    public List<SysMenuExt> getMenuTreeData(SysMenuExt ext) {
        List<SysMenuExt> menuList  = menuMapper.getMenuTreeData(ext);
        SysMenuExt root = new SysMenuExt();
        root.setMenuId(new BigDecimal(0));
        root.setMenuName(MessageSourceUtils.getSourceFromCache("menu_root", Locale.getDefault()));
        root.setParentId(new BigDecimal(-1));
        root.setOpen(true);
        menuList.add(root);

        return menuList;
    }

    @Override
    @Transactional
    public SysMenuEntity saveOrUpdate(SysMenuExt ext) {
        // 获取当前菜单信息
        SysUserExt operator = ShiroUtils.getCurrUserInfo();

        /**主表信息操作**/
        SysMenuEntity entity = new SysMenuEntity();
        BeanUtils.copyProperties(ext, entity);
        if (entity.getMenuId() != null && !"".equals(entity.getMenuId())) {
            // 菜单信息设置
            entity.setUpdateUserId(operator.getUserId().toString());
            entity.setUpdateUserName(operator.getUserName());
            entity.setUpdateTime(new Date());

            // 菜单信息更新
            menuMapper.updateByPrimaryKeySelective(entity);

        } else { // 新增操作
            entity.setCreateUserId(operator.getUserId().toString());
            entity.setCreateUserName(operator.getUserName());
            entity.setCreateTime(new Date());
            entity.setDelFlag(CommConstants.DEL_NORMAL_FLAG);

            // 数据新增存储
            menuMapper.insertSelective(entity);
        }

        return entity;
    }

    @Override
    @Transactional
    public void delMenuInfo(List<BigDecimal> idsList) {
        // 删除菜单主数据
        menuMapper.delMenuInfo(idsList);
    }

    @Override
    public List<SysMenuExt> selectMenuPermissionList(SysUserExt currUserInfo) {
        return menuMapper.selectMenuPermissionList(currUserInfo.getUserId());
    }

}