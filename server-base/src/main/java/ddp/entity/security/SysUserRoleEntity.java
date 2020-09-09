package ddp.entity.security;

import ddp.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;


/**
* 描述：系统用户角色关联表模型
* @author Ddp
* @date 2020-09-01 17:28:18
*/
@Entity
@Table(name = "sys_user_role")
public class SysUserRoleEntity extends BaseEntity {
    /**
    *主键ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ur_id", length = 19)
    private BigDecimal urId;

    /**
    *用户ID
    */
    @Column(name = "user_id", length = 19)
    private BigDecimal userId;

    /**
    *角色ID
    */
    @Column(name = "role_id", length = 19)
    private BigDecimal roleId;


    /**
    * 设置主键ID
    */
    public BigDecimal getUrId() {
        return this.urId;
    }

    /**
    * 获取主键ID
    */
    public void setUrId(BigDecimal urId) {
        this.urId = urId;
    }

    /**
    * 设置用户ID
    */
    public BigDecimal getUserId() {
        return this.userId;
    }

    /**
    * 获取用户ID
    */
    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    /**
    * 设置角色ID
    */
    public BigDecimal getRoleId() {
        return this.roleId;
    }

    /**
    * 获取角色ID
    */
    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }




}