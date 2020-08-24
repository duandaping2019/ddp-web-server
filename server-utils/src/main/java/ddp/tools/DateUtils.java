package ddp.tools;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils {

  private DateUtils() {
  }

  /*常见日期格式串*/
  private static String[] patternOfDateArr = {

  };

  /*常见时间格式串*/
  private static String[] patternOfTimeArr = {

  };

  /*常见日期时间格式串*/
  private static String[] patternOfDateAndTimeArr = {

  };

  /**
   * 获得指定格式当前日期
   */
  public static String getCurrentDate(String pattern) {
    if (!Arrays.asList(patternOfDateArr).contains(pattern)) {
      pattern = "yyyy-MM-dd";
    }

    SimpleDateFormat df = new SimpleDateFormat(pattern);
    return df.format(new Date());
  }

  /**
   * 获得指定格式当前时间
   */
  public static String getCurrentTime(String pattern) {
    if (!Arrays.asList(patternOfTimeArr).contains(pattern)) {
      pattern = "HH:mm:ss";
    }

    SimpleDateFormat df = new SimpleDateFormat(pattern);
    return df.format(new Date());
  }

  /**
   * 获得指定格式当前日期时间
   */
  public static String getCurrentDateAndTime(String pattern) {
    if (!Arrays.asList(patternOfDateAndTimeArr).contains(pattern)) {
      pattern = "yyyy-MM-dd HH:mm:ss";
    }

    SimpleDateFormat df = new SimpleDateFormat(pattern);
    return df.format(new Date());
  }


}