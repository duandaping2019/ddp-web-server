package ddp.web.controller.system;

import ddp.entity.security.SecUserEntity;
import ddp.ext.security.SecUserExt;
import ddp.service.security.UserService;
import ddp.web.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户管理类",value = "UserRestController")
@RestController
@RequestMapping("/user")
public class UserRestController {

  /**
   * 用户管理服务
   */
  @Autowired
  private UserService userService;

  @ApiOperation(value = "login",notes = "用户登陆")
  @PostMapping("/login")
  public BaseResponse login(@RequestBody SecUserExt ext){
    SecUserEntity user = userService.getUserByLoginId(ext.getLoginId());
    if(user != null){
      //登录成功后将用户信息写入session
      return BaseResponse.success("登陆成功",user);
    }
    return BaseResponse.badrequest("登陆失败！");
  }


}
