package ddp;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 通用Entity定义
 * 用于免强制转换
 */
public class BaseEntity implements Serializable {
    @Transient
    private String appCode; //系统标识

    @Transient
    private String orderColumn; //排序字段【自定义】

    @Transient
    private String orderRule; //排序规则【asc/desc】

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public String getOrderRule() {
        return orderRule;
    }

    public void setOrderRule(String orderRule) {
        this.orderRule = orderRule;
    }
}
