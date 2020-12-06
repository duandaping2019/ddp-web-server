package ddp.web.controller.common;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import ddp.constants.CommConstants;
import ddp.service.security.SysIndexService;
import ddp.web.aop.OperLog;
import ddp.web.controller.BaseController;
import ddp.web.tools.ShiroUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：登录相关
 */
@RestController("/")
public class SysLoginController extends BaseController {

    @Autowired
    private Producer producer;

    @Autowired
    private SysIndexService sysIndexService;


    @ApiOperation(value = "captcha", notes = "登录页验证码生成")
    @RequestMapping("captcha.jpg")
    @OperLog(operModul = "系统管理", operType = CommConstants.ADD_DATA, operDesc = "验证码生成")
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

    @ApiOperation(value = "sysUserList", notes = "获取用户信息列表")
    @RequestMapping("sys/user/list")
    @OperLog(operModul = "用户管理", operType = CommConstants.GET_DATA, operDesc = "获取用户信息列表")
    @RequiresPermissions("sys:user:list1")
    public List<Map<String, Object>> sysUserList(){
        System.out.println("123456");
        return null;
    }

}