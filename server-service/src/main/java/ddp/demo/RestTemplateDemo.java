package ddp.demo;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateDemo {

  private static Logger logger = LoggerFactory.getLogger(RestTemplateDemo.class);

  public static void main(String[] args) {
    getCommContent();
  }

  /**
   *  获取普通对象
   */
  private static void getCommContent() {
    //请求体设置参数
    JSONObject json = new JSONObject();
    json.put("loginId", "ddp");
    json.put("loginPwd", "123456");

    //请求头设置
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.valueOf("application/json;UTF-8"));

    //请求服务器
    RestTemplate template = new RestTemplate();
    HttpEntity<JSONObject> strEntity = new HttpEntity<>(json, headers);
    JSONObject result = template.postForObject("http://localhost:8080/user/login?language=en_US", strEntity, JSONObject.class);

    //输出响应结果
    logger.info(result.getString("msg"));

  }

}
