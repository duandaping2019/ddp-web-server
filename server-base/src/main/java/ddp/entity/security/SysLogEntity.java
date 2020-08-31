package ddp.entity.security;

import ddp.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
* 描述：系统日志表模型
* @author Ddp
* @date 2020-08-31 19:10:29
*/
@Entity
@Table(name = "sys_log")
public class SysLogEntity extends BaseEntity {
    /**
    *主键ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", length = 32)
    private String logId;

    /**
    *操作模块
    */
    @Column(name = "log_model", length = 32)
    private String logModel;

    /**
    *操作类型
    */
    @Column(name = "log_type", length = 16)
    private String logType;

    /**
    *记录内容
    */
    @Column(name = "log_content")
    private String logContent;

    /**
    *请求URI
    */
    @Column(name = "log_req_uri", length = 32)
    private String logReqUri;

    /**
    *请求类名
    */
    @Column(name = "log_req_class", length = 32)
    private String logReqClass;

    /**
    *请求方法名
    */
    @Column(name = "log_req_method", length = 32)
    private String logReqMethod;

    /**
    *请求参数
    */
    @Column(name = "log_req_params", length = 32)
    private String logReqParams;

    /**
    *操作人IP
    */
    @Column(name = "log_ip", length = 32)
    private String logIp;

    /**
    *操作人ID
    */
    @Column(name = "log_operator_id", length = 32)
    private String logOperatorId;

    /**
    *操作人Name
    */
    @Column(name = "log_operator_name", length = 32)
    private String logOperatorName;

    /**
    *操作时间
    */
    @Column(name = "log_time", length = 19)
    private Date logTime;


    /**
    * 设置主键ID
    */
    public String getLogId() {
        return this.logId;
    }

    /**
    * 获取主键ID
    */
    public void setLogId(String logId) {
        this.logId = logId;
    }

    /**
    * 设置操作模块
    */
    public String getLogModel() {
        return this.logModel;
    }

    /**
    * 获取操作模块
    */
    public void setLogModel(String logModel) {
        this.logModel = logModel;
    }

    /**
    * 设置操作类型
    */
    public String getLogType() {
        return this.logType;
    }

    /**
    * 获取操作类型
    */
    public void setLogType(String logType) {
        this.logType = logType;
    }

    /**
    * 设置记录内容
    */
    public String getLogContent() {
        return this.logContent;
    }

    /**
    * 获取记录内容
    */
    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    /**
    * 设置请求URI
    */
    public String getLogReqUri() {
        return this.logReqUri;
    }

    /**
    * 获取请求URI
    */
    public void setLogReqUri(String logReqUri) {
        this.logReqUri = logReqUri;
    }

    /**
    * 设置请求类名
    */
    public String getLogReqClass() {
        return this.logReqClass;
    }

    /**
    * 获取请求类名
    */
    public void setLogReqClass(String logReqClass) {
        this.logReqClass = logReqClass;
    }

    /**
    * 设置请求方法名
    */
    public String getLogReqMethod() {
        return this.logReqMethod;
    }

    /**
    * 获取请求方法名
    */
    public void setLogReqMethod(String logReqMethod) {
        this.logReqMethod = logReqMethod;
    }

    /**
    * 设置请求参数
    */
    public String getLogReqParams() {
        return this.logReqParams;
    }

    /**
    * 获取请求参数
    */
    public void setLogReqParams(String logReqParams) {
        this.logReqParams = logReqParams;
    }

    /**
    * 设置操作人IP
    */
    public String getLogIp() {
        return this.logIp;
    }

    /**
    * 获取操作人IP
    */
    public void setLogIp(String logIp) {
        this.logIp = logIp;
    }

    /**
    * 设置操作人ID
    */
    public String getLogOperatorId() {
        return this.logOperatorId;
    }

    /**
    * 获取操作人ID
    */
    public void setLogOperatorId(String logOperatorId) {
        this.logOperatorId = logOperatorId;
    }

    /**
    * 设置操作人Name
    */
    public String getLogOperatorName() {
        return this.logOperatorName;
    }

    /**
    * 获取操作人Name
    */
    public void setLogOperatorName(String logOperatorName) {
        this.logOperatorName = logOperatorName;
    }

    /**
    * 设置操作时间
    */
    public Date getLogTime() {
        return this.logTime;
    }

    /**
    * 获取操作时间
    */
    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }




}