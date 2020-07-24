package ddp.web.configure;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;


/**
 * 国际化解析器
 */
@Configuration
public class MyLocaleResolver implements LocaleResolver {

  private static Locale locale = Locale.getDefault();

  /**
   *  获取当前语言环境
   */
  public static Locale getCurrLocale(){
    return locale;
  }

  @Override
  public Locale resolveLocale(HttpServletRequest request) {
    String language = request.getParameter("language");
    if(!StringUtils.isEmpty(language)){
      String[] split = language.split("_");
      locale = new Locale(split[0],split[1]);
    }
    return locale;
  }

  @Override
  public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {}

}
