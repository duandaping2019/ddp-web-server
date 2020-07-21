package ddp.web.controller.test;

import com.github.pagehelper.PageInfo;
import ddp.beans.common.MyPageHelper;
import ddp.beans.test.HelloUserBean;
import ddp.ext.security.SecUserExt;
import ddp.service.security.UserService;
import ddp.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "框架测试类",value = "HelloRestController")
@RestController
@RequestMapping("/test")
public class HelloRestController extends BaseController {

  @Autowired
  private UserService userService;

  @ApiOperation(value = "hello",notes = "欢迎测试")
  @GetMapping("/hello")
  public HelloUserBean hello(@ApiParam(value = "访问人姓名",required =true) @RequestParam("userName") String userName){
    HelloUserBean bean = new HelloUserBean();
    bean.setUserName("国际化"+userName);
    return bean;
  }

  @ApiOperation(value = "getSecUserInfo",notes = "获取系统用户信息")
  @PostMapping("/get_sec_user_info")
  public PageInfo<SecUserExt> getSecUserInfo(@ApiParam(value = "用户请求参数",required =false) @RequestBody SecUserExt ext,@ApiParam(value = "用户请求对象",required =false) HttpServletRequest request){
    return userService.getSecUserInfo(new MyPageHelper<SecUserExt>(ext,request));
  }



}
