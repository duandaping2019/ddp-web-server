package ddp.ext.security;

import ddp.entity.security.SysUserEntity;

public class SysUserExt extends SysUserEntity {

    private String captcha; //验证码

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
