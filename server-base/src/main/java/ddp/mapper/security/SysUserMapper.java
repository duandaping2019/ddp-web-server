package ddp.mapper.security;

import ddp.MyMapper;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface SysUserMapper extends MyMapper<SysUserEntity> {

    /*获取用户实体信息*/
    SysUserExt getExtInfo(SysUserExt ext);

    /*获取用户集合信息*/
    List<SysUserExt> getExtListInfo(SysUserExt ext);

    /*删除用户信息*/
    void delUserInfo(@Param("idsList") List<BigDecimal> idsList);

    /*获取用户可配置角色信息*/
    List<Map<String, Object>> getRoleInfoList(@Param("userId") BigDecimal userId);

    /*获取用户拥有角色信息*/
    List<String> getRoleIdList(@Param("userId") BigDecimal userId);

    /*删除用户角色关联信息*/
    void delUserRoleInfo(@Param("userId") BigDecimal userId);

    /*插入用户角色关联信息*/
    void insertUserRoleInfo(@Param("userId") BigDecimal userId, @Param("roleIdList") List<String> roleIdList);

    /*密码更新*/
    void updateUserPassword(SysUserExt ext);

}
