package ddp.mapper.security;

import ddp.MyMapper;
import ddp.entity.security.SysRoleEntity;
import ddp.ext.security.SysRoleExt;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 描述：系统角色表模型
* @author Ddp
* @date 2020-09-01 17:32:22
*/
@Repository
public interface SysRoleMapper extends MyMapper<SysRoleEntity> {

    /*获取角色列表*/
    List<SysRoleExt> getExtListInfo(SysRoleExt ext);


}