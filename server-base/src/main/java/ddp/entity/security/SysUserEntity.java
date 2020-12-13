package ddp.entity.security;

import ddp.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  系统用户表
 */
@Entity
@Table(name = "sys_user")
public class SysUserEntity extends BaseEntity {

  /**
   *  用户主键
   *  IDENTITY：主键由数据库自动生成（主要是自动增长型） AUTO：主键由程序控制。
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", length = 32)
  private BigDecimal userId;

  @Column(name = "create_user_id", length = 32)
  private String createUserId; //创建人Id

  @Column(name = "create_user_name", length = 128)
  private String createUserName; //创建人Name

  @Column(name = "create_time", length = 19)
  private Date createTime; //创建时间

  @Column(name = "update_user_id", length = 32)
  private String updateUserId; //更新人ID

  @Column(name = "update_user_name", length = 128)
  private String updateUserName; //更新人Name

  @Column(name = "update_time", length = 32)
  private Date updateTime; //更新时间

  @Column(name = "del_flag", length = 1)
  private Short delFlag; // 删除标记

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

  /**
   *  用户状态【0：在用 1：冻结】
   */
  @Column(name = "user_state", length = 1)
  private String userState;

  @Column(name = "email", length = 64)
  private String email; //邮箱

  @Column(name = "mobile", length = 16)
  private String mobile; //手机号

  public BigDecimal getUserId() {
    return userId;
  }

  public void setUserId(BigDecimal userId) {
    this.userId = userId;
  }

  public String getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }

  public String getCreateUserName() {
    return createUserName;
  }

  public void setCreateUserName(String createUserName) {
    this.createUserName = createUserName;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(String updateUserId) {
    this.updateUserId = updateUserId;
  }

  public String getUpdateUserName() {
    return updateUserName;
  }

  public void setUpdateUserName(String updateUserName) {
    this.updateUserName = updateUserName;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Short getDelFlag() {
    return delFlag;
  }

  public void setDelFlag(Short delFlag) {
    this.delFlag = delFlag;
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

  public String getUserState() {
    return userState;
  }

  public void setUserState(String userState) {
    this.userState = userState;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
}
