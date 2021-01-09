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
* 描述：菜单表模型
* @author Ddp
* @date 2020-12-03 18:21:23
*/
@Entity
@Table(name = "sys_menu")
public class SysMenuEntity extends BaseEntity {
    /**
    *主键ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id", length = 32)
    private BigDecimal menuId;

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
    *父级主键
    */
    @Column(name = "parent_id", length = 19)
    private BigDecimal parentId;

    /**
    *菜单类型【0目录  1菜单  2按钮】
    */
    @Column(name = "menu_type", length = 1)
    private Integer menuType;

    /**
    *菜单编码
    */
    @Column(name = "menu_code", length = 16)
    private String menuCode;

    /**
    *菜单名称ZH
    */
    @Column(name = "menu_name", length = 32)
    private String menuName;

    /**
    *菜单名称EN
    */
    @Column(name = "menu_name_en", length = 32)
    private String menuNameEn;

    /**
    *带单权限标识符
    */
    @Column(name = "menu_permission", length = 100)
    private String menuPermission;

    /**
    *菜单排序
    */
    @Column(name = "menu_index", length = 19)
    private BigDecimal menuIndex;

    /**
    *菜单路径
    */
    @Column(name = "menu_url", length = 256)
    private String menuUrl;

    /**
    *菜单图标
    */
    @Column(name = "menu_icon", length = 64)
    private String menuIcon;


    public BigDecimal getMenuId() {
        return menuId;
    }

    public void setMenuId(BigDecimal menuId) {
        this.menuId = menuId;
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

    public BigDecimal getParentId() {
        return parentId;
    }

    public void setParentId(BigDecimal parentId) {
        this.parentId = parentId;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuNameEn() {
        return menuNameEn;
    }

    public void setMenuNameEn(String menuNameEn) {
        this.menuNameEn = menuNameEn;
    }

    public String getMenuPermission() {
        return menuPermission;
    }

    public void setMenuPermission(String menuPermission) {
        this.menuPermission = menuPermission;
    }

    public BigDecimal getMenuIndex() {
        return menuIndex;
    }

    public void setMenuIndex(BigDecimal menuIndex) {
        this.menuIndex = menuIndex;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

}