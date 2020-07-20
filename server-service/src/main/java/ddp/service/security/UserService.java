package ddp.service.security;

import com.github.pagehelper.PageInfo;
import ddp.BaseService;
import ddp.beans.common.MyPageHelper;
import ddp.ext.security.SecUserExt;

public interface UserService extends BaseService {
  /*获取系统用户信息列表*/
  PageInfo<SecUserExt> getSecUserInfo(MyPageHelper<SecUserExt> myPageHelper);

}
