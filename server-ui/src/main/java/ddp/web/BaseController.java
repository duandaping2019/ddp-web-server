package ddp.web;

import ddp.tools.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {
  /**
   * 缓存工具类
   */
  @Autowired
  private RedisUtils redisUtils;

}
