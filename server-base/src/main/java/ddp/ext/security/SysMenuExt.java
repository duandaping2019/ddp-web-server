package ddp.ext.security;

import ddp.entity.security.SysMenuEntity;

import java.util.List;

/**
* 描述：菜单表模型
* @author Ddp
* @date 2020-12-03 18:21:23
*/
public class SysMenuExt extends SysMenuEntity {

    private List<SysMenuExt> list;
    private String parentName;//父级菜单名称
    private Boolean open;//菜单是否展开

    public List<SysMenuExt> getList() {
        return list;
    }

    public void setList(List<SysMenuExt> list) {
        this.list = list;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

}
