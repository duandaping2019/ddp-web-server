package ddp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ddp.beans.MyPageParamers;
import ddp.constants.CommConstants;
import ddp.tools.MyPageUtils;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

@SuppressWarnings("unchecked")
@Component//事务依赖前提【被spring管理】
public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T>{

  @Autowired(required = false)
  protected MyMapper<T> mapper;

  // 当前泛型真实类型的Class
  private Class<T> modelClass;

  public BaseServiceImpl() {
    // 获得具体model，通过反射来根据属性条件查找数据
    ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
    modelClass = (Class<T>) pt.getActualTypeArguments()[0];
  }

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
  public T getEntityInfo(Example example){
    return mapper.selectOneByExample(example);
  }

  @Override
  public PageInfo<T> getEntityInfoList(MyPageUtils<T> myPageHelper) {
    setPageInfo(myPageHelper);//分页设置
    Example example = new Example(modelClass);
    Example.Criteria condition = example.createCriteria();
    condition.andEqualTo(myPageHelper.getDoMain());
    List<T> list = mapper.selectByExample(example);
    return new PageInfo<T>(list);
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////Helper start////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public void setPageInfo(MyPageUtils myPageHelper) {
    HttpServletRequest request = myPageHelper.getRequest();
    MyPageParamers pageParamers = new MyPageParamers();
    String pageNum = request.getParameter(CommConstants.PAGE_NUM);
    if(StringUtils.isNotEmpty(pageNum)){
      pageParamers.setPageNum(new Integer(pageNum));
    }

    String pageSize = request.getParameter(CommConstants.PAGE_SIZE);
    if(StringUtils.isNotEmpty(pageSize)){
      pageParamers.setPageSize(new Integer(pageSize));
    }

    PageHelper.startPage(pageParamers.getPageNum(),pageParamers.getPageSize());
  }

}
