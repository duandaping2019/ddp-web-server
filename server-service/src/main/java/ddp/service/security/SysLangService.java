package ddp.service.security;

import ddp.BaseService;
import ddp.entity.security.SysLangEntity;
import java.util.List;

/**
 * 国际化配置
 */
public interface SysLangService extends BaseService<SysLangEntity> {

  /**
   * 获取所有配置信息
   */
  List<SysLangEntity> findAllInfo();

}
