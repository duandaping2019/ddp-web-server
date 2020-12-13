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

    /**
    *父级主键
    */
    @Column(name = "parent_id", length = 19)
    private BigDecimal parentId;

    /**
    *菜单类型【0项目表示  1菜单  2按钮】
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


    /**
    * 设置主键ID
    */
    public BigDecimal getMenuId() {
        return this.menuId;
    }

    /**
    * 获取主键ID
    */
    public void setMenuId(BigDecimal menuId) {
        this.menuId = menuId;
    }

    /**
    * 设置父级主键
    */
    public BigDecimal getParentId() {
        return this.parentId;
    }

    /**
    * 获取父级主键
    */
    public void setParentId(BigDecimal parentId) {
        this.parentId = parentId;
    }

    /**
    * 设置菜单类型【0项目表示  1菜单  2按钮】
    */
    public Integer getMenuType() {
        return this.menuType;
    }

    /**
    * 获取菜单类型【0项目表示  1菜单  2按钮】
    */
    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    /**
    * 设置菜单编码
    */
    public String getMenuCode() {
        return this.menuCode;
    }

    /**
    * 获取菜单编码
    */
    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    /**
    * 设置菜单名称ZH
    */
    public String getMenuName() {
        return this.menuName;
    }

    /**
    * 获取菜单名称ZH
    */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
    * 设置菜单名称EN
    */
    public String getMenuNameEn() {
        return this.menuNameEn;
    }

    /**
    * 获取菜单名称EN
    */
    public void setMenuNameEn(String menuNameEn) {
        this.menuNameEn = menuNameEn;
    }

    /**
    * 设置带单权限标识符
    */
    public String getMenuPermission() {
        return this.menuPermission;
    }

    /**
    * 获取带单权限标识符
    */
    public void setMenuPermission(String menuPermission) {
        this.menuPermission = menuPermission;
    }

    /**
    * 设置菜单排序
    */
    public BigDecimal getMenuIndex() {
        return this.menuIndex;
    }

    /**
    * 获取菜单排序
    */
    public void setMenuIndex(BigDecimal menuIndex) {
        this.menuIndex = menuIndex;
    }

    /**
    * 设置菜单路径
    */
    public String getMenuUrl() {
        return this.menuUrl;
    }

    /**
    * 获取菜单路径
    */
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    /**
    * 设置菜单图标
    */
    public String getMenuIcon() {
        return this.menuIcon;
    }

    /**
    * 获取菜单图标
    */
    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }


}