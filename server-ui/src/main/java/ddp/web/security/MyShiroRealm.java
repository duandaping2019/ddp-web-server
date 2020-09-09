package ddp.web.security;

import ddp.entity.security.SysUserEntity;
import ddp.service.security.SysUserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

/**
 * 实现自定义Realm
 * 完成用户认证和权限认证
 */
public class MyShiroRealm extends AuthorizingRealm {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private SysUserService userService;

    /**
     * 用户认证
     * 可在此处调用loginservice实现login逻辑；
     * 也可以在其它地方login验证完后，只是在此处生成一个用户票据，principal
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        logger.info("---------------- 执行 Shiro 凭证认证 ----------------------");

        UsernamePasswordToken authcToken = (UsernamePasswordToken) token;

        //封装条件
        Example example = new Example(SysUserEntity.class);
        example.createCriteria().andEqualTo("loginId", authcToken.getUsername()).andEqualTo("loginPwd", String.valueOf(authcToken.getPassword()));

        //执行查询
        SysUserEntity user = userService.getEntityInfo(example);


        if (user != null) {
            // 用户为禁用状态
            if (!"0".equals(user.getUserState())) {
                throw new DisabledAccountException();
            }

            logger.info("---------------- Shiro 凭证认证成功 ----------------------");
            return new SimpleAuthenticationInfo(
                    user, //用户
                    user.getLoginPwd(), //密码
                    getName()  //realm name
            );
        }

        throw new UnknownAccountException();

    }


    /**
     * 角色权限和对应权限添加
     * 对ShiroFilterFactoryBean中标记为 filterMap.put("/**","perms")
     * 或控制器中标记为 @RequiresPermissions("sayHello")，@RequiresRoles("sayHello") 注解的方法，每次访问都会进行权限认证
     * 如果设置了redis等缓存，则doGetAuthorizationInfo只执行一次，除非手工clearCache
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }


}
