package ddp.web.security;

import ddp.constants.CommConstants;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import ddp.service.security.SysIndexService;
import ddp.service.security.SysUserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 实现自定义Realm
 * 完成用户认证和权限认证
 */
public class MyShiroRealm extends AuthorizingRealm {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysIndexService sysIndexService;

    /**
     * 用户认证，只是在此处生成一个用户票据，principal
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        logger.info("---------------- 执行 Shiro 凭证认证 ----------------------");

        UsernamePasswordToken authcToken = (UsernamePasswordToken) token;

        SysUserExt condition = new SysUserExt();
        condition.setLoginId(authcToken.getUsername());
        SysUserExt user = userService.getExtInfo(condition);

        if (user != null) {
            if (!"0".equals(user.getUserState())) {// 用户为禁用状态
                throw new LockedAccountException();
            }

            logger.info("---------------- Shiro 凭证认证成功 ----------------------");
            return new SimpleAuthenticationInfo(user, user.getLoginPwd(), ByteSource.Util.bytes(CommConstants.SALT), this.getName());
        }

        throw new UnknownAccountException();

    }

    /**
     * 角色权限和对应权限添加[菜单或按钮与用户多对多对应关系]
     * 控制器中标记为 @RequiresPermissions("sayHello")注解的方法，每次访问都会进行权限认证
     * 如果设置了redis等缓存，则doGetAuthorizationInfo只执行一次，除非手工clearCache
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("---------------- 执行 Shiro 权限获取 ---------------------");
        SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
        Set<String> permsSet = sysIndexService.selectPermissions(user); ////用户权限列表

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet); //权限控制
        logger.info("---------------- Shiro 权限获取成功 ----------------------");
        return info;

    }

}
