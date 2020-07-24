package ddp;

import ddp.web.configure.MyLocaleResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.LocaleResolver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableSwagger2
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


  /**
   * 配置自定义语言解析器
   */
  @Bean
  public LocaleResolver localeResolver(){
    return new MyLocaleResolver();
  }

}
