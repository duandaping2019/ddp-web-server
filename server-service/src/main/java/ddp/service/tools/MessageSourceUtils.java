package ddp.service.tools;

import ddp.entity.security.SysLangEntity;
import ddp.service.security.SysLangService;
import ddp.tools.SpringBeanUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageSourceUtils {

  private MessageSourceUtils(){}

  //用来缓存数据库中获取到的配置的 数据库配置更改的时候可以调用reload方法重新加载
  private static final Map<String, Map<String, String>> LOCAL_CACHE = new ConcurrentHashMap<>(256);

  /**
   * 从数据库中获取所有国际化配置
   */
  private static Map<String, Map<String, String>> loadAllMessageResourcesFromDB() {
    SysLangService sysLangService = SpringBeanUtils.getBean(SysLangService.class);
    List<SysLangEntity> list = sysLangService.findAllInfo();
    if (!list.isEmpty()) {
      LOCAL_CACHE.clear(); //清理缓存
      final Map<String, String> zhCnMessageResources = new HashMap<>(list.size());
      final Map<String, String> enUsMessageResources = new HashMap<>(list.size());
      for (SysLangEntity entity : list) {
        zhCnMessageResources.put(entity.getLangCode(), entity.getLangZh());
        enUsMessageResources.put(entity.getLangCode(), entity.getLangEn());
      }
      LOCAL_CACHE.put("zh_CN", zhCnMessageResources);
      LOCAL_CACHE.put("en_US", enUsMessageResources);
    }
    return LOCAL_CACHE;
  }

  /**
   * 从缓存中取出国际化配置对应的数据 或者从父级获取
   * @param code 关键字
   * @param locale 语言代码
   */
  public static String getSourceFromCache(String code, Locale locale) {
    String language = locale.getLanguage() + "_" + locale.getCountry();
    Map<String, String> props = LOCAL_CACHE.get(language); //缓存获取

    String result = null;
    if (props != null && props.containsKey(code)) {
      result = props.get(code);
    } else {
      loadAllMessageResourcesFromDB(); //数据库获取
      props = LOCAL_CACHE.get(language); //返回结果
      result = props.get(code);
    }

    return result;
  }
}
