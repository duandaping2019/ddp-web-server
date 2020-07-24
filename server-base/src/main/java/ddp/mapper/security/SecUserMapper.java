package ddp.mapper.security;

import ddp.BaseMapper;
import ddp.entity.security.SecUserEntity;
import ddp.ext.security.SecUserExt;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecUserMapper extends BaseMapper<SecUserEntity> {
  /*通过Ext检索数据*/
  List<SecUserExt> selectByExt(SecUserExt userExt);

}
