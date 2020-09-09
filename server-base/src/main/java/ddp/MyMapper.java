package ddp;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 *  TKmapper公共接口类
 *  提供常用单表操作方法
 */
@org.apache.ibatis.annotations.Mapper
public interface MyMapper<T> extends Mapper<T>, BaseMapper<T>, ConditionMapper<T>, IdsMapper<T>, InsertListMapper<T> {

}