package ddp.web.controller.system;

import com.github.pagehelper.PageInfo;
import ddp.beans.BaseResponse;
import ddp.constants.CommConstants;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import ddp.service.security.SysUserService;
import ddp.tools.MyPageUtils;
import ddp.web.controller.BaseController;
import ddp.web.aop.OperLog;
import ddp.web.tools.MessageSourceUtils;
import ddp.web.tools.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Api(tags = "用户管理类", value = "UserRestController")
@RestController
@RequestMapping("/user")
public class UserRestController extends BaseController {

  /**
   * 用户管理服务
   */
  @Autowired
  private SysUserService userService;

  @Autowired
  private LocaleResolver localeResolver;

  /**
   * 用户登陆
   */
  @ApiOperation(value = "login", notes = "用户登陆")
  @RequestMapping("/login")
  @OperLog(operModul = "系统管理", operType = CommConstants.GET_DATA, operDesc = "用户登陆")
  public BaseResponse<Object> login(@ApiParam(value = "用户请求参数", required = false) @RequestBody SysUserExt ext,
                            @ApiParam(value = "语言请求参数", required = false) Locale locale,
                            @ApiParam(value = "用户请求对象", required = false) HttpServletRequest request,
                            @ApiParam(value = "用户响应对象", required = false) HttpServletResponse response) {

    // 语言环境设置
    localeResolver.setLocale(request, response, locale);

    try {
      // 登陆验证
      UsernamePasswordToken token = new UsernamePasswordToken(ext.getLoginId(), ext.getLoginPwd());
      Subject subject = SecurityUtils.getSubject();
      subject.login(token);

    } catch (DisabledAccountException e) {
      return BaseResponse.badrequest(MessageSourceUtils.getSourceFromCache("login_fail_forbid", locale));
    } catch (AuthenticationException e) {
      return BaseResponse.badrequest(MessageSourceUtils.getSourceFromCache("login_fail_info", locale));
    }

    // 执行到这里说明用户已登录成功
    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("login_succ", locale));

  }


  /**
   * 用户注销
   */
  @ApiOperation(value = "logout", notes = "用户注销")
  @RequestMapping("/logout")
  @OperLog(operModul = "系统管理", operType = CommConstants.GET_DATA, operDesc = "用户退出")
  public BaseResponse<Object> logout(@ApiParam(value = "用户请求参数", required = false) @RequestBody SysUserExt ext,
                             @ApiParam(value = "语言请求参数", required = false) Locale locale) {
    //逻辑处理
    if (ShiroUtils.isLogin()) {
      ShiroUtils.logout();
    } else {
      return BaseResponse.success(MessageSourceUtils.getSourceFromCache("login_out_fail", locale));
    }

    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("login_out", locale));
  }


  @ApiOperation(value = "getUserInfo", notes = "获取用户信息")
  @RequestMapping("/get_user_info")
  @OperLog(operModul = "系统管理", operType = CommConstants.GET_DATA, operDesc = "获取用户信息")
//  @RequiresPermissions("get_user_info")
  public BaseResponse<Object> getUserInfo(@ApiParam(value = "用户请求参数", required = false) @RequestBody SysUserExt ext,
                                  @ApiParam(value = "语言请求参数", required = false) Locale locale) {
    //封装条件
    Example example = new Example(SysUserEntity.class);
    example.createCriteria().andEqualTo(ext);

    //执行查询
    SysUserEntity entity = userService.getEntityInfo(example);

    //返回结果
    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), entity);
  }


  @ApiOperation(value = "getUserInfoList", notes = "获取用户集合信息")
  @RequestMapping("/get_user_info_list")
  @OperLog(operModul = "系统管理", operType = CommConstants.GET_DATA, operDesc = "获取用户集合信息")
  public BaseResponse<Object> getUserInfoList(@ApiParam(value = "用户请求参数", required = false) @RequestBody SysUserExt ext,
      @ApiParam(value = "用户请求对象", required = false) HttpServletRequest request, @ApiParam(value = "语言请求参数", required = false) Locale locale) {

    //封装条件
    Example example = new Example(SysUserEntity.class);
    example.createCriteria().andEqualTo(ext);

    MyPageUtils.setPageInfo(request); //分页设定
    PageInfo<SysUserEntity> pageInfo = userService.getEntityInfoList(example);
    return BaseResponse.success(pageInfo);
  }


  @ApiOperation(value = "addUserInfo", notes = "添加用户信息")
  @RequestMapping("/add_user_info")
  @OperLog(operModul = "系统管理", operType = CommConstants.ADD_DATA, operDesc = "添加用户信息")
  public BaseResponse<Object> addUserInfo(@ApiParam(value = "用户请求参数", required = false) @RequestBody SysUserExt ext,
                                  @ApiParam(value = "语言请求参数", required = false) Locale locale) {
    SysUserEntity entity = new SysUserEntity();
    BeanUtils.copyProperties(ext, entity);
    int result = userService.addEntityInfo(entity);
    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), result);
  }


  @ApiOperation(value = "addUserInfoList", notes = "添加用户信息")
  @RequestMapping("/add_user_info_list")
  @OperLog(operModul = "系统管理", operType = CommConstants.ADD_DATA, operDesc = "添加用户信息")
  public BaseResponse<Object> addUserInfoList(@ApiParam(value = "用户请求参数", required = false) @RequestBody List<SysUserExt> extList,
                                      @ApiParam(value = "语言请求参数", required = false) Locale locale) {
    List<SysUserEntity> entityList = new ArrayList<>();
    entityList.addAll(extList);
    int result = userService.addEntityInfoList(entityList);
    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), result);
  }


  @ApiOperation(value = "stepIntoUserIndex", notes = "进入用户首页")
  @RequestMapping("/index")
  @OperLog(operModul = "系统首页", operType = CommConstants.GET_DATA, operDesc = "进入首页")
  public BaseResponse<Object> stepIntoUserIndex(@ApiParam(value = "语言请求参数", required = false) Locale locale) {
    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), "登陆成功！");
  }


  @ApiOperation(value = "sysErrorInfo", notes = "展示系统异常信息")
  @RequestMapping("/error")
  @OperLog(operModul = "系统错误", operType = CommConstants.GET_DATA, operDesc = "抛出错误")
  public BaseResponse<Object> sysErrorInfo(@ApiParam(value = "语言请求参数", required = false) Locale locale) {
    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), "系统异常！");
  }



  @ApiOperation(value = "kickout", notes = "用户被踢出")
  @RequestMapping("/kickout")
  @OperLog(operModul = "系统提示", operType = CommConstants.GET_DATA, operDesc = "用户被踢出")
  public BaseResponse<Object> kickout(@ApiParam(value = "语言请求参数", required = false) Locale locale) {
    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), "用户被踢出了！");
  }


}
