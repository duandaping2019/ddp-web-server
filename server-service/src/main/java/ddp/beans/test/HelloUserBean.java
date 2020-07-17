package ddp.beans.test;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HelloUserBean",description = "测试用户封装类")
public class HelloUserBean {
  @ApiModelProperty(value = "用户名",example = "张三")
  private String userName;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
