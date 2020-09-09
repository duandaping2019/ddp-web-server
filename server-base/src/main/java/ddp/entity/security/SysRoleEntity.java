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
    @Column(name = "role_id", length = 19)
    private BigDecimal roleId;

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

    /**
    * 设置角色标识
    */
    public String getRoleCode() {
        return this.roleCode;
    }

    /**
    * 获取角色标识
    */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    /**
    * 设置角色名称
    */
    public String getRoleName() {
        return this.roleName;
    }

    /**
    * 获取角色名称
    */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }




}