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
* 描述：系统角色表模型
* @author Ddp
* @date 2020-09-01 17:32:22
*/
@Entity
@Table(name = "sys_role")
public class SysRoleEntity extends BaseEntity {
    /**
    *角色ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", length = 32)
    private BigDecimal roleId;

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
    *角色标识
    */
    @Column(name = "role_code", length = 32)
    private String roleCode;

    /**
    *角色名称
    */
    @Column(name = "role_name", length = 64)
    private String roleName;

    /**
     *角色名称
     */
    @Column(name = "role_desc", length = 256)
    private String roleDesc;

    public BigDecimal getRoleId() {
        return roleId;
    }

    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
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

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

}