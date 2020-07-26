package ddp;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 *  TKmapper公共接口类
 *  提供常用单表操作方法
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {}
