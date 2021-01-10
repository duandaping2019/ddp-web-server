package ddp.mapper.security;

import ddp.MyMapper;
import ddp.entity.security.SysMenuEntity;
import ddp.ext.security.SysMenuExt;
import org.apache.ibatis.annotations.Param;
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
    /*获取拥有目录菜单权限*/
    List<SysMenuExt> selectMenuList(@Param("userId") BigDecimal userId);

    /*获取用户权限*/
    List<String> selectPerms(@Param("userId") BigDecimal userId);

    /*获取菜单拓展实体*/
    SysMenuExt getExtInfo(SysMenuExt ext);

    /*获取菜单集合列表数据*/
    List<SysMenuExt> getExtListInfo(SysMenuExt ext);

    /*获取非按钮的菜单树集合信息*/
    List<SysMenuExt> getMenuTreeData(SysMenuExt ext);

    /*删除菜单信息*/
    void delMenuInfo(@Param("idsList") List<BigDecimal> idsList);

    /*授权菜单权限控制*/
    List<SysMenuExt> selectMenuPermissionList(@Param("userId") BigDecimal userId);




}