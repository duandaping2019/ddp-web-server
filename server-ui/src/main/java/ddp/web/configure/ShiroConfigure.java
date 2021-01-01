package ddp.web.configure;

import ddp.constants.CommConstants;
import ddp.service.listeners.CustomShiroSessionListener;
import ddp.web.filters.KickoutSessionControlFilter;
import ddp.web.filters.MyPassThruAuthenticationFilter;
import ddp.web.security.MyCredentialsMatcher;
import ddp.web.security.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
        MyCredentialsMatcher credentialsMatcher = new MyCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(CommConstants.HASH_NAME); // 散列算法:这里使用MD5算法;
        credentialsMatcher.setHashIterations(CommConstants.HASH_ITERATIONS); // 散列的次数，相当于 md5(md5(""));
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    /**
     * sessionIdCookie的实现,用于重写覆盖容器默认的JSESSIONID
     */
    @Bean
    public SimpleCookie sessionIdCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName(CommConstants.SHIRO_SESSION_COOKIES); //cookie的name,对应的默认是 JSESSIONID
        simpleCookie.setMaxAge(CommConstants.SESSION_EXPIRETIME.intValue());//单位秒 -1 代表退出即删除
        simpleCookie.setPath("/");
        simpleCookie.setHttpOnly(true);//只支持http
        return simpleCookie;
    }


    /**
     * websessionManager 采用redisSessionDao
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());//自定义redis的sessionDao
        sessionManager.setGlobalSessionTimeout(CommConstants.SESSION_EXPIRETIME * 1000);//设置全局session超时时间 ms
        sessionManager.setCacheManager(cacheManager()); //使用redis缓存管理
        sessionManager.setSessionIdCookieEnabled(true);//启用自定义的SessionIdCookie
        sessionManager.setSessionIdCookie(sessionIdCookie());//自定义SessionIdCookie
        sessionManager.setSessionIdUrlRewritingEnabled(false);//关闭URL中带上JSESSIONID
        sessionManager.setSessionValidationSchedulerEnabled(true);//定时检查失效的session
        sessionManager.setDeleteInvalidSessions(true);//启用删除无效sessioin

        List<SessionListener> listeners = new ArrayList<>();
        listeners.add(new CustomShiroSessionListener());
        sessionManager.setSessionListeners(listeners); // 自定义Sesion 监听器

        return sessionManager;
    }


    /**
     * redisSessionDao
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setKeyPrefix(CommConstants.REDIS_SESSION_PREFIX);
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
        redisManager.setExpire(CommConstants.SESSION_EXPIRETIME.intValue()); // 配置缓存过期时间
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
        redisCacheManager.setKeyPrefix(CommConstants.REDIS_SESSION_PREFIX);
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

        /*基础链接限制条件*/
        shiroFilterFactoryBean.setSecurityManager(securityManager); //设置安全管理器
        shiroFilterFactoryBean.setLoginUrl("/login.html"); // 登陆页面

        /*拦截器设置*/
        Map<String, Filter> filtersMap = new LinkedHashMap<>(16);
        filtersMap.put("kickout", kickoutSessionControlFilter()); //限制同一帐号同时在线的个数
        filtersMap.put("authc", new MyPassThruAuthenticationFilter()); //【强制登陆访问限制】
        shiroFilterFactoryBean.setFilters(filtersMap);

        /*设置限制链接*/
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>(16); // LinkedHashMap 为关键点

        //第三方资源匿名配置
        filterChainDefinitionMap.put("/public/**", "anon"); //公共资源
        filterChainDefinitionMap.put("/webjars/**", "anon"); //静态资源
        filterChainDefinitionMap.put("/swagger**", "anon"); //swagger配置
        filterChainDefinitionMap.put("/v2/api-docs", "anon"); //swagger文档配置
        filterChainDefinitionMap.put("/swagger-resources/configuration/ui", "anon"); //swagger访问UI配置

        //系统内
        filterChainDefinitionMap.put("/sys/login", "anon"); //系统登陆
        filterChainDefinitionMap.put("/sys/logout", "anon"); //系统注销
        filterChainDefinitionMap.put("/sys/kickout", "anon"); //踢出用户
        filterChainDefinitionMap.put("/captcha.jpg", "anon"); //验证信息
        filterChainDefinitionMap.put("/apis/**", "anon"); //开放接口

        // 缺省配置
        filterChainDefinitionMap.put("/**", "authc,kickout"); // authc【用户认证】
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



    /**
     * 限制同一账号登录同时登录人数控制
     * **：注意顺序，必须放在shiroFilter下边要不报错
     * No SecurityManager accessible to the calling code
     */
    @Bean
    public KickoutSessionControlFilter kickoutSessionControlFilter() {
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        kickoutSessionControlFilter.setCacheManager(cacheManager());
        //用于根据会话ID，获取会话进行踢出操作的；
        kickoutSessionControlFilter.setSessionManager(sessionManager());
        //剔出顺序，true代表后来者不允许进入
        kickoutSessionControlFilter.setKickoutAfter(false);
        //同一个用户最大的会话数，默认1；
        kickoutSessionControlFilter.setMaxSession(1);
        //被踢出后重定向到的地址
        kickoutSessionControlFilter.setKickoutUrl("/sys/kickout");
        return kickoutSessionControlFilter;
    }

}
