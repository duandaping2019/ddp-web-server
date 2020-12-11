package ddp.service.common;

public interface MailService {

    /**
     * 发送简单文本邮件
     * @param subject 主题
     * @param content 内容
     * @param toWho 需要发送的人
     * @param ccPeoples 需要抄送的人
     * @param bccPeoples 需要密送的人
     */
    void sendSimpleMailMessge(String subject, String content, String[] toWho, String[] ccPeoples, String[] bccPeoples);
    void sendSimpleMailMessge(String subject, String content, String[] toWho, String[] ccPeoples);
    void sendSimpleMailMessge(String subject, String content, String[] toWho);


    /**
     * 发送带附件文本邮件
     * @param subject 主题
     * @param content 内容
     * @param toWho 需要发送的人
     * @param ccPeoples 需要抄送的人
     * @param bccPeoples 需要密送的人
     * @param attachments 需要附带的附件
     */
    void sendAttachmentMailMessge(String subject, String content, String[] toWho, String[] ccPeoples, String[] bccPeoples, String[] attachments);
    void sendAttachmentMailMessge(String subject, String content, String[] toWho, String[] ccPeoples, String[] attachments);
    void sendAttachmentMailMessge(String subject, String content, String[] toWho, String[] attachments);

    /**
     * 发送Html文本邮件
     * @param subject 主题
     * @param content 内容
     * @param toWho 需要发送的人
     * @param ccPeoples 需要抄送的人
     * @param bccPeoples 需要密送的人
     */
    void sendHtmlMailMessge(String subject, String content, String[] toWho, String[] ccPeoples, String[] bccPeoples);
    void sendHtmlMailMessge(String subject, String content, String[] toWho, String[] ccPeoples);
    void sendHtmlMailMessge(String subject, String content, String[] toWho);

    /**
     * 发送Html文本且带附件邮件
     * @param subject 主题
     * @param content 内容
     * @param toWho 需要发送的人
     * @param ccPeoples 需要抄送的人
     * @param bccPeoples 需要密送的人
     * @param attachments 需要附带的附件
     */
    void sendHtmlAndFileMailMessge(String subject, String content, String[] toWho, String[] ccPeoples, String[] bccPeoples, String[] attachments);
    void sendHtmlAndFileMailMessge(String subject, String content, String[] toWho, String[] ccPeoples, String[] attachments);
    void sendHtmlAndFileMailMessge(String subject, String content, String[] toWho, String[] attachments);
}
