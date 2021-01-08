package ddp.mapper.security;

import ddp.MyMapper;
import ddp.entity.security.SysMenuEntity;
import ddp.ext.security.SysMenuExt;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
* 描述：菜单表模型
* @author Ddp
* @date 2020-12-03 18:21:23
*/
@Repository
public interface SysMenuMapper extends MyMapper<SysMenuEntity> {
    /*获取所有权限*/
    List<String> selectAllPerms();

    /*获取指定用户权限*/
    List<String> selectPointPerms(BigDecimal userId);

    /*获取用户菜单*/
    List<BigDecimal> queryAllMenuId(BigDecimal userId);

    /*通过父级Id获取子菜单*/
    List<SysMenuExt> queryListParentId(BigDecimal parentId);

    /*获取菜单集合列表数据*/
    List<SysMenuExt> getExtListInfo(SysMenuExt ext);

    /*获取非按钮的菜单树集合信息*/
    List<SysMenuExt> getMenuTreeData();

}