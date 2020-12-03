package ddp.mapper.security;

import ddp.MyMapper;
import ddp.entity.security.SysMenuEntity;
import org.springframework.stereotype.Repository;

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
    List<String> selectPointPerms(Long userId);

}