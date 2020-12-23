package ddp.service.security;

import ddp.BaseServiceImpl;
import ddp.constants.CommConstants;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import ddp.mapper.security.SysUserMapper;
import ddp.service.tools.ShiroUtils;
import ddp.tools.MyStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserEntity> implements SysUserService {
  @Autowired
  private SysUserMapper userMapper;

  @Autowired
  private SysRoleService roleService;

  @Override
  public SysUserExt getEntityInfo(SysUserExt ext) {
    return userMapper.getExtInfo(ext);
  }

  @Override
  public SysUserExt getExtInfo(SysUserExt ext) {
    // 获取用户主信息
    SysUserExt user = userMapper.getExtInfo(ext);

    // 获取用户角色信息[授权范围]
    user.setRoleList(roleService.sysRoleSelect(ShiroUtils.getCurrUserInfo().getUserId()));

    // 获取用户角色信息[拥有范围]
    List<String> roleIdList = new ArrayList<>();
    roleIdList.add("1");
    roleIdList.add("2");
    user.setRoleIdList(roleIdList);

    return user;
  }

  @Override
  public List<SysUserExt> getExtListInfo(SysUserExt ext) {
    return userMapper.getExtListInfo(ext);
  }

  @Override
  public SysUserEntity saveOrUpdate(SysUserExt ext) {
    // 获取当前用户信息
    SysUserExt operator = ShiroUtils.getCurrUserInfo();

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

  @Override
  public void delUserInfo(List<BigDecimal> idsList) {
    // 删除用户主数据
    userMapper.delUserInfo(idsList);
  }

}
