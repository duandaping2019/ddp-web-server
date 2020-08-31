package ddp.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import ddp.beans.BaseResponse;
import ddp.constants.CommConstants;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import ddp.service.security.SysUserService;
import ddp.tools.MyPageUtils;
import ddp.web.BaseController;
import ddp.web.aop.OperLog;
import ddp.web.tools.MessageSourceUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

  /**
   * 用户登陆
   */
  @ApiOperation(value = "login", notes = "用户登陆")
  @PostMapping("/login")
  @OperLog(operModul = "系统管理", operType = CommConstants.GET_DATA, operDesc = "用户登陆")
  public BaseResponse<Object> login(@ApiParam(value = "用户请求参数", required = false) @RequestBody SysUserExt ext,
                            @ApiParam(value = "语言请求参数", required = false) Locale locale,
                            @ApiParam(value = "用户会话对象", required = false) HttpSession session) {
    //封装条件
    Example example = new Example(SysUserEntity.class);
    example.createCriteria().andEqualTo("loginId", ext.getLoginId()).andEqualTo("loginPwd", ext.getLoginPwd());

    //执行查询
    SysUserEntity user = userService.getEntityInfo(example);

    //逻辑处理
    if (user != null && user.getLoginPwd().equals(ext.getLoginPwd())) {
      //将用户信息写入session，用于登陆拦截判定【*】
      session.setAttribute(session.getId(), JSON.toJSONString(user));

    } else {
      return BaseResponse.badrequest(MessageSourceUtils.getSourceFromCache("login_fail", locale));
    }

    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("login_succ", locale));
  }


  /**
   * 用户注销
   */
  @ApiOperation(value = "logout", notes = "用户注销")
  @PostMapping("/logout")
  public BaseResponse<Object> logout(@ApiParam(value = "用户请求参数", required = false) @RequestBody SysUserExt ext,
                             @ApiParam(value = "语言请求参数", required = false) Locale locale,
                             @ApiParam(value = "用户会话对象", required = false) HttpSession session) {
    //封装条件
    Example example = new Example(SysUserEntity.class);
    example.createCriteria().andEqualTo("loginId", ext.getLoginId());

    //执行查询
    SysUserEntity user = userService.getEntityInfo(example);

    //逻辑处理
    if (user != null) {
      session.removeAttribute(session.getId());
    } else {
      return BaseResponse.success(MessageSourceUtils.getSourceFromCache("login_out_fail", locale));
    }

    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("login_out", locale));
  }


  @ApiOperation(value = "getUserInfo", notes = "获取用户信息")
  @PostMapping("/get_user_info")
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
  @PostMapping("/get_user_info_list")
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
  @PostMapping("/add_user_info")
  public BaseResponse<Object> addUserInfo(@ApiParam(value = "用户请求参数", required = false) @RequestBody SysUserExt ext,
                                  @ApiParam(value = "语言请求参数", required = false) Locale locale) {
    SysUserEntity entity = new SysUserEntity();
    BeanUtils.copyProperties(ext, entity);
    int result = userService.addEntityInfo(entity);
    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), result);
  }


  @ApiOperation(value = "addUserInfoList", notes = "添加用户信息")
  @PostMapping("/add_user_info_list")
  public BaseResponse<Object> addUserInfoList(@ApiParam(value = "用户请求参数", required = false) @RequestBody List<SysUserExt> extList,
                                      @ApiParam(value = "语言请求参数", required = false) Locale locale) {
    List<SysUserEntity> entityList = new ArrayList<>();
    entityList.addAll(extList);
    int result = userService.addEntityInfoList(entityList);
    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), result);
  }






}
