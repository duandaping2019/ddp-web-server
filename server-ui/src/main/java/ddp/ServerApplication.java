package ddp;

import ddp.tools.SpringBeanUtils;
import ddp.web.configure.MyLocaleResolver;
import ddp.web.filters.HttpServletRequestReplacedFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.LocaleResolver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * 在启动类当中加上extends SpringBootServletInitializer并重写configure方法，这是为了打包springboot项目用的
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {
    "ddp.web", //server-ui
    "ddp.service", //server-service
    "ddp.beans", "ddp.configure", "ddp.demo", "ddp.tools" //server-utils
})
@MapperScan(basePackages = {"ddp.mapper"})
public class ServerApplication {

  public static void main(String[] args) {
    ApplicationContext applicationContext = SpringApplication.run(ServerApplication.class, args);
    SpringBeanUtils.setContext(applicationContext);
  }

  /**
   * 配置自定义语言解析器
   */
  @Bean
  public LocaleResolver localeResolver() {
    return new MyLocaleResolver();
  }

  /**
   * 拦截器注册中心【request.getInputStream()】
   * 解决IO流关闭只能读取一次BUG
   */
  @Bean
  public FilterRegistrationBean httpServletRequestReplacedRegistration() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new HttpServletRequestReplacedFilter());
    registration.addUrlPatterns("/*");
    registration.addInitParameter("paramName", "paramValue");
    registration.setName("httpServletRequestReplacedFilter");
    registration.setOrder(1);
    return registration;
  }

}
