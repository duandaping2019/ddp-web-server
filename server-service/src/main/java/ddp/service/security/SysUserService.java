package ddp.service.security;

import com.github.pagehelper.PageInfo;
import ddp.BaseService;
import ddp.tools.MyPageUtils;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;

/**
 * 用户管理服务
 */
public interface SysUserService extends BaseService<SysUserEntity> {
  /*获取系统用户信息列表*/
  PageInfo<SysUserExt> getUserInfoList(MyPageUtils<SysUserExt> myPageHelper);

  /*通过登陆ID获取用户信息*/
  SysUserEntity getUserByLoginId(String loginId);

}
