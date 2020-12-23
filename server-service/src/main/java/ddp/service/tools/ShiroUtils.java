package ddp.service.tools;

import ddp.bean.RRException;
import ddp.constants.CommConstants;
import ddp.ext.security.SysUserExt;
import ddp.service.security.SysUserService;
import ddp.tools.SpringBeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 */
public class ShiroUtils {

    private ShiroUtils(){}

    /**
     * 获取用户会话对象
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 设置会话对象属性
     */
    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 获取会话对象属性
     */
    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    /**
     * 判断是否登陆
     */
    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    /**
     * 用户注销信息
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 获取当前用户信息
     */
    public static SysUserExt getCurrUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        SysUserExt ext = (SysUserExt) subject.getPrincipal();
        if (ext == null) {
            SysUserService userService = SpringBeanUtils.getBean(SysUserService.class);
            SysUserExt condition = new SysUserExt();
            condition.setUserId(CommConstants.ADMIN_USER);
            ext = userService.getEntityInfo(condition);
        }
        return ext;
    }

    /**
     * 获取验证码文本
     */
    public static String getKaptcha(String key) {
        Object kaptcha = getSessionAttribute(key);
        if (kaptcha == null) {
            throw new RRException("验证码已失效");
        }
        getSession().removeAttribute(key);
        return kaptcha.toString();
    }

}