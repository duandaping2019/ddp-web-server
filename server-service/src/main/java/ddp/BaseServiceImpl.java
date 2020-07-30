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
import tk.mybatis.mapper.entity.Example;

@SuppressWarnings("unchecked")
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T>{

  @Autowired(required = false)
  protected MyMapper<T> mapper;


  private Class<T> modelClass;    // 当前泛型真实类型的Class

  public BaseServiceImpl() {
    // 获得具体model，通过反射来根据属性条件查找数据
    ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
    modelClass = (Class<T>) pt.getActualTypeArguments()[0];
  }

  @Override
  public int addEntityInfo(T t) {
    return 0;
  }

  @Override
  public T getEntityInfo(Object ext){
    Example example = new Example(modelClass);
    Example.Criteria condition = example.createCriteria();
    condition.andEqualTo(ext);
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
