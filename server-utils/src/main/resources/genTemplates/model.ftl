package ${package_name};

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
* 描述：${table_remark}模型
* @author ${author}
* @date ${date}
*/
@Entity
@Table(name = "${table_name}")
public class ${class_name} extends BaseEntity {
<#if model_column?exists>
<#list model_column as model>
    /**
    *${model.columnComment!}
    */
    <#if model_index == 0>
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    <#if (model.columnType = 'varchar' || model.columnType = 'char')>
    @Column(name = "${model.columnName}", length = ${model.columnLen})
    private String ${model.changeColumnName?uncap_first};
    </#if>
    <#if (model.columnType = 'text')>
    @Column(name = "${model.columnName}")
    private String ${model.changeColumnName?uncap_first};
    </#if>
    <#if (model.columnType = 'bigint')>
    @Column(name = "${model.columnName}", length = ${model.columnLen})
    private BigDecimal ${model.changeColumnName?uncap_first};
    </#if>
    <#if model.columnType = 'timestamp' || model.columnType = 'datetime'>
    @Column(name = "${model.columnName}", length = ${model.columnLen})
    private Date ${model.changeColumnName?uncap_first};
    </#if>

</#list>
</#if>

<#if model_column?exists>
<#list model_column as model>
    <#if (model.columnType = 'varchar' || model.columnType = 'text' || model.columnType = 'char')>
    /**
    * 设置${model.columnComment!}
    */
    public String get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }

    /**
    * 获取${model.columnComment!}
    */
    public void set${model.changeColumnName}(String ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    </#if>
    <#if (model.columnType = 'bigint')>
    /**
    * 设置${model.columnComment!}
    */
    public BigDecimal get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }

    /**
    * 获取${model.columnComment!}
    */
    public void set${model.changeColumnName}(BigDecimal ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    </#if>
    <#if model.columnType = 'timestamp' || model.columnType = 'datetime'>
    /**
    * 设置${model.columnComment!}
    */
    public Date get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }

    /**
    * 获取${model.columnComment!}
    */
    public void set${model.changeColumnName}(Date ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    </#if>

</#list>
</#if>

}