package ddp.web.controller.common;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import ddp.beans.BaseResponse;
import ddp.constants.CommConstants;
import ddp.ext.security.SysUserExt;
import ddp.service.security.SysIndexService;
import ddp.service.tools.MessageSourceUtils;
import ddp.service.tools.ShiroUtils;
import ddp.tools.MyStringUtils;
import ddp.tools.RsaUtils;
import ddp.web.aop.OperLog;
import ddp.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 功能描述：登录相关
 */
@RestController("/")
public class SysLoginController extends BaseController {

    @Autowired
    private Producer producer;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private SysIndexService sysIndexService;

    @ApiOperation(value = "captcha", notes = "登录页验证码生成")
    @RequestMapping("captcha.jpg")
    @OperLog(operModul = "系统管理", operType = CommConstants.GET_DATA, operDesc = "验证码生成")
    public void captcha(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();
    }

    @ApiOperation(value = "sysLogin", notes = "系统登陆")
    @RequestMapping("sys/login")
    @OperLog(operModul = "系统管理", operType = CommConstants.GET_DATA, operDesc = "系统登陆")
    public BaseResponse<Object> sysLogin(@ApiParam(value = "用户请求参数", required = false) @RequestBody SysUserExt ext,
                                      @ApiParam(value = "语言请求参数", required = false) Locale locale,
                                      @ApiParam(value = "用户请求对象", required = false) HttpServletRequest request,
                                      @ApiParam(value = "用户响应对象", required = false) HttpServletResponse response) {

        if (ext.getCaptcha() != null && ext.getCaptcha().equals("POST_MAN_DDP")) { // PostMan测试模式
            System.out.println("this is postMan testing!!!");
        } else {
            // 校验码校验
            String captcha = (String) ShiroUtils.getSessionAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (!captcha.equalsIgnoreCase(ext.getCaptcha())) { // 忽略大写小的验证码比对
                return BaseResponse.badrequest(MessageSourceUtils.getSourceFromCache("login_fail_info", locale));
            }

        }

        // 语言环境设置
        localeResolver.setLocale(request, response, locale);

        try {
            // 登陆验证【账号/凭证】****这里的凭证是用户输入原始凭证，若前端加密这里需要有对应的解密过程支持
            UsernamePasswordToken token = new UsernamePasswordToken(ext.getLoginId(), MyStringUtils.getRsaDecodePwd(ext.getLoginPwd()));
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);

            // 写入登陆信息记录表
            sysIndexService.monitorshirosession(subject, "login");

        } catch (Exception e) {
            return BaseResponse.badrequest(MessageSourceUtils.getSourceFromCache("login_fail_info", locale));
        }

        // 执行到这里说明用户已登录成功
        return BaseResponse.success(MessageSourceUtils.getSourceFromCache("login_succ", locale));

    }

    @ApiOperation(value = "sysKickout", notes = "用户剔除操作校验")
    @RequestMapping("sys/kickout")
    public ModelAndView sysKickout(@ApiParam(value = "语言请求参数", required = false) Locale locale) {
        ModelAndView modelAndView = new ModelAndView("redirect:/login.html");
        modelAndView.addObject("kickoutInfo", MessageSourceUtils.getSourceFromCache("kickout", locale));
        return modelAndView;
    }


    @ApiOperation(value = "sysMenuUser", notes = "用户菜单权限加载")
    @RequestMapping("sys/menu/user")
    @OperLog(operModul = "系统首页", operType = CommConstants.GET_DATA, operDesc = "用户菜单权限加载")
    public Map<String, Object> sysMenuUser() {
        Map<String, Object> data = new HashMap<>();
        data.put("menuList", sysIndexService.selectMenuList(ShiroUtils.getCurrUserInfo())); //菜单
        data.put("permissions", sysIndexService.selectPermissions(ShiroUtils.getCurrUserInfo())); //权限
        return data;
    }

    @ApiOperation(value = "sysUserInfo", notes = "当前登录用户信息")
    @RequestMapping("sys/user/info")
    @OperLog(operModul = "系统首页", operType = CommConstants.GET_DATA, operDesc = "当前登录用户信息")
    public Map<String, Object> sysUserInfo() {
        Map<String, Object> data = new HashMap<>();
        data.put("user", ShiroUtils.getCurrUserInfo());
        return data;
    }

    @ApiOperation(value = "sysLogout", notes = "系统注销")
    @RequestMapping("sys/logout")
    @OperLog(operModul = "系统管理", operType = CommConstants.GET_DATA, operDesc = "系统注销")
    public ModelAndView sysLogout(@ApiParam(value = "语言请求参数", required = false) Locale locale) {
        if (ShiroUtils.isLogin()) {
            ShiroUtils.logout();
        }

        return new ModelAndView("redirect:/login.html");
    }

    @ApiOperation(value = "sysUserPassword", notes = "密码修改")
    @RequestMapping("sys/user/password")
    @OperLog(operModul = "系统管理", operType = CommConstants.UPDATE_DATA, operDesc = "密码修改")
    public BaseResponse<Object> sysUserPassword(@ApiParam(value = "用户请求参数", required = false) @RequestBody SysUserExt ext) {
        String result = sysIndexService.updateSysUserPassword(ext);
        return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", Locale.getDefault()), result);
    }

    @ApiOperation(value = "sysGetRsaPubKey", notes = "获取Rsa公钥")
    @RequestMapping("apis/rsa/getRsaPubKey")
    @OperLog(operModul = "系统管理", operType = CommConstants.GET_DATA, operDesc = "获取Rsa公钥")
    public BaseResponse<Object> sysGetRsaPubKey(@ApiParam(value = "语言请求参数", required = false) Locale locale) {
        String rsaPubKey = RsaUtils.getPublicKey();
        return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", Locale.getDefault()), rsaPubKey);
    }

}