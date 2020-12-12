package ddp.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
    String pageNum = null;
    String pageSize = null;
    if (request.getMethod().equalsIgnoreCase("POST")){
      if (request.getContentType().equalsIgnoreCase("application/json")) {
        try {
          String jsonStr = HttpInfoUtils.getBodyString(request);
          JSONObject object = JSON.parseObject(jsonStr);
          pageNum = object.get(CommConstants.PAGE_NUM).toString();
          pageSize = object.get(CommConstants.PAGE_SIZE).toString();
        } catch (Exception e) {
          throw new RuntimeException("获取POST请求参数异常");
        }
      }
    } else {
      pageNum = request.getParameter(CommConstants.PAGE_NUM);
      pageSize = request.getParameter(CommConstants.PAGE_SIZE);
    }

    MyPageParamers pageParamers = new MyPageParamers();
    if (StringUtils.isNotEmpty(pageNum)) {
      pageParamers.setPageNum(Integer.parseInt(pageNum));
    }

    if (StringUtils.isNotEmpty(pageSize)) {
      pageParamers.setPageSize(Integer.parseInt(pageSize));
    }

    PageHelper.startPage(pageParamers.getPageNum(), pageParamers.getPageSize());
  }

}
