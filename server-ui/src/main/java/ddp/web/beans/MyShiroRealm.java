package ddp.web.beans;

import ddp.beans.CommConstants;
import ddp.ext.security.SysUserExt;
import ddp.service.security.SysIndexService;
import ddp.service.security.SysUserService;
import ddp.service.tools.ShiroUtils;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 实现自定义Realm
 * 完成用户认证和权限认证
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysIndexService sysIndexService;

    @Override
    public String getName() {
        return CommConstants.SHIRO_NAME;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        // 仅支持UsernamePasswordToken类型token
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 用户认证，只是在此处生成一个用户票据，principal
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {

        // 获取令牌中信息
        String userName = (String)token.getPrincipal(); // 多原则登陆入口【身份ID，手机号，邮箱】

        // 获取数据库信息【支持如上三种方式认证】
        SysUserExt condition = new SysUserExt();
        condition.setLoginId(userName);
        SysUserExt user = userService.getEntityInfo(condition);

        if (user != null) {
            if (!"0".equals(user.getUserState())) {//禁用账号
                throw new LockedAccountException();
            }

            // 关键凭证验证代码
            return new SimpleAuthenticationInfo(user.getLoginId(), user.getLoginPwd(), ByteSource.Util.bytes(CommConstants.SALT), this.getName());
        }

        // 非法账号登陆
        throw new UnknownAccountException();

    }

    /**
     * 角色权限和对应权限添加[菜单或按钮与用户多对多对应关系]
     * 控制器中标记为 @RequiresPermissions("sayHello")注解的方法，每次访问都会进行权限认证
     * 如果设置了redis等缓存，则doGetAuthorizationInfo只执行一次，除非手工clearCache
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取用户权限列表
        Set<String> permsSet = sysIndexService.selectPermissions(ShiroUtils.getCurrUserInfo());

        // 设置权限认证对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;

    }

}
