package ddp;

import com.github.pagehelper.PageHelper;
import ddp.beans.common.MyPageHelper;
import ddp.beans.common.MyPageParamers;
import ddp.constants.CommConstants;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class BaseServiceImpl implements BaseService{
  @Override
  public void setPageInfo(MyPageHelper myPageHelper) {
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
