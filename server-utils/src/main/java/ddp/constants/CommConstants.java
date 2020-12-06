package ddp.constants;

public class CommConstants {

  private CommConstants() {
  }

  /*超级管理员ID*/
  public static final String ADMIN_USER = "ddp"; //超级管理员ID

  /*MD5盐值加密*/
  public static final String LOGIN_PWD = "123456"; //默认密码
  public static final String SALT = "7e6f"; //盐值
  public static final Integer HASH_ITERATIONS = 1024;

  /*分页参数*/
  public static final String PAGE_NUM = "pageNum"; //页码数
  public static final String PAGE_SIZE = "pageSize"; //每页条数

  /*数据操作类*/
  public static final String GET_DATA = "GET_DATA"; //获取数据
  public static final String DEL_DATA = "DEL_DATA"; //删除数据
  public static final String ADD_DATA = "ADD_DATA"; //新增数据
  public static final String UPDATE_DATA = "UPDATE_DATA"; //更新数据

  /*菜单类型*/
  public enum MenuType {
    CATALOG(0), //目录
    MENU(1), //菜单
    BUTTON(2); //按钮

    private Integer value;

    private MenuType(Integer value) {
      this.value = value;
    }
    public Integer getValue() {
      return value;
    }
  }

}
