package ddp.service.security;

import ddp.constants.CommConstants;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysMenuExt;
import ddp.ext.security.SysUserExt;
import ddp.mapper.security.SysMenuMapper;
import ddp.mapper.security.SysUserMapper;
import ddp.service.tools.MessageSourceUtils;
import ddp.service.tools.ShiroUtils;
import ddp.tools.MyStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class SysIndexServiceImpl implements SysIndexService{

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysMenuExt> selectMenuList(SysUserEntity userEntity) {
        //系统管理员，拥有最高权限
        if(userEntity.getUserId().compareTo(new BigDecimal("1")) == 0){
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

        if(userEntity.getUserId().compareTo(new BigDecimal("1")) == 0){ //系统管理员，拥有最高权限
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

    @Override
    @Transactional
    public String updateSysUserPassword(SysUserExt ext) {
        // 密码校验
        String oldPwdMd5 = MyStringUtils.getPointLoginPwd(ext.getPassword());
        SysUserExt currOperator = ShiroUtils.getCurrUserInfo();
        if (!currOperator.getLoginPwd().equals(oldPwdMd5)) {
            return MessageSourceUtils.getSourceFromCache("pwd_valid_fail", Locale.getDefault());
        }

        // 修改密码
        ext.setUserId(currOperator.getUserId());
        ext.setLoginPwd(MyStringUtils.getPointLoginPwd(ext.getNewPassword()));
        sysUserMapper.updateUserPassword(ext);

        // 删除该用户Shiro缓存
        ShiroUtils.logout();

        return MessageSourceUtils.getSourceFromCache("login_review", Locale.getDefault());
    }

}
