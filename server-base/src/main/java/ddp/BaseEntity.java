package ddp;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 通用Entity定义
 * 用于免强制转换
 */
public class BaseEntity implements Serializable {
    @Transient
    private String appCode;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
}
