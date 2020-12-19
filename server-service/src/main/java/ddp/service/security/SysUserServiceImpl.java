package ddp.service.security;

import ddp.BaseServiceImpl;
import ddp.constants.CommConstants;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import ddp.mapper.security.SysUserMapper;
import ddp.tools.MyStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
  public SysUserEntity saveOrUpdate(SysUserExt ext, SysUserExt operator) {
    SysUserEntity entity = new SysUserEntity();
    BeanUtils.copyProperties(ext, entity);
    if (entity.getUserId() != null && !"".equals(entity.getUserId())) {
      entity.setUpdateUserId(operator.getUserId().toString());
      entity.setUpdateUserName(operator.getUserName());
      entity.setUpdateTime(new Date());

      userMapper.updateByPrimaryKeySelective(entity);
    } else { // 新增操作
      entity.setCreateUserId(operator.getUserId().toString());
      entity.setCreateUserName(operator.getUserName());
      entity.setCreateTime(new Date());
      entity.setDelFlag(CommConstants.DEL_NORMAL_FLAG);

      // 初始化密码
      entity.setLoginPwd(MyStringUtils.getDefaultLoginPwd());

      userMapper.insertSelective(entity);
    }

    return entity;
  }



}
