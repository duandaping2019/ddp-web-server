package ddp.demo;

import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateDemo {

  public static void main(String[] args) {
    getCommContent();
  }

  /**
   *  获取普通对象
   */
  private static void getCommContent() {
    //请求体设置参数
    JSONObject json = new JSONObject();
    json.put("loginId","ddp");
    json.put("loginPwd","123456");

    //请求头设置
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.valueOf("application/json;UTF-8"));

    //请求服务器
    RestTemplate template = new RestTemplate();
    HttpEntity<JSONObject> strEntity = new HttpEntity<JSONObject>(json,headers);
    JSONObject result = template.postForObject("http://localhost:8080/user/login?language=en_US",strEntity,JSONObject.class);

    //输出响应结果
    System.out.println(result.getString("msg"));

  }


  /**
   *  获取文件流
   */
  public static void getFileContent(){
    RestTemplate template = new RestTemplate();
    ResponseEntity<Resource> entity = template.getForEntity("http://10.32.27.170/version-mgt/api/file/downfile?sourceid=8606", Resource.class);
    if(entity.getStatusCode().equals(HttpStatus.OK)){
      InputStream in = null;
      OutputStream out = null;
      try {
        in = entity.getBody().getInputStream();
        out = new FileOutputStream(new File("G:\\Temp\\svm-files\\ddp-test.txt"));

        //当temp等于-1时，表示已经到了文件结尾，停止读取
        int temp = 0;
        while ((temp = in.read()) != -1) {
          out.write(temp);
        }
      }catch (Exception e){
        e.printStackTrace();
      }finally {
        try {
          if(in != null){
            in.close();
          }
          if(out != null){
            out.close();
          }
        }catch (Exception e){
          e.printStackTrace();
        }
      }
    }
  }

}
