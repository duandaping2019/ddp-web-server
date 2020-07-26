package ddp.beans;

public class MyPageParamers {

  /**
   * 页码数
   */
  private Integer pageNum = 1;

  /**
   * 每页条数
   */
  private Integer pageSize = 10;

  public Integer getPageNum() {
    return pageNum;
  }

  public void setPageNum(Integer pageNum) {
    this.pageNum = pageNum;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
}