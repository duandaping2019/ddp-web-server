package ddp.mapper.security;

import ddp.MyMapper;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper extends MyMapper<SysUserEntity> {

    /*通过登陆ID获取用户信息*/
    SysUserExt findByLoginId(String loginId);

}
