package ddp.web.controller.system;

import com.github.pagehelper.PageInfo;
import ddp.beans.BaseResponse;
import ddp.constants.CommConstants;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import ddp.service.security.SysUserService;
import ddp.service.tools.MessageSourceUtils;
import ddp.tools.MyPageUtils;
import ddp.web.aop.OperLog;
import ddp.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Api(tags = "用户管理类", value = "UserRestController")
@RestController
@RequestMapping("/user")
public class UserRestController extends BaseController {

  @Autowired
  private SysUserService userService; //用户管理服务

  @ApiOperation(value = "sysUserList", notes = "获取用户信息列表")
  @RequestMapping("list")
  @OperLog(operModul = "用户管理", operType = CommConstants.GET_DATA, operDesc = "获取用户信息列表")
  @RequiresPermissions("sys:user:list")
  public BaseResponse<Object> sysUserList(@ApiParam(value = "用户请求参数", required = false) @RequestBody SysUserExt ext,
                                               @ApiParam(value = "用户请求对象", required = false) HttpServletRequest request,
                                               @ApiParam(value = "语言请求参数", required = false) Locale locale){

    MyPageUtils.setPageInfo(request); //分页设定
    return BaseResponse.success(new PageInfo(userService.getExtListInfo(ext)));
  }


  @ApiOperation(value = "saveOrUpdate", notes = "存储用户信息")
  @RequestMapping("/save_or_update")
  @OperLog(operModul = "系统管理", operType = CommConstants.ADD_DATA, operDesc = "存储用户信息")
  @RequiresPermissions("sys:user:save")
  public BaseResponse<Object> saveOrUpdate(@ApiParam(value = "用户请求参数", required = false) @RequestBody SysUserExt ext,
                                  @ApiParam(value = "语言请求参数", required = false) Locale locale) {

    SysUserEntity entity = userService.saveOrUpdate(ext);
    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), entity);

  }


  @ApiOperation(value = "getUserInfo", notes = "获取用户信息")
  @RequestMapping("/get_user_info")
  @OperLog(operModul = "系统管理", operType = CommConstants.GET_DATA, operDesc = "获取用户信息")
  @RequiresPermissions("sys:user:info")
  public BaseResponse<Object> getUserInfo(@ApiParam(value = "用户请求参数", required = false) @RequestBody SysUserExt ext,
                                  @ApiParam(value = "语言请求参数", required = false) Locale locale) {

    SysUserExt userInfo = userService.getExtInfo(ext);
    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), userInfo);
  }


  @ApiOperation(value = "delUserInfo", notes = "获取用户信息")
  @RequestMapping("/del_user_info")
  @OperLog(operModul = "系统管理", operType = CommConstants.DEL_DATA, operDesc = "删除用户信息")
  @RequiresPermissions("sys:user:delete")
  public BaseResponse<Object> delUserInfo(@ApiParam(value = "用户请求参数", required = false) @RequestBody List<BigDecimal> idsList,
                                          @ApiParam(value = "语言请求参数", required = false) Locale locale) {

    userService.delUserInfo(idsList);
    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale));
  }





}
