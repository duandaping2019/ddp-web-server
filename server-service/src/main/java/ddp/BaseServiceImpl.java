package ddp;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@SuppressWarnings("unchecked")
@Component//事务依赖前提【被spring管理】
public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

  @Autowired(required = false)
  protected MyMapper<T> mapper;

  //////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////Transactional manager///////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public int addEntityInfo(T t) {
    return mapper.insert(t);
  }

  @Override
  public int addEntityInfoList(List<T> list) {
    return mapper.insertList(list);
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////Normol manager//////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public T getEntityInfo(Example example) {
    return mapper.selectOneByExample(example);
  }

  @Override
  public PageInfo<T> getEntityInfoList(Example example) {
    List<T> list = mapper.selectByExample(example);
    return new PageInfo<>(list);
  }

}
