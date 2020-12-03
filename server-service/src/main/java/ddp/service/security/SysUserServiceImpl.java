package ddp.service.security;

import ddp.BaseServiceImpl;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import ddp.mapper.security.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserEntity> implements SysUserService {
  @Autowired
  private SysUserMapper userMapper;


  @Override
  public SysUserExt findByLoginId(String loginId) {
    return userMapper.findByLoginId(loginId);
  }

  @Override
  public SysUserExt getLoginUserInfo(SysUserEntity user) {
    return userMapper.getLoginUserInfo(user.getLoginId());
  }


}
