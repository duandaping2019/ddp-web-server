package ddp.mapper.security;

import ddp.MyMapper;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SysUserMapper extends MyMapper<SysUserEntity> {

    /*获取用户实体信息*/
    SysUserExt getExtInfo(SysUserExt ext);

    /*获取用户分页信息*/
    List<SysUserExt> getExtListInfo(SysUserExt ext);

    /*删除用户信息*/
    void delUserInfo(@Param("idsList") List<BigDecimal> idsList);

}
