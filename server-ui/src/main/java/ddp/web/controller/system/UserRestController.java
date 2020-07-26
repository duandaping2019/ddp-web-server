package ddp.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import ddp.service.security.SysUserService;
import ddp.tools.ExceptionUtils;
import ddp.tools.MyPageUtils;
import ddp.web.BaseController;
import ddp.web.BaseResponse;
import ddp.web.tools.MessageSourceUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户管理类",value = "UserRestController")
@RestController
@RequestMapping("/user")
public class UserRestController extends BaseController {

  /**
   * 用户管理服务
   */
  @Autowired
  private SysUserService userService;

  @ApiOperation(value = "login",notes = "用户登陆")
  @PostMapping("/login")
  public BaseResponse login(@ApiParam(value = "用户请求参数",required =false) @RequestBody SysUserExt ext,@ApiParam(value = "语言请求参数",required =false) Locale locale,
      @ApiParam(value = "用户会话对象",required =false) HttpSession session) throws ExceptionUtils{
    SysUserEntity user = userService.getUserByLoginId(ext.getLoginId());
    if(user != null && user.getLoginPwd().equals(ext.getLoginPwd())){
      //将用户信息写入session，用于登陆拦截判定
      session.setAttribute(session.getId(), JSON.toJSONString(user));

    }else{
      return BaseResponse.badrequest(MessageSourceUtils.getSourceFromCache("login_fail",locale));
    }

    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("login_succ",locale));
  }


  @ApiOperation(value = "logout",notes = "用户注销")
  @PostMapping("/logout")
  public BaseResponse logout(@ApiParam(value = "用户请求参数",required =false) @RequestBody SysUserExt ext,@ApiParam(value = "语言请求参数",required =false) Locale locale,
      @ApiParam(value = "用户会话对象",required =false) HttpSession session) throws ExceptionUtils{
    SysUserEntity user = userService.getUserByLoginId(ext.getLoginId());
    if(user != null){
      session.removeAttribute(session.getId());
    }

    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("login_out",locale));
  }


  @ApiOperation(value = "getUserInfoList",notes = "获取用户集合信息")
  @PostMapping("/get_user_info_list")
  public BaseResponse getUserInfoList(@ApiParam(value = "用户请求参数",required =false) @RequestBody SysUserExt ext,
      @ApiParam(value = "用户请求对象",required =false) HttpServletRequest request,@ApiParam(value = "语言请求参数",required =false) Locale locale){
    PageInfo<SysUserExt> pageInfo = userService.getUserInfoList(new MyPageUtils<SysUserExt>(ext,request));
    return BaseResponse.success(pageInfo);
  }


}
