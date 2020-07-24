package ddp.service.security.Impl;

import com.github.pagehelper.PageInfo;
import ddp.BaseServiceImpl;
import ddp.beans.common.MyPageHelper;
import ddp.entity.security.SecUserEntity;
import ddp.ext.security.SecUserExt;
import ddp.mapper.security.SecUserMapper;
import ddp.service.security.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {
  @Autowired
  private SecUserMapper userMapper;

  @Override
  public PageInfo<SecUserExt> getSecUserInfo(MyPageHelper<SecUserExt> myPageHelper) {
    setPageInfo(myPageHelper);
    List<SecUserExt> list = userMapper.selectByExt(myPageHelper.getDoMain());
    return new PageInfo<SecUserExt>(list);
  }

  @Override
  public SecUserEntity getUserByLoginId(String loginId) {
    Example example = new Example(SecUserEntity.class);
    Example.Criteria criteria = example.createCriteria();
    criteria.andEqualTo("loginId",loginId);
    return userMapper.selectOneByExample(example);
  }

}
