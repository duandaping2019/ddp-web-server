package ddp.web.filters;

import com.alibaba.fastjson.JSON;
import ddp.web.BaseResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Session拦截器
 */
@Configuration
public class SessionCofig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new SecurityInterceptor())
        //排除拦截
        .excludePathPatterns("/user/login")
        .excludePathPatterns("/user/logout")

        //拦截路径
        .addPathPatterns("/**");
  }

  @Configuration
  public class SecurityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
      HttpSession session = request.getSession();
      if (session.getAttribute(session.getId()) != null){
        return true;
      }

      response.getWriter().write(JSON.toJSONString(BaseResponse.noLogin("")));
      return false;
    }
  }

}
