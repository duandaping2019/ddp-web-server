package ddp.mapper.security;

import ddp.MyMapper;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper extends MyMapper<SysUserEntity> {

    /*通过登陆ID获取用户信息【单表】*/
    SysUserExt findByLoginId(String loginId);

    /*获取登陆用户信息【联表】*/
    SysUserExt getLoginUserInfo(String loginId);

}
