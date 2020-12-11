package ddp.service.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailServiceImpl implements MailService{
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    //本身邮件的发送者，来自邮件配置
    @Value("${spring.mail.username}")
    private String userName;
    @Value("${spring.mail.nickname}")
    private String nickname;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendSimpleMailMessge(String subject, String content,
                                         String[] toWho, String[] ccPeoples, String[] bccPeoples){

        //创建一个简单邮件信息对象
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //设置邮件的基本信息
        handleBasicInfo(simpleMailMessage,subject,content,toWho,ccPeoples,bccPeoples);

        try {
            //发送邮件
            mailSender.send(simpleMailMessage);
            logger.info("发送邮件成功: 主题->{}",subject);
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常!", e);
        }
    }

    private void handleBasicInfo(SimpleMailMessage simpleMailMessage,String subject,String content,String[] toWho,String[] ccPeoples,String[] bccPeoples){
        //设置发件人
        simpleMailMessage.setFrom(nickname+'<'+userName+'>');
        //设置邮件的主题
        simpleMailMessage.setSubject(subject);
        //设置邮件的内容
        simpleMailMessage.setText(content);
        //设置邮件的收件人
        simpleMailMessage.setTo(toWho);
        //设置邮件的抄送人
        simpleMailMessage.setCc(ccPeoples);
        //设置邮件的密送人
        simpleMailMessage.setBcc(bccPeoples);
    }

    @Override
    public void sendSimpleMailMessge(String subject, String content, String[] toWho, String[] ccPeoples) {
        this.sendSimpleMailMessge(subject, content, toWho, ccPeoples, null);
    }

    @Override
    public void sendSimpleMailMessge(String subject, String content, String[] toWho) {
        this.sendSimpleMailMessge(subject, content, toWho, null, null);
    }

    @Override
    public void sendAttachmentMailMessge(String subject, String content, String[] toWho, String[] ccPeoples, String[] bccPeoples, String[] attachments) {
        try{
            // 处理MimeMessage类型信息
            MimeMessage mimeMessage = handleMimeMessage(subject, content, toWho, ccPeoples, bccPeoples, attachments , false);

            //发送该邮件
            mailSender.send(mimeMessage);
            logger.info("发送邮件成功: 主题->{}",subject);
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("发送邮件失败: 主题->{}",subject);

        }
    }

    private void handleAttachment(MimeMessageHelper mimeMessageHelper,String subject,String[] attachmentFilePaths) {
        //判断是否需要处理邮件的附件
        if(attachmentFilePaths != null&&attachmentFilePaths.length > 0) {
            //循环处理邮件的附件
            for (String attachmentFilePath : attachmentFilePaths) {
                //获取该路径所对应的文件资源对象
                FileSystemResource resource = new FileSystemResource(new File(attachmentFilePath));
                //判断该资源是否存在，当不存在时仅仅会打印一条警告日志，不会中断处理程序。
                // 也就是说在附件出现异常的情况下，邮件是可以正常发送的，所以请确定你发送的邮件附件在本机存在
                if (!resource.exists()) {
                    String info = "邮件->{"+ subject +"} 的附件->{"+ attachmentFilePath +"} 不存在！";
                    logger.warn(info);
                    throw new RuntimeException(info); // 抛出异常信息并中断程序
                }
                //获取资源的名称
                String fileName = resource.getFilename();
                try {
                    //添加附件
                    mimeMessageHelper.addAttachment(fileName, resource);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    logger.error("邮件->{} 添加附件->{} 出现异常->{}", subject, attachmentFilePath, e.getMessage());
                }
            }
        }
    }

    private boolean handleBasicInfo(MimeMessageHelper mimeMessageHelper, String subject, String content,
                                    String[] toWho, String[] ccPeoples, String[] bccPeoples, boolean isHtml){
        try{
            //设置发件人
            mimeMessageHelper.setFrom(nickname+'<'+userName+'>');
            //设置邮件的主题
            mimeMessageHelper.setSubject(subject);
            //设置邮件的内容，区别是否是HTML邮件
            mimeMessageHelper.setText(content,isHtml);
            //设置邮件的收件人
            mimeMessageHelper.setTo(toWho);
            //设置非必要的邮件元素，在使用helper进行封装时，这些数据都不能够为空
            if(ccPeoples != null) {
                //设置邮件的抄送人
                mimeMessageHelper.setCc(ccPeoples);
            }

            if(bccPeoples != null) {
                //设置邮件的密送人
                mimeMessageHelper.setBcc(bccPeoples);
            }

            return true;
        }catch(MessagingException e){
            e.printStackTrace();
            logger.error("邮件基本信息出错->{}",subject);
        }
        return false;
    }

    @Override
    public void sendAttachmentMailMessge(String subject, String content, String[] toWho, String[] ccPeoples, String[] attachments) {
        this.sendAttachmentMailMessge(subject, content, toWho, ccPeoples, null, attachments);
    }

    @Override
    public void sendAttachmentMailMessge(String subject, String content, String[] toWho, String[] attachments) {
        this.sendAttachmentMailMessge(subject, content, toWho, null, null, attachments);
    }

    @Override
    public void sendHtmlMailMessge(String subject, String content, String[] toWho, String[] ccPeoples, String[] bccPeoples) {
        try{
            // 处理MimeMessage类型信息
            MimeMessage mimeMessage = handleMimeMessage(subject, content, toWho, ccPeoples, bccPeoples, null, true);

            //发送该邮件
            mailSender.send(mimeMessage);
            logger.info("发送邮件成功: 主题->{}",subject);
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("发送邮件失败: 主题->{}",subject);

        }
    }

    @Override
    public void sendHtmlMailMessge(String subject, String content, String[] toWho, String[] ccPeoples) {
        this.sendHtmlMailMessge(subject, content, toWho, ccPeoples, null);
    }

    @Override
    public void sendHtmlMailMessge(String subject, String content, String[] toWho) {
        this.sendHtmlMailMessge(subject, content, toWho, null, null);
    }

    private MimeMessage handleMimeMessage(String subject, String content, String[] toWho,
                                          String[] ccPeoples, String[] bccPeoples,
                                          String[] attachments ,boolean isHtml) throws MessagingException{
        //附件处理需要进行二进制传输
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        //设置邮件的基本信息
        boolean continueProcess = handleBasicInfo(helper, subject, content, toWho, ccPeoples, bccPeoples, isHtml);
        //如果处理基本信息出现错误
        if(!continueProcess){
            throw new RuntimeException("邮件基本信息出错: 主题->{"+ subject +"}");
        }

        //处理附件
        if (attachments != null && attachments.length > 0) {
            handleAttachment(helper, subject, attachments);
        }

        return mimeMessage;
    }

    @Override
    public void sendHtmlAndFileMailMessge(String subject, String content, String[] toWho, String[] ccPeoples, String[] bccPeoples, String[] attachments) {
        try{
            // 处理MimeMessage类型信息
            MimeMessage mimeMessage = handleMimeMessage(subject, content, toWho, ccPeoples, bccPeoples, attachments,true);

            //发送该邮件
            mailSender.send(mimeMessage);
            logger.info("发送邮件成功: 主题->{}",subject);
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("发送邮件失败: 主题->{}",subject);

        }
    }

    @Override
    public void sendHtmlAndFileMailMessge(String subject, String content, String[] toWho, String[] ccPeoples, String[] attachments) {
        this.sendHtmlAndFileMailMessge(subject, content, toWho, ccPeoples, null, attachments);
    }

    @Override
    public void sendHtmlAndFileMailMessge(String subject, String content, String[] toWho, String[] attachments) {
        this.sendHtmlAndFileMailMessge(subject, content, toWho, null, null, attachments);
    }
}
