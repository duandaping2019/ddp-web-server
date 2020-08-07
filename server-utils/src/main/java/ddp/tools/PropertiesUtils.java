package ddp.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件获取类
 */
public class PropertiesUtils {
  /*设置属性对象*/
  private static Properties pro = new Properties();
  static {
    InputStream in=null;
    try {
      in = Thread.currentThread().getContextClassLoader().getResourceAsStream("genTemplates/genProps.properties");
      pro.load(in);
    }catch (Exception e){
      e.printStackTrace();
    }finally {
      if(in !=null){
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 获取属性值
   */
  public static String getProperty(String key) {
    return pro.getProperty(key);
  }
}
