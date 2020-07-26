package ddp.tools;

import javax.servlet.http.HttpServletRequest;

public class MyPageUtils<T> {

  /**
   * 业务主体
   */
  private T doMain;

  /**
   * 用户请求对象
   */
  private HttpServletRequest request;

  public MyPageUtils(T doMain, HttpServletRequest request){
    this.doMain=doMain;
    this.request=request;
  }

  public T getDoMain() {
    return doMain;
  }

  public void setDoMain(T doMain) {
    this.doMain = doMain;
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }
}
