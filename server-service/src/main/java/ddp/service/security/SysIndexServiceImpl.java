package ddp.service.security;

import ddp.beans.CommConstants;
import ddp.entity.security.SysUserEntity;
import ddp.entity.security.SysUserOnlineEntity;
import ddp.ext.security.SysMenuExt;
import ddp.ext.security.SysUserExt;
import ddp.ext.security.SysUserOnlineExt;
import ddp.mapper.security.SysMenuMapper;
import ddp.mapper.security.SysUserMapper;
import ddp.mapper.security.SysUserOnlineMapper;
import ddp.service.tools.MessageSourceUtils;
import ddp.service.tools.ShiroUtils;
import ddp.utils.MyStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    @Autowired
    private SysUserOnlineMapper sysUserOnlineMapper;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public List<SysMenuExt> selectMenuList(SysUserEntity userEntity) {
        // 获取权限菜单数据
        List<SysMenuExt> list = sysMenuMapper.selectMenuList(userEntity.getUserId());

        // 结果数据组装，返回前端所需数据
        List<SysMenuExt> cataList = new ArrayList<>();
        reCalledDataOfList(cataList, list);
        return cataList;
    }

    private void reCalledDataOfList(List<SysMenuExt> cataList, List<SysMenuExt> list) {

        List<SysMenuExt> menuList = new ArrayList<>();

        for (SysMenuExt ext: list) {
            if (ext.getMenuType().equals(CommConstants.MenuType.CATALOG.getValue())) {
                cataList.add(ext);
            } else if (ext.getMenuType().equals(CommConstants.MenuType.MENU.getValue())) {
                menuList.add(ext);
            }
        }

        for (SysMenuExt cata: cataList) {
            List<SysMenuExt> subCata = new ArrayList<>();
            for (SysMenuExt menu: menuList) {
                if (menu.getParentId().compareTo(cata.getMenuId()) == 0) {
                    subCata.add(menu);
                }
            }
            cata.setList(subCata);
        }
    }


    @Override
    public Set<String> selectPermissions(SysUserEntity userEntity) {
        List<String> permsList = sysMenuMapper.selectPerms(userEntity.getUserId());

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

    @Override
    @Transactional
    public synchronized void monitorshirosession(Subject subject, String fromRequest) {
        // 获取在线用户信息
        SysUserOnlineExt condition = new SysUserOnlineExt();
        condition.setSessionId(subject.getSession().getId().toString());
        SysUserOnlineExt userOnlineExt = sysUserOnlineMapper.getExtInfo(condition);
        if (userOnlineExt == null && "login".equals(fromRequest)){
            SysUserOnlineEntity entity = new SysUserOnlineEntity();
            entity.setUoId(MyStringUtils.getUUID()); // 主键ID
            entity.setSessionId(subject.getSession().getId().toString()); //会话标识ID
            entity.setUserId(ShiroUtils.getCurrUserInfo().getUserId()); //用户ID
            entity.setHost(subject.getSession().getHost());// 用户主机
            entity.setStartTime(new Date()); //开始时间
            sysUserOnlineMapper.insert(entity);

        } else if (userOnlineExt != null) {
            userOnlineExt.setLastAccessTime(new Date());
            sysUserOnlineMapper.updateByPrimaryKeySelective(userOnlineExt);

        }

    }

    @Override
    @Transactional
    public void deleteshirosession(String sessionId) {
        SysUserOnlineExt condition = new SysUserOnlineExt();
        condition.setSessionId(sessionId);
        sysUserOnlineMapper.deleteshirosession(condition);
    }

    @Override
    @Transactional
    public void truncateshirosession() {
        sysUserOnlineMapper.truncateshirosession();
    }

    @Override
    public void logoutSystem() {
        String key = ShiroUtils.getCurrUserInfo().getLoginId();
        cacheManager.getCache(CommConstants.REDIS_SESSION_PREFIX).remove(key);
        ShiroUtils.logout();
    }


}
