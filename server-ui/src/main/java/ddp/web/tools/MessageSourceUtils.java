package ddp.web.tools;

import ddp.entity.security.SysLangEntity;
import ddp.service.security.SysLangService;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class MessageSourceUtils{
  /*静态资源注入  开始部分*/
  @Resource
  private SysLangService sysLangServiceResource;
  private static SysLangService sysLangService;
  @PostConstruct
  public void init() {
    this.sysLangService = sysLangServiceResource;
  }
  /*静态资源注入  结束部分*/

  //用来缓存数据库中获取到的配置的 数据库配置更改的时候可以调用reload方法重新加载
  private static final Map<String, Map<String, String>> LOCAL_CACHE = new ConcurrentHashMap<>(256);

  /**
   * 从数据库中获取所有国际化配置
   */
  public static Map<String, Map<String, String>> loadAllMessageResourcesFromDB() {
    List<SysLangEntity> list = sysLangService.findAllInfo();
    if(list !=null&&list.size()>0){
      final Map<String, String> zhCnMessageResources = new HashMap<>(list.size());
      final Map<String, String> enUsMessageResources = new HashMap<>(list.size());
      for(SysLangEntity entity : list){
        zhCnMessageResources.put(entity.getLangCode(),entity.getLangZh());
        enUsMessageResources.put(entity.getLangCode(),entity.getLangEn());
      }
      LOCAL_CACHE.put("zh_CN", zhCnMessageResources);
      LOCAL_CACHE.put("en_US", enUsMessageResources);
    }
    return LOCAL_CACHE;
  }

  /**
   * 从缓存中取出国际化配置对应的数据 或者从父级获取
   * @param code
   * @param locale
   * @return
   */
  public static String getSourceFromCache(String code, Locale locale) {
    String language = locale.getLanguage()+"_"+locale.getCountry();
    Map<String, String> props = LOCAL_CACHE.get(language);
    if (null != props && props.containsKey(code)) {
      return props.get(code);
    } else {
      //重新加载数据
      loadAllMessageResourcesFromDB();
      props = LOCAL_CACHE.get(language);
      if(null != props && props.containsKey(code)){
        return props.get(code);
      }else{
        return props.get("sys_error");
      }
    }
  }
}
