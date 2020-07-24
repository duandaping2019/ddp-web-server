package ddp.service.security;

import com.github.pagehelper.PageInfo;
import ddp.BaseService;
import ddp.beans.common.MyPageHelper;
import ddp.entity.security.SecUserEntity;
import ddp.ext.security.SecUserExt;

/**
 * 用户管理服务
 */
public interface UserService extends BaseService {
  /*获取系统用户信息列表*/
  PageInfo<SecUserExt> getSecUserInfo(MyPageHelper<SecUserExt> myPageHelper);

  /*通过登陆ID获取用户信息*/
  SecUserEntity getUserByLoginId(String loginId);

}
