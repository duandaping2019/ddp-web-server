package ddp;

import com.github.pagehelper.PageInfo;
import ddp.tools.MyPageUtils;
import tk.mybatis.mapper.entity.Example;

public interface BaseService<T extends BaseEntity> {

  /**
   * 根据主键进行查询
   */
  int addEntityInfo(T t);

  /**
   * 根据特定信息获取唯一实体
   */
  T getEntityInfo(Example example);

  /**
   * 根据特定信息获取实体集合【分页】
   */
  PageInfo<T> getEntityInfoList(MyPageUtils<T> myPageHelper);

  /**
   * 设置分页信息
   */
  void setPageInfo(MyPageUtils myPageHelper);

}
