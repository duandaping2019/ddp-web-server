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
* @date 2020-08-31 16:47:52
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
    *类型【CRUD】
    */
    @Column(name = "log_type", length = 16)
    private String logType;

    /**
    *板块【B】
    */
    @Column(name = "log_model", length = 32)
    private String logModel;

    /**
    *操作人ID
    */
    @Column(name = "log_operator", length = 32)
    private String logOperator;

    /**
    *操作时间
    */
    @Column(name = "log_time", length = 19)
    private Date logTime;

    /**
    *记录内容
    */
    @Column(name = "log_content")
    private String logContent;


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
    * 设置类型【CRUD】
    */
    public String getLogType() {
        return this.logType;
    }

    /**
    * 获取类型【CRUD】
    */
    public void setLogType(String logType) {
        this.logType = logType;
    }

    /**
    * 设置板块【B】
    */
    public String getLogModel() {
        return this.logModel;
    }

    /**
    * 获取板块【B】
    */
    public void setLogModel(String logModel) {
        this.logModel = logModel;
    }

    /**
    * 设置操作人ID
    */
    public String getLogOperator() {
        return this.logOperator;
    }

    /**
    * 获取操作人ID
    */
    public void setLogOperator(String logOperator) {
        this.logOperator = logOperator;
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




}