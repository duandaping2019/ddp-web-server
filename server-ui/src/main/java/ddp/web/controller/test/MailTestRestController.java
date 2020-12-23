package ddp.web.controller.test;

import ddp.beans.BaseResponse;
import ddp.service.common.MailService;
import ddp.service.tools.MessageSourceUtils;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/mail")
public class MailTestRestController {

   @Autowired
   private MailService mailService;

    /**
     * 发送简单文本邮件
     */
    @RequestMapping("/sendSimpleMailMessge")
    public BaseResponse<Object> sendSimpleMailMessge(@ApiParam(value = "语言请求参数", required = false) Locale locale){
        String subject = "来自FAMS的关怀";
        String content = "这是一个来自遥远的问候，请查收！！！";
        String[] toWho = new String[]{"16601054148@163.com"};
        mailService.sendSimpleMailMessge(subject, content, toWho);

        //返回结果
        return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), "发送简单文本邮件");
    }

    /**
     * 发送带附件文本邮件
     */
    @RequestMapping("/sendAttachmentMailMessge")
    public BaseResponse<Object> sendAttachmentMailMessge(@ApiParam(value = "语言请求参数", required = false) Locale locale){
        String subject = "来自FAMS的关怀";
        String content = "这是一个来自遥远的问候，请查收！！！";
        String[] toWho = new String[]{"16601054148@163.com"};
        String[] attachments = new String[]{"G:\\Temp\\files\\test-picture.jpg"};

        mailService.sendAttachmentMailMessge(subject, content, toWho, attachments);

        //返回结果
        return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), "发送带附件文本邮件");
    }

    /**
     * 发送Html文本邮件
     */
    @RequestMapping("/sendHtmlMailMessge")
    public BaseResponse<Object> sendHtmlMailMessge(@ApiParam(value = "语言请求参数", required = false) Locale locale){
        String subject = "来自FAMS的关怀";
        String content = "<h3 style = \"color: red\">这是一个来自遥远的问候，请查收！！！</h3>";
        String[] toWho = new String[]{"16601054148@163.com"};
        mailService.sendHtmlMailMessge(subject, content, toWho);

        //返回结果
        return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), "发送Html文本邮件");
    }

    /**
     * 发送Html文本且带附件邮件
     */
    @RequestMapping("/sendHtmlAndFileMailMessge")
    public BaseResponse<Object> sendHtmlAndFileMailMessge(@ApiParam(value = "语言请求参数", required = false) Locale locale){
        String subject = "来自FAMS的关怀";
        String content = "<h3 style = \"color: red\">这是一个来自遥远的问候，请查收！！！</h3>";
        String[] toWho = new String[]{"16601054148@163.com"};
        String[] attachments = new String[]{"G:\\Temp\\files\\test-picture.jpg"};
        mailService.sendHtmlAndFileMailMessge(subject, content, toWho, attachments);

        //返回结果
        return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), "发送Html文本且带附件邮件");
    }


}
