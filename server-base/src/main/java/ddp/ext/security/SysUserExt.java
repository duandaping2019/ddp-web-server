package ddp.ext.security;


import ddp.entity.security.SysUserEntity;

import java.util.List;
import java.util.Map;

public class SysUserExt extends SysUserEntity {

    private String captcha; //验证码
    private List<String> roleIdList; //用户角色列表
    private List<Map<String ,Object>> roleList; //角色列表

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public List<String> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public List<Map<String, Object>> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Map<String, Object>> roleList) {
        this.roleList = roleList;
    }

}
