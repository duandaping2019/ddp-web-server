package ddp.mapper.security;

import ddp.MyMapper;
import ddp.entity.security.SysRoleEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
* 描述：系统角色表模型
* @author Ddp
* @date 2020-09-01 17:32:22
*/
@Repository
public interface SysRoleMapper extends MyMapper<SysRoleEntity> {

    /*获取超级管理员角色列表*/
    List<Map<String, Object>> querySuperRoleList();

    /*获取普通用户角色列表*/
    List<Map<String, Object>> queryUserRoleList(@Param("userId") BigDecimal userId);

}