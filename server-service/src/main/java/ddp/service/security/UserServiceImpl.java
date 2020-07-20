package ddp.service.security;

import com.github.pagehelper.PageInfo;
import ddp.BaseServiceImpl;
import ddp.beans.common.MyPageHelper;
import ddp.ext.security.SecUserExt;
import ddp.mapper.security.SecUserMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService{
  @Autowired
  private SecUserMapper userMapper;

  @Override
  public PageInfo<SecUserExt> getSecUserInfo(MyPageHelper<SecUserExt> myPageHelper) {
    setPageInfo(myPageHelper);
    List<SecUserExt> list = userMapper.selectByExt(myPageHelper.getDoMain());
    return new PageInfo<SecUserExt>(list);
  }


}
