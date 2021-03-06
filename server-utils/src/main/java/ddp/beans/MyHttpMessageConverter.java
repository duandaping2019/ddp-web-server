package ddp.beans;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义消息转换支持类
 */
public class MyHttpMessageConverter extends MappingJackson2HttpMessageConverter {
  public MyHttpMessageConverter() {
    List<MediaType> mediaTypes = new ArrayList<>();
    mediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
    setSupportedMediaTypes(mediaTypes);
  }
}
