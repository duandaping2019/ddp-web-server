package ddp.service.security.Impl;

import com.github.pagehelper.PageInfo;
import ddp.BaseServiceImpl;
import ddp.tools.MyPageUtils;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import ddp.mapper.security.SysUserMapper;
import ddp.service.security.SysUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl extends BaseServiceImpl implements SysUserService {
  @Autowired
  private SysUserMapper userMapper;

  @Override
  public PageInfo<SysUserExt> getSecUserInfo(MyPageUtils<SysUserExt> myPageHelper) {
    setPageInfo(myPageHelper);
    List<SysUserExt> list = userMapper.selectByExt(myPageHelper.getDoMain());
    return new PageInfo<SysUserExt>(list);
  }

  @Override
  public SysUserEntity getUserByLoginId(String loginId) {
    Example example = new Example(SysUserEntity.class);
    Example.Criteria criteria = example.createCriteria();
    criteria.andEqualTo("loginId",loginId);
    return userMapper.selectOneByExample(example);
  }

}
