package ${package_name};

import ddp.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


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
    <#if (model.columnType = 'varchar')>
    @Column(name = "${model.columnName}", length = ${model.columnLen})
    private String ${model.changeColumnName?uncap_first};
    </#if>
    <#if (model.columnType = 'text')>
    @Column(name = "${model.columnName}")
    private String ${model.changeColumnName?uncap_first};
    </#if>
    <#if model.columnType = 'timestamp' || model.columnType = 'datetime'>
    @Column(name = "${model.columnName}", length = ${model.columnLen})
    private Date ${model.changeColumnName?uncap_first};
    </#if>

</#list>
</#if>
}