package ddp.web.controller;

import ddp.ext.security.SysUserExt;
import ddp.service.security.SysUserService;
import ddp.tools.RedisUtils;
import ddp.service.tools.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {
  /**
   * 缓存工具类
   */
  @Autowired
  private RedisUtils redisUtils;

  /**
   *  用户服务类
   */
  @Autowired
  private SysUserService userService;

  /**
   * 获取登陆用户信息
   */
  public SysUserExt getLoginUserInfo() {
    return userService.getExtInfo(ShiroUtils.getCurrUserInfo());
  }


}
