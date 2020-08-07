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
    "ddp.service",//server-service
    "ddp.beans","ddp.configure","ddp.demo","ddp.tools"//server-tools
})
@MapperScan(basePackages = {"ddp.mapper"})
/**
 * 在启动类当中加上extends SpringBootServletInitializer并重写configure方法，这是为了打包springboot项目用的
 */
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
