package ddp.tools;

import com.github.pagehelper.PageHelper;
import ddp.beans.MyPageParamers;
import ddp.constants.CommConstants;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 *  分页工具类
 */
public class MyPageUtils {

  private MyPageUtils(){

  }

  /**
   * 设置分页
   */
  public static void setPageInfo(HttpServletRequest request) {
    MyPageParamers pageParamers = new MyPageParamers();
    String pageNum = request.getParameter(CommConstants.PAGE_NUM);
    if (StringUtils.isNotEmpty(pageNum)) {

      pageParamers.setPageNum(Integer.parseInt(pageNum));
    }

    String pageSize = request.getParameter(CommConstants.PAGE_SIZE);
    if (StringUtils.isNotEmpty(pageSize)) {
      pageParamers.setPageSize(Integer.parseInt(pageSize));
    }

    PageHelper.startPage(pageParamers.getPageNum(), pageParamers.getPageSize());
  }

}
