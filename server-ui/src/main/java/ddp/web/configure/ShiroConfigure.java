package ddp.web.configure;

import ddp.constants.CommConstants;
import ddp.web.filters.MyPassThruAuthenticationFilter;
import ddp.web.security.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Apache Shiro安全验证框架
 * 代码说明：
 * -- 代码中引入了redis cache，用以缓存权限；
 * -- 代码中引入了redis session，session存放在redis；
 * -- lifecycleBeanPostProcessor，getDefaultAdvisorAutoProxyCreator，authorizationAttributeSourceAdvisor
 * 用以实现@RequiresPermissions("sayHello")，@RequiresRoles("sayHello") 注解方式进行权限控制；
 */
@Configuration
public class ShiroConfigure {

    @Autowired
    private RedisConfig redisConfig;

    /**
     * 将自己的验证方式加入容器
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 密码凭证匹配器，作为自定义认证的基础 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了）
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5"); // 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(CommConstants.HASH_ITERATIONS); // 散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    /**
     * websessionManager 采用redisSessionDao
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setSessionDAO(redisSessionDAO());
        return mySessionManager;
    }


    /**
     * redisSessionDao
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }


    /**
     * 配置shiro redisManager
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisConfig.getHost());
        redisManager.setPort(redisConfig.getPort());
        redisManager.setExpire(1800); // 配置缓存过期时间
        redisManager.setTimeout(redisConfig.getTimeout());
        redisManager.setPassword(redisConfig.getPassword());
        return redisManager;
    }


    /**
     * 权限管理，配置主要是Realm的管理认证
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myShiroRealm());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }


    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     *  * -- shrio中4种常见的权限
     *  * -- authc：所有已登陆用户可访问
     *  * -- roles：有指定角色的用户可访问
     *  * -- perms：有指定权限的用户可访问
     *  * -- anon：所有用户可访问，通常作为指定页面的静态资源时使用
     */
    @Bean
    @RequiresAuthentication
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filtersMap = new HashMap<>(16);
        MyPassThruAuthenticationFilter authFilter = new MyPassThruAuthenticationFilter();
        filtersMap.put("authc", authFilter);
        shiroFilterFactoryBean.setFilters(filtersMap);

        Map<String, String> filterChainDefinitionMap = new HashMap<>(16);
        filterChainDefinitionMap.put("/v2/api-docs", "anon"); // 表示可以匿名访问

        filterChainDefinitionMap.put("/user/logout", "logout"); //系统注销
        filterChainDefinitionMap.put("/**", "authc"); // 对所有用户认证

        shiroFilterFactoryBean.setLoginUrl("/user/login"); // 系统登陆
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }


    /**
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 解决注解不生效的问题
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }


    /**
     * 加入注解的使用，不加入这个注解不生效
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}