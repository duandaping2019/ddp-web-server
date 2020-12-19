package ddp.ext.security;

import ddp.entity.security.SysUserEntity;

import java.util.List;

public class SysUserExt extends SysUserEntity {

    private String captcha; //验证码
    private List<String> roleIdList; //用户角色列表

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

}
