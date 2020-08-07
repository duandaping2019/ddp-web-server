package ddp.tools;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

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
    ResultSet resultSet = databaseMetaData.getColumns(null,"%", PropertiesUtils.getProperty("TABLE_NAME"),"%");

    //生成Model文件
    generateModelFile(resultSet);

  }

  /**
   * 生成Model文件
   * @param resultSet
   */
  private static void generateModelFile(ResultSet resultSet) throws Exception{
    final String path = PropertiesUtils.getProperty("DISK_PATH") + MyStringUtils.replaceUnderLineAndUpperCase(PropertiesUtils.getProperty("TABLE_NAME")) + ".java";
    System.out.println(path);
    File file = new File(path);
    if(!file.exists()){
      file.createNewFile();
    }




//    final String templateName = "Model.ftl";
//    File mapperFile = new File(path);
//    List<ColumnClass> columnClassList = new ArrayList<>();
//    ColumnClass columnClass = null;
//    while(resultSet.next()){
//      //id字段略过
//      if(resultSet.getString("COLUMN_NAME").equals("id")) continue;
//      columnClass = new ColumnClass();
//      //获取字段名称
//      columnClass.setColumnName(resultSet.getString("COLUMN_NAME"));
//      //获取字段类型
//      columnClass.setColumnType(resultSet.getString("TYPE_NAME"));
//      //转换字段名称，如 sys_name 变成 SysName
//      columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
//      //字段在数据库的注释
//      columnClass.setColumnComment(resultSet.getString("REMARKS"));
//      columnClassList.add(columnClass);
//    }
//    Map<String,Object> dataMap = new HashMap<>();
//    dataMap.put("model_column",columnClassList);
//    generateFileByTemplate("model.ftl",mapperFile,dataMap);
  }

}
