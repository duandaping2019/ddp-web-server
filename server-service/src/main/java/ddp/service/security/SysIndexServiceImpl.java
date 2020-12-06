package ddp.service.security;

import ddp.constants.CommConstants;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysMenuExt;
import ddp.mapper.security.SysMenuMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysIndexServiceImpl implements SysIndexService{

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenuExt> selectMenuList(SysUserEntity userEntity) {
        //系统管理员，拥有最高权限
        if(userEntity.getUserId() == 1){
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<BigDecimal> menuIdList = sysMenuMapper.queryAllMenuId(userEntity.getUserId());
        return getAllMenuList(menuIdList);
    }

    private List<SysMenuExt> getAllMenuList(List<BigDecimal> menuIdList) {
        //查询根菜单列表
        List<SysMenuExt> menuList = queryListParentId(new BigDecimal(0), menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    private List<SysMenuExt> getMenuTreeList(List<SysMenuExt> menuList, List<BigDecimal> menuIdList) {
        List<SysMenuExt> subMenuList = new ArrayList<>();

        for(SysMenuExt entity : menuList){
            if(entity.getMenuType().equals(CommConstants.MenuType.CATALOG.getValue())){//目录
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }

    private List<SysMenuExt> queryListParentId(BigDecimal parentId, List<BigDecimal> menuIdList) {
        List<SysMenuExt> menuList = sysMenuMapper.queryListParentId(parentId);
        if(menuIdList == null){
            return menuList;
        }

        List<SysMenuExt> userMenuList = new ArrayList<>();
        for(SysMenuExt menu : menuList){
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }


    @Override
    public Set<String> selectPermissions(SysUserEntity userEntity) {
        List<String> permsList;

        if(userEntity.getUserId() == 1){ //系统管理员，拥有最高权限
            permsList = sysMenuMapper.selectAllPerms();
        }else{
            permsList = sysMenuMapper.selectPointPerms(userEntity.getUserId());
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }
}
