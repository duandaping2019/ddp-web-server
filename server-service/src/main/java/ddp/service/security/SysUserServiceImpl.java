package ddp.service.security;

import ddp.BaseServiceImpl;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import ddp.mapper.security.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserEntity> implements SysUserService {
  @Autowired
  private SysUserMapper userMapper;

  @Override
  public SysUserExt getExtInfo(SysUserExt ext) {
    return userMapper.getExtInfo(ext);
  }

  @Override
  public List<SysUserExt> getExtListInfo(SysUserExt ext) {
    return userMapper.getExtListInfo(ext);
  }

  @Override
  public Integer saveOrUpdate(SysUserExt ext) {
    return 1;
  }



}
