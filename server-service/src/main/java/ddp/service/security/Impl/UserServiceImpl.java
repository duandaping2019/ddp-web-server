package ddp.service.security.Impl;

import ddp.BaseServiceImpl;
import ddp.entity.security.SysUserEntity;
import ddp.mapper.security.SysUserMapper;
import ddp.service.security.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<SysUserEntity> implements SysUserService {
  @Autowired
  private SysUserMapper userMapper;


}
