package ddp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "ddp.web",//server-ui
    "ddp.service","ddp.beans",//server-service
    "ddp.configure","ddp.tools"//server-tools
})
@MapperScan(basePackages = {"ddp.mapper"})
//@EnableRedisHttpSession
public class ServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);
  }

}
