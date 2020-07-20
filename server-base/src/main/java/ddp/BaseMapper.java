package ddp;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
  /*通过Ext检索数据*/
//  List<T> selectByExt(@Param("entity") T t);


}
