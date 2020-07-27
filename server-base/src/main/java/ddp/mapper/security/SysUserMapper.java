package ddp.mapper.security;

import ddp.MyMapper;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends MyMapper<SysUserEntity> {
  /*通过Ext检索数据*/
  List<SysUserExt> selectByExt(SysUserExt userExt);

}
