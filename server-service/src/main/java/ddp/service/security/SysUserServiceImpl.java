package ddp.service.security;

import ddp.BaseServiceImpl;
import ddp.beans.CommConstants;
import ddp.dao.MySessionDAO;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import ddp.mapper.security.SysUserMapper;
import ddp.service.tools.ShiroUtils;
import ddp.utils.MyStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserEntity> implements SysUserService {
  @Autowired
  private SysUserMapper userMapper;

  @Autowired
  private MySessionDAO mySessionDAO;

  @Override
  public SysUserExt getEntityInfo(SysUserExt ext) {
    return userMapper.getExtInfo(ext);
  }

  @Override
  public SysUserExt getExtInfo(SysUserExt ext) {
    // 获取用户主信息
    SysUserExt user = userMapper.getExtInfo(ext);

    // 获取用户可配置角色信息[授权信息]
    user.setRoleList(this.userRoleSelect(ShiroUtils.getCurrUserInfo().getUserId()));

    // 获取用户拥有角色信息[被授予]
    user.setRoleIdList(userMapper.getRoleIdList(user.getUserId()));

    return user;
  }

  @Override
  public List<SysUserExt> getExtListInfo(SysUserExt ext) {
    // 获取当前用户信息
    ext.setCurrOperator(ShiroUtils.getCurrUserInfo());
    return userMapper.getExtListInfo(ext);
  }

  @Override
  public List<Map<String, Object>> userRoleSelect(BigDecimal currUserId) {
    return userMapper.getRoleInfoList(currUserId);
  }

  @Override
  @Transactional
  public SysUserEntity saveOrUpdate(SysUserExt ext) {
    // 获取当前用户信息
    SysUserExt operator = ShiroUtils.getCurrUserInfo();

    /**主表信息操作**/
    SysUserEntity entity = new SysUserEntity();
    BeanUtils.copyProperties(ext, entity);
    if (entity.getUserId() != null && !"".equals(entity.getUserId())) {
      // 用户信息设置
      entity.setUpdateUserId(operator.getUserId().toString());
      entity.setUpdateUserName(operator.getUserName());
      entity.setUpdateTime(new Date());

      // 用户信息更新
      userMapper.updateByPrimaryKeySelective(entity);

      //强制用户下线重新登录获取最新信息
      System.out.println(mySessionDAO);

    } else { // 新增操作
      entity.setCreateUserId(operator.getUserId().toString());
      entity.setCreateUserName(operator.getUserName());
      entity.setCreateTime(new Date());
      entity.setDelFlag(CommConstants.DEL_NORMAL_FLAG);

      // 初始化密码
      entity.setLoginPwd(MyStringUtils.getDefaultLoginPwd());

      userMapper.insertSelective(entity);
    }

    /**角色信息操作**/
    userMapper.delUserRoleInfo(entity.getUserId());
    userMapper.insertUserRoleInfo(entity.getUserId(), ext.getRoleIdList());

    return entity;
  }

  @Override
  @Transactional
  public void delUserInfo(List<BigDecimal> idsList) {
    // 删除用户主数据
    userMapper.delUserInfo(idsList);
  }



}
