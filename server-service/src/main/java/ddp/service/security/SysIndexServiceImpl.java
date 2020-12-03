package ddp.service.security;

import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysMenuExt;
import ddp.mapper.security.SysMenuMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysIndexServiceImpl implements SysIndexService{

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenuExt> selectMenuList(SysUserEntity userEntity) {
        return null;
    }

    @Override
    public Set<String> selectPermissions(SysUserEntity userEntity) {
        List<String> permsList;

        if(userEntity.getUserId() == 1){ //系统管理员，拥有最高权限
            permsList = sysMenuMapper.selectAllPerms();
        }else{
            permsList = sysMenuMapper.selectPointPerms(userEntity.getUserId());
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }
}
