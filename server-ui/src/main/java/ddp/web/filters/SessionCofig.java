package ddp.web.filters;

import com.alibaba.fastjson.JSON;
import ddp.beans.BaseResponse;
import ddp.web.configure.MyLocaleResolver;
import ddp.web.tools.MessageSourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Session拦截器
 */
//@Configuration
public class SessionCofig implements WebMvcConfigurer {

  @Autowired
  private MyLocaleResolver myLocaleResolver;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new SecurityInterceptor())
        //排除拦截
        .excludePathPatterns("/user/login")
        .excludePathPatterns("/user/logout")

        //拦截路径
        .addPathPatterns("/**");
  }

  //@Configuration
  public class SecurityInterceptor implements HandlerInterceptor {

    /**
     * 登陆Session校验拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
      HttpSession session = request.getSession();
      if (session.getAttribute(session.getId()) != null) {
        return true;
      }

      response.getWriter().write(JSON.toJSONString(BaseResponse.noLogin(MessageSourceUtils.getSourceFromCache(
              "login_first", myLocaleResolver.resolveLocale(request)))));
      return false;
    }
  }

}
