package ddp.web.controller.system;

import com.github.pagehelper.PageInfo;
import ddp.beans.BaseResponse;
import ddp.constants.CommConstants;
import ddp.entity.security.SysUserEntity;
import ddp.ext.security.SysUserExt;
import ddp.service.security.SysUserService;
import ddp.tools.MyPageUtils;
import ddp.web.aop.OperLog;
import ddp.web.controller.BaseController;
import ddp.web.tools.MessageSourceUtils;
import ddp.web.tools.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

  @ApiOperation(value = "saveOrUpdate", notes = "添加用户信息")
  @RequestMapping("/save_or_update")
  @OperLog(operModul = "系统管理", operType = CommConstants.ADD_DATA, operDesc = "添加用户信息")
  public BaseResponse<Object> saveOrUpdate(@ApiParam(value = "用户请求参数", required = false) @RequestBody SysUserExt ext,
                                  @ApiParam(value = "语言请求参数", required = false) Locale locale) {

    SysUserEntity entity = userService.saveOrUpdate(ext, ShiroUtils.getCurrUserInfo());
    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), entity);

  }


//  @ApiOperation(value = "getUserInfo", notes = "获取用户信息")
//  @RequestMapping("/get_user_info")
//  @OperLog(operModul = "系统管理", operType = CommConstants.GET_DATA, operDesc = "获取用户信息")
////  @RequiresPermissions("get_user_info")
//  public BaseResponse<Object> getUserInfo(@ApiParam(value = "用户请求参数", required = false) @RequestBody SysUserExt ext,
//                                  @ApiParam(value = "语言请求参数", required = false) Locale locale) {
//    //封装条件
//    Example example = new Example(SysUserEntity.class);
//    example.createCriteria().andEqualTo(ext);
//
//    //执行查询
//    SysUserEntity entity = userService.getEntityInfo(example);
//
//    //返回结果
//    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), entity);
//  }
//

//
//
//  @ApiOperation(value = "addUserInfoList", notes = "添加用户信息")
//  @RequestMapping("/add_user_info_list")
//  @OperLog(operModul = "系统管理", operType = CommConstants.ADD_DATA, operDesc = "添加用户信息")
//  public BaseResponse<Object> addUserInfoList(@ApiParam(value = "用户请求参数", required = false) @RequestBody List<SysUserExt> extList,
//                                      @ApiParam(value = "语言请求参数", required = false) Locale locale) {
//    List<SysUserEntity> entityList = new ArrayList<>();
//    entityList.addAll(extList);
//    int result = userService.addEntityInfoList(entityList);
//    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), result);
//  }
//
//
//  @ApiOperation(value = "stepIntoUserIndex", notes = "进入用户首页")
//  @RequestMapping("/index")
//  @OperLog(operModul = "系统首页", operType = CommConstants.GET_DATA, operDesc = "进入首页")
//  public BaseResponse<Object> stepIntoUserIndex(@ApiParam(value = "语言请求参数", required = false) Locale locale) {
//    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), "登陆成功！");
//  }
//
//
//  @ApiOperation(value = "sysErrorInfo", notes = "展示系统异常信息")
//  @RequestMapping("/error")
//  @OperLog(operModul = "系统错误", operType = CommConstants.GET_DATA, operDesc = "抛出错误")
//  public BaseResponse<Object> sysErrorInfo(@ApiParam(value = "语言请求参数", required = false) Locale locale) {
//    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), "系统异常！");
//  }
//
//
//
//  @ApiOperation(value = "kickout", notes = "用户被踢出")
//  @RequestMapping("/kickout")
//  @OperLog(operModul = "系统提示", operType = CommConstants.GET_DATA, operDesc = "用户被踢出")
//  public BaseResponse<Object> kickout(@ApiParam(value = "语言请求参数", required = false) Locale locale) {
//    return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), "用户被踢出了！");
//  }


}
