package ddp.web;

import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import ddp.tools.RedisUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {
  /**
   * 缓存工具类
   */
  @Autowired
  private RedisUtils redisUtils;

  /**
   * 获取登陆用户信息
   */
  public SysUserExt getLoginUserInfo() {
    Subject subject = SecurityUtils.getSubject();
    SysUserEntity user = (SysUserEntity) subject.getPrincipal();
    SysUserExt sysUserExt = new SysUserExt();
    BeanUtils.copyProperties(user,sysUserExt);
    return sysUserExt;
  }


}
