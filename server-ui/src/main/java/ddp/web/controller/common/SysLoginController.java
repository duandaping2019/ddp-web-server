package ddp.web.controller.common;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import ddp.constants.CommConstants;
import ddp.web.aop.OperLog;
import ddp.web.tools.ShiroUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 功能描述：登录相关
 */
@Controller("/")
public class SysLoginController {

    @Autowired
    private Producer producer;

    @ApiOperation(value = "captcha", notes = "登录页验证码生成")
    @RequestMapping("captcha.jpg")
    @OperLog(operModul = "系统管理", operType = CommConstants.ADD_DATA, operDesc = "验证码生成")
    public void captcha(HttpServletResponse response)throws ServletException, IOException {
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



}
