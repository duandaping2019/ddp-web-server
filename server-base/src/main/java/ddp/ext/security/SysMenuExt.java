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

    public List<SysMenuExt> getList() {
        return list;
    }

    public void setList(List<SysMenuExt> list) {
        this.list = list;
    }
}
