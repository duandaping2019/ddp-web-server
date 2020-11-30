package ddp.tools;

import ddp.constants.CommConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.io.File;
import java.util.UUID;
import java.util.regex.Matcher;

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
   * 要用replaceAll()方法，而不是replace();
   * 包名中的"."在正则中匹配时，要转义\\.，.在正则表达式中匹配任意一个字符;
   * windows下File.separator为\，需要Matcher.quoteReplacement(File.separator)获取
   * @param modelPackage 包名
   */
  public static String transPackage2Path(String modelPackage) {
    StringBuilder builder = new StringBuilder();
    builder.append(modelPackage.replaceAll("\\.", Matcher.quoteReplacement(File.separator)));
    builder.append(File.separator);

    return builder.toString();
  }

  /**
   * 获取UUID字符串
   */
  public static String getUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  /**
   * 获取默认MD5加密密码
   */
  public static String getDefaultLoginPwd() {
    Md5Hash md5Hash = new Md5Hash(CommConstants.LOGIN_PWD, CommConstants.SALT, CommConstants.HASH_ITERATIONS);
    return md5Hash.toString();
  }

  /**
   * 获取指定MD5加密密码
   */
  public static String getPointLoginPwd(String password) {
    Md5Hash md5Hash = new Md5Hash(password, CommConstants.SALT, CommConstants.HASH_ITERATIONS);
    return md5Hash.toString();
  }

}
