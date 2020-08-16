package ddp.tools;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.IOException;

/**
 *  FreeMarker模板工具类
 */
public class FreeMarkerTemplateUtils {


  private FreeMarkerTemplateUtils(){}
  private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_30);

  static{
    //这里比较重要，用来指定加载模板所在的路径
    CONFIGURATION.setTemplateLoader(new ClassTemplateLoader(FreeMarkerTemplateUtils.class, "/genTemplates"));
    CONFIGURATION.setDefaultEncoding("UTF-8");
    CONFIGURATION.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    CONFIGURATION.setCacheStorage(NullCacheStorage.INSTANCE);
  }

  /**
   *  获取模板引擎对象
   * @param templateName 模板文件
   * @return
   */
  public static Template getTemplate(String templateName) throws IOException {
    try {
      return CONFIGURATION.getTemplate(templateName);
    } catch (IOException e) {
      throw e;
    }
  }

  /**
   *  清除配置信息
   */
  public static void clearCache() {
    CONFIGURATION.clearTemplateCache();
  }

}
