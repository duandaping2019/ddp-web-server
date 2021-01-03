package ddp.service.security;

import ddp.BaseServiceImpl;
import ddp.beans.CommConstants;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import ddp.ext.security.SysUserOnlineExt;
import ddp.mapper.security.SysUserMapper;
import ddp.mapper.security.SysUserOnlineMapper;
import ddp.service.filters.KickoutSessionControlFilter;
import ddp.service.tools.ShiroUtils;
import ddp.utils.MyStringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
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
  private SysUserOnlineMapper sysUserOnlineMapper;

  @Autowired
  private SessionManager sessionManager;

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

      // 刷新缓存信息
      forceUserOffLine(entity);

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

  /*强制用户下线重新登录获取最新信息*/
  public void forceUserOffLine(SysUserEntity entity) {
    SysUserOnlineExt condition = new SysUserOnlineExt();
    condition.setUserId(entity.getUserId());
    List<SysUserOnlineExt> list = this.getOnlineExtListInfo(condition);

    if (list != null && list.size() > 0 ) {
      for(SysUserOnlineExt ext : list){
        Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(ext.getSessionId()));
        if (kickoutSession != null) {
          //设置会话的kickout属性表示踢出了
          kickoutSession.setAttribute("kickout", true);

          KickoutSessionControlFilter filter = new KickoutSessionControlFilter();
          System.out.println("待实现内容、、、、、、、");

        }

      }
    }
  }

  @Override
  @Transactional
  public void delUserInfo(List<BigDecimal> idsList) {
    // 删除用户主数据
    userMapper.delUserInfo(idsList);
  }

  /**
   * 获取在线用户信息集合
   */
  @Override
  public List<SysUserOnlineExt> getOnlineExtListInfo(SysUserOnlineExt ext) {
    return sysUserOnlineMapper.getExtListInfo(ext);
  }


}
