package ddp.tools;

import ddp.beans.ColumnClass;
import freemarker.template.Template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 描述：代码生成器
 */
public class CodeGenerateUtils {

  /**
   * 启动入口
   * @param args 系统参数
   * @throws Exception 系统异常
   */
  public static void main(String[] args) {
    Connection connection = null;
    try {
      //驱动加载
      Class.forName(PropertiesUtils.getProperty("DRIVER"));
      connection = DriverManager.getConnection(PropertiesUtils.getProperty("URL"),
              PropertiesUtils.getProperty("USER"), PropertiesUtils.getProperty("PASSWORD"));

      DatabaseMetaData databaseMetaData = connection.getMetaData();

      //获取列信息【首参数：指定用户】
      ResultSet columnsResultSet = databaseMetaData.getColumns("ddp", "%",
              PropertiesUtils.getProperty("TABLE_NAME"), "%");

      //读取数据集合
      List<ColumnClass> columnClassList = getColumnClassList(columnsResultSet);


      //生成Model文件
      generateModelFile(columnClassList);
      //生成Ext文件
      generateExtFile();
      //生成Mapper文件
      generateMapperFile();
      generateMapperXmlFile(columnClassList);



    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }

  }

  /**
   * 生成MapperXml文件
   */
  private static void generateMapperXmlFile(List<ColumnClass> columnClassList) {
    //获取文件【basePath + packagePath + fileName】
    final String path =
            PropertiesUtils.getProperty("DISK_PATH") + PropertiesUtils.getProperty("BASE_PACKAGE_SOURCE_BATH")
                    + MyStringUtils.replaceUnderLineAndUpperCase(PropertiesUtils.getProperty("TABLE_NAME"))
                    + "Mapper.xml";
    File mapperXmlFile = new File(path);

    //生成文件信息
    Map<String, Object> dataMap = new HashMap<>();
    dataMap.put("mapperInfo", PropertiesUtils.getProperty("MAPPER_PACKAGE") + "."
            + MyStringUtils.replaceUnderLineAndUpperCase(PropertiesUtils.getProperty("TABLE_NAME")) + "Mapper"); //类全名
    dataMap.put("typeInfo", PropertiesUtils.getProperty("EXT_PACKAGE") + "."
            + MyStringUtils.replaceUnderLineAndUpperCase(PropertiesUtils.getProperty("TABLE_NAME")) + "Ext"); // 引入信息
    dataMap.put("model_column", columnClassList); //列信息

    generateFileByTemplate("mapperXml.ftl", mapperXmlFile, dataMap);
  }

  /**
   * 生成Mapper文件
   */
  private static void generateMapperFile() {
    //获取文件【basePath + packagePath + fileName】
    final String path =
            PropertiesUtils.getProperty("DISK_PATH") + PropertiesUtils.getProperty("BASE_PACKAGE_JAVA_BATH")
                    + MyStringUtils.transPackage2Path(PropertiesUtils.getProperty("MAPPER_PACKAGE"))
                    + MyStringUtils.replaceUnderLineAndUpperCase(PropertiesUtils.getProperty("TABLE_NAME"))
                    + "Mapper.java";
    File mapperFile = new File(path);

    //生成文件信息
    Map<String, Object> dataMap = new HashMap<>();
    dataMap.put("package_name", PropertiesUtils.getProperty("MAPPER_PACKAGE")); //包名
    dataMap.put("class_name", MyStringUtils.replaceUnderLineAndUpperCase(PropertiesUtils.getProperty("TABLE_NAME"))
            + "Mapper"); //类名
    dataMap.put("point_class_info", PropertiesUtils.getProperty("MODEL_PACKAGE") + "."
            + MyStringUtils.replaceUnderLineAndUpperCase(PropertiesUtils.getProperty("TABLE_NAME")) + "Entity"); //对应实体包名
    dataMap.put("point_class", MyStringUtils.replaceUnderLineAndUpperCase(PropertiesUtils.getProperty("TABLE_NAME")) + "Entity"); //对应实体类名
    dataMap.put("table_remark", PropertiesUtils.getProperty("TABLE_REMARK")); //表描述
    dataMap.put("author", PropertiesUtils.getProperty("AUTHOR")); //作者
    dataMap.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); //日期

    generateFileByTemplate("mapper.ftl", mapperFile, dataMap);

  }

  /**
   * 生成Ext文件
   */
  private static void generateExtFile() {
    //获取文件【basePath + packagePath + fileName】
    final String path =
            PropertiesUtils.getProperty("DISK_PATH") + PropertiesUtils.getProperty("BASE_PACKAGE_JAVA_BATH")
                    + MyStringUtils.transPackage2Path(PropertiesUtils.getProperty("EXT_PACKAGE"))
                    + MyStringUtils.replaceUnderLineAndUpperCase(PropertiesUtils.getProperty("TABLE_NAME"))
                    + "Ext.java";
    File extFile = new File(path);


    //生成文件信息
    Map<String, Object> dataMap = new HashMap<>();
    dataMap.put("package_name", PropertiesUtils.getProperty("EXT_PACKAGE")); //包名
    dataMap.put("class_name", MyStringUtils.replaceUnderLineAndUpperCase(PropertiesUtils.getProperty("TABLE_NAME"))
            + "Ext"); //类名
    dataMap.put("parent_class_name", MyStringUtils.replaceUnderLineAndUpperCase(PropertiesUtils.getProperty("TABLE_NAME"))
            + "Entity"); //父级类名
    dataMap.put("parent_import_info", PropertiesUtils.getProperty("MODEL_PACKAGE") + "."
            + dataMap.get("parent_class_name").toString()); //父级包名
    dataMap.put("table_remark", PropertiesUtils.getProperty("TABLE_REMARK")); //表描述
    dataMap.put("author", PropertiesUtils.getProperty("AUTHOR")); //作者
    dataMap.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); //日期

    generateFileByTemplate("ext.ftl", extFile, dataMap);
  }

  /**
   * 生成Model文件
   * @param columnClassList 结果集
   */
  private static void generateModelFile(List<ColumnClass> columnClassList) {
    //获取文件【basePath + packagePath + fileName】
    final String path =
        PropertiesUtils.getProperty("DISK_PATH") + PropertiesUtils.getProperty("BASE_PACKAGE_JAVA_BATH")
            + MyStringUtils.transPackage2Path(PropertiesUtils.getProperty("MODEL_PACKAGE"))
            + MyStringUtils.replaceUnderLineAndUpperCase(PropertiesUtils.getProperty("TABLE_NAME"))
            + "Entity.java";
    File modelFile = new File(path);

    /*基础信息设置*/
    Map<String, Object> dataMap = new HashMap<>();
    dataMap.put("model_column", columnClassList); //列信息
    dataMap.put("package_name", PropertiesUtils.getProperty("MODEL_PACKAGE")); //包名
    dataMap.put("table_remark", PropertiesUtils.getProperty("TABLE_REMARK")); //表描述
    dataMap.put("author", PropertiesUtils.getProperty("AUTHOR")); //作者
    dataMap.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); //日期
    dataMap.put("table_name", PropertiesUtils.getProperty("TABLE_NAME")); //表名
    dataMap.put("class_name", MyStringUtils.replaceUnderLineAndUpperCase(PropertiesUtils.getProperty("TABLE_NAME")) + "Entity"); //类名

    //生成文件信息
    generateFileByTemplate("model.ftl", modelFile, dataMap);

  }

  /**
   * 读取数据集合
   * @param resultSet 数据来源
   */
  private static List<ColumnClass> getColumnClassList(ResultSet resultSet) {
    //获取实体信息集合
    List<ColumnClass> columnClassList = new ArrayList<>();
    while (true) {
      try {
        if (!resultSet.next()) {
          break;
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }

      try {
        ColumnClass columnClass = new ColumnClass();
        columnClass.setColumnName(resultSet.getString("COLUMN_NAME")); //获取字段名称
        columnClass.setColumnType(resultSet.getString("TYPE_NAME").toLowerCase()); //获取字段类型【转小写】
        columnClass.setColumnLen(resultSet.getInt("COLUMN_SIZE")); // 字段长度
        columnClass.setChangeColumnName(MyStringUtils.replaceUnderLineAndUpperCase(columnClass.getColumnName())); //实体属性
        columnClass.setColumnComment(resultSet.getString("REMARKS")); //字段说明

        columnClassList.add(columnClass);
      } catch (SQLException e) {
        e.printStackTrace();
      }

    }

    return columnClassList;
  }


  /**
   * 生成模板文件
   * @param templateName 模板
   * @param file 文件
   * @param dataMap 数据
   */
  private static void generateFileByTemplate(String templateName, File file, Map<String, Object> dataMap) {
    // 读取磁盘文件【utf-8】
    try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8), 10240)) {
      Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
      template.process(dataMap, out);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }


}
