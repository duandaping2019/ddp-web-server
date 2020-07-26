package ddp.entity.security;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  系统用户表
 */
@Entity
@Table(name = "sys_user")
public class SysUserEntity implements Serializable {

  /**
   *  用户主键
   *  IDENTITY：主键由数据库自动生成（主要是自动增长型） AUTO：主键由程序控制。
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", length = 32)
  private Long userId;

  /**
   *  用户编号
   */
  @Column(name = "user_no", length = 16)
  private String userNo;

  /**
   *  用户姓名
   */
  @Column(name = "user_name", length = 128)
  private String userName;

  /**
   *  用户性别
   */
  @Column(name = "user_sex", length = 1)
  private Short userSex;

  /**
   *  登陆ID
   */
  @Column(name = "login_id", length = 128)
  private String loginId;

  /**
   *  登陆密码
   */
  @Column(name = "login_pwd", length = 128)
  private String loginPwd;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUserNo() {
    return userNo;
  }

  public void setUserNo(String userNo) {
    this.userNo = userNo;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Short getUserSex() {
    return userSex;
  }

  public void setUserSex(Short userSex) {
    this.userSex = userSex;
  }

  public String getLoginId() {
    return loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  public String getLoginPwd() {
    return loginPwd;
  }

  public void setLoginPwd(String loginPwd) {
    this.loginPwd = loginPwd;
  }
}
