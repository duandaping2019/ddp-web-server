package ddp.tools;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * 字符串解析工具类
 */
public class MyStringUtils {

  private MyStringUtils(){}

  /**
   *  驼峰转换
   *  输入：sys_log >>>>> 输出：sysLog
   */
  public static String replaceUnderLineAndUpperCase(String str) {
    StringBuilder sb = new StringBuilder();
    sb.append(str);
    int count = sb.indexOf("_");
    while (count != 0) {
      int num = sb.indexOf("_", count);
      count = num + 1;
      if (num != -1) {
        char ss = sb.charAt(count);
        char ia = (char) (ss - 32);
        sb.replace(count, count + 1, ia + "");
      }
    }
    String result = sb.toString().replace("_", "");
    return StringUtils.capitalize(result);
  }

  /**
   * 包名转路径
   * @param modelPackage 包名
   * @return
   */
  public static String transPackage2Path(String modelPackage) {
    StringBuilder builder = new StringBuilder();
    builder.append(modelPackage.replaceAll("\\.", File.separator));
    builder.append(File.separator);

    return builder.toString();
  }
}
