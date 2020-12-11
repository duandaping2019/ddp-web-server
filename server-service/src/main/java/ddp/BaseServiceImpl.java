package ddp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
  public int insertEntityInfo(T t) {
    return mapper.insert(t);
  }

  @Override
  public int insertEntityListInfo(List<T> list) {
    return mapper.insertList(list);
  }

}
