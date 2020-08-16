package ddp.tools;

import ddp.beans.ColumnClass;
import freemarker.template.Template;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：代码生成器
 */
public class CodeGenerateUtils {

  /**
   *  启动入口
   */
  public static void main(String[] args) throws Exception{
    Class.forName(PropertiesUtils.getProperty("DRIVER"));
    Connection connection= DriverManager.getConnection(PropertiesUtils.getProperty("URL"),PropertiesUtils.getProperty("USER"),PropertiesUtils.getProperty("PASSWORD"));
    DatabaseMetaData databaseMetaData = connection.getMetaData();

    //获取列信息
    ResultSet columnsResultSet = databaseMetaData.getColumns(null,"%", PropertiesUtils.getProperty("TABLE_NAME"),"%");

    //生成Model文件
    generateModelFile(columnsResultSet);

  }

  /**
   * 生成Model文件
   * @param resultSet
   */
  private static void generateModelFile(ResultSet resultSet) throws Exception{
    //获取文件【basePath + packagePath + fileName】
    final String path =
        PropertiesUtils.getProperty("DISK_PATH") + PropertiesUtils.getProperty("MODEL_PACKAGE_BATH")
            + MyStringUtils.transPackage2Path(PropertiesUtils.getProperty("MODEL_PACKAGE"))
            + MyStringUtils.replaceUnderLineAndUpperCase(PropertiesUtils.getProperty("TABLE_NAME"))
            + ".java";
    File modelFile = new File(path);

    //获取实体信息集合
    List<ColumnClass> columnClassList = new ArrayList<>();
    while(resultSet.next()){
      ColumnClass columnClass = new ColumnClass();

      columnClass.setColumnName(resultSet.getString("COLUMN_NAME"));//获取字段名称
      columnClass.setColumnType(resultSet.getString("TYPE_NAME"));//获取字段类型
      columnClass.setChangeColumnName(MyStringUtils.replaceUnderLineAndUpperCase(columnClass.getColumnName()));//实体属性
      columnClass.setColumnComment(resultSet.getString("REMARKS"));//字段说明

      columnClassList.add(columnClass);
    }

    //生成文件信息
    Map<String,Object> dataMap = new HashMap<>();
    dataMap.put("model_column",columnClassList);

    generateFileByTemplate("model.ftl",modelFile,dataMap);
  }


  /**
   * 生成模板文件
   * @param templateName 模板
   * @param file 文件
   * @param dataMap 数据
   */
  private static void generateFileByTemplate(String templateName, File file, Map<String, Object> dataMap) throws Exception {
    Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
    FileOutputStream fos = new FileOutputStream(file);

    /*基础信息设置*/
    dataMap.put("package_name",PropertiesUtils.getProperty("MODEL_PACKAGE"));//包名
    dataMap.put("table_remark",PropertiesUtils.getProperty("TABLE_REMARK"));//表描述
    dataMap.put("author",PropertiesUtils.getProperty("AUTHOR"));//作者
    dataMap.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//日期
    dataMap.put("table_name",PropertiesUtils.getProperty("TABLE_NAME"));//表名
    dataMap.put("class_name",MyStringUtils.replaceUnderLineAndUpperCase(PropertiesUtils.getProperty("TABLE_NAME")));//类名

    /*属性信息设置*/


    Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
    template.process(dataMap,out);

  }




}
