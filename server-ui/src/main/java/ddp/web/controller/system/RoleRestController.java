package ddp.web.controller.system;

import com.github.pagehelper.PageInfo;
import ddp.beans.BaseResponse;
import ddp.beans.CommConstants;
import ddp.ext.security.SysRoleExt;
import ddp.service.security.SysRoleService;
import ddp.utils.MyPageUtils;
import ddp.web.aop.OperLog;
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

@Api(tags = "角色管理类", value = "RoleRestController")
@RestController
@RequestMapping("/role")
public class RoleRestController {

    @Autowired
    private SysRoleService roleService;

    @ApiOperation(value = "sysRoleList", notes = "获取角色信息列表")
    @RequestMapping("list")
    @OperLog(operModul = "角色管理", operType = CommConstants.GET_DATA, operDesc = "获取角色信息列表")
    @RequiresPermissions("sys:role:list")
    public BaseResponse<Object> sysRoleList(@ApiParam(value = "角色请求参数", required = false) @RequestBody SysRoleExt ext,
                                            @ApiParam(value = "角色请求对象", required = false) HttpServletRequest request,
                                            @ApiParam(value = "语言请求参数", required = false) Locale locale){

        MyPageUtils.setPageInfo(request); //分页设定
        return BaseResponse.success(new PageInfo(roleService.getExtListInfo(ext)));
    }

//    @ApiOperation(value = "sysMenuSave", notes = "存储角色信息")
//    @RequestMapping("/save_or_update")
//    @OperLog(operModul = "系统管理", operType = CommConstants.ADD_DATA, operDesc = "存储角色信息")
//    @RequiresPermissions("sys:role:save")
//    public BaseResponse<Object> sysMenuSave(@ApiParam(value = "角色请求参数", required = false) @RequestBody SysRoleExt ext,
//                                            @ApiParam(value = "语言请求参数", required = false) Locale locale) {
//
//        SysMenuEntity entity = roleService.saveOrUpdate(ext);
//        return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), entity);
//
//    }
    
//    @ApiOperation(value = "getMenuInfo", notes = "获取角色信息")
//    @RequestMapping("/get_menu_info")
//    @OperLog(operModul = "系统管理", operType = CommConstants.GET_DATA, operDesc = "获取角色信息")
//    @RequiresPermissions("sys:role:info")
//    public BaseResponse<Object> getMenuInfo(@ApiParam(value = "角色请求参数", required = false) @RequestBody SysRoleExt ext,
//                                            @ApiParam(value = "语言请求参数", required = false) Locale locale) {
//
//        SysRoleExt menuInfo = roleService.getExtInfo(ext);
//        return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), menuInfo);
//    }
    
    
}
