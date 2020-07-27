package ddp;

import ddp.tools.MyPageUtils;

public interface BaseService<T extends BaseEntity> {

  /**
   * 根据主键进行查询
   */
  int addEntityInfo(T t);

  /**
   * 根据主键进行查询
   */
  T getEntityInfo(T t);

  /**
   * 设置分页信息
   */
  void setPageInfo(MyPageUtils myPageHelper);

}
