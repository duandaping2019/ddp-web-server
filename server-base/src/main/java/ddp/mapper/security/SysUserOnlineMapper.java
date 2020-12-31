package ddp.mapper.security;

import ddp.MyMapper;
import ddp.entity.security.SysUserOnlineEntity;
import ddp.ext.security.SysUserOnlineExt;
import org.springframework.stereotype.Repository;

/**
* 描述：用户在线信息表模型
* @author Ddp
* @date 2020-12-29 18:24:09
*/
@Repository
public interface SysUserOnlineMapper extends MyMapper<SysUserOnlineEntity> {
    /*获取在线用户信息实体*/
    SysUserOnlineExt getExtInfo(SysUserOnlineExt condition);

    /*删除在线用户信息实体*/
    void deleteshirosession(SysUserOnlineExt condition);

}