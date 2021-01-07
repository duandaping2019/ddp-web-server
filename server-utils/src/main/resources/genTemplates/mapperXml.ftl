<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${mapperInfo}" >
    <resultMap id="BaseResultMap" type="${typeInfo}">
    <#if model_column?exists>
        <#list model_column as model>
            <#if model_index == 0>
                <#if (model.columnType = 'varchar')>
        <id column="${model.columnName}" property="${model.changeColumnName?uncap_first}" jdbcType="VARCHAR" />
                <#else>
        <id column="${model.columnName}" property="${model.changeColumnName?uncap_first}" jdbcType="BIGINT" />
                </#if>
            <#else>
                <#if (model.columnType = 'varchar' || model.columnType = 'text')>
        <result column="${model.columnName}" property="${model.changeColumnName?uncap_first}" jdbcType="VARCHAR" />
                <#elseif (model.columnType = 'timestamp' || model.columnType = 'datetime')>
        <result column="${model.columnName}" property="${model.changeColumnName?uncap_first}" jdbcType="TIMESTAMP" />
                <#else>
        <result column="${model.columnName}" property="${model.changeColumnName?uncap_first}" jdbcType="BIGINT" />
                </#if>
            </#if>
        </#list>
    </#if>
    </resultMap>

</mapper>