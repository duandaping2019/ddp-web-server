package ddp.web.configure;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


/**
 * 国际化解析器
 */
@Configuration
public class MyLocaleResolver implements LocaleResolver {

  @Override
  public Locale resolveLocale(HttpServletRequest request) {
    Locale locale = Locale.getDefault();

    String language = request.getParameter("language");
    if (!StringUtils.isEmpty(language)) {
      String[] split = language.split("_");
      locale = new Locale(split[0], split[1]);
    }
    return locale;
  }

  @Override
  public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    Locale.setDefault(locale);
  }

}
