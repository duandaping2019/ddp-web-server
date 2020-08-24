package ddp.tools;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 属性文件获取类
 */
public class PropertiesUtils {
  private PropertiesUtils(){}

  /*设置属性对象*/
  private static Properties pro = new Properties();

  static {
    try (InputStreamReader in = new InputStreamReader(PropertiesUtils.class.getClassLoader().getResourceAsStream("genTemplates/genProps.properties"), StandardCharsets.UTF_8)) {
      pro.load(in);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取属性值
   */
  public static String getProperty(String key) {
    return pro.getProperty(key);
  }
}
