package ddp.entity.security;

import ddp.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.math.BigDecimal;


/**
* 描述：用户在线信息表模型
* @author Ddp
* @date 2020-12-29 18:24:08
*/
@Entity
@Table(name = "sys_user_online")
public class SysUserOnlineEntity extends BaseEntity {
    /**
    *主键ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uo_id", length = 32)
    private String uoId;

    /**
    *会话标识ID
    */
    @Column(name = "session_id", length = 64)
    private String sessionId;

    /**
    *用户ID
    */
    @Column(name = "user_id", length = 19)
    private BigDecimal userId;

    /**
    *用户主机
    */
    @Column(name = "host", length = 32)
    private String host;

    /**
    *IP地址
    */
    @Column(name = "ip", length = 32)
    private String ip;

    /**
    *状态
    */
    @Column(name = "status", length = 10)
    private String status;

    /**
    *开始时间
    */
    @Column(name = "start_time", length = 19)
    private Date startTime;

    /**
    *最近访问时间
    */
    @Column(name = "last_access_time", length = 19)
    private Date lastAccessTime;


    /**
    * 设置主键ID
    */
    public String getUoId() {
        return this.uoId;
    }

    /**
    * 获取主键ID
    */
    public void setUoId(String uoId) {
        this.uoId = uoId;
    }

    /**
    * 设置会话标识ID
    */
    public String getSessionId() {
        return this.sessionId;
    }

    /**
    * 获取会话标识ID
    */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
    * 设置用户ID
    */
    public BigDecimal getUserId() {
        return this.userId;
    }

    /**
    * 获取用户ID
    */
    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    /**
    * 设置用户主机
    */
    public String getHost() {
        return this.host;
    }

    /**
    * 获取用户主机
    */
    public void setHost(String host) {
        this.host = host;
    }

    /**
    * 设置IP地址
    */
    public String getIp() {
        return this.ip;
    }

    /**
    * 获取IP地址
    */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
    * 设置状态
    */
    public String getStatus() {
        return this.status;
    }

    /**
    * 获取状态
    */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
    * 设置开始时间
    */
    public Date getStartTime() {
        return this.startTime;
    }

    /**
    * 获取开始时间
    */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
    * 设置最近访问时间
    */
    public Date getLastAccessTime() {
        return this.lastAccessTime;
    }

    /**
    * 获取最近访问时间
    */
    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }


}