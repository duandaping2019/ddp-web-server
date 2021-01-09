package ddp.web.controller.system;

import com.github.pagehelper.PageInfo;
import ddp.beans.BaseResponse;
import ddp.beans.CommConstants;
import ddp.entity.security.SysMenuEntity;
import ddp.ext.security.SysMenuExt;
import ddp.service.security.SysMenuService;
import ddp.service.tools.MessageSourceUtils;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Api(tags = "菜单管理类", value = "MenuRestController")
@RestController
@RequestMapping("/menu")
public class MenuRestController {
    @Autowired
    private SysMenuService menuService;

    @ApiOperation(value = "sysMenuList", notes = "获取菜单信息列表")
    @RequestMapping("list")
    @OperLog(operModul = "菜单管理", operType = CommConstants.GET_DATA, operDesc = "获取菜单信息列表")
    @RequiresPermissions("sys:menu:list")
    public BaseResponse<Object> sysMenuList(@ApiParam(value = "菜单请求参数", required = false) @RequestBody SysMenuExt ext,
                                            @ApiParam(value = "菜单请求对象", required = false) HttpServletRequest request,
                                            @ApiParam(value = "语言请求参数", required = false) Locale locale){

        MyPageUtils.setPageInfo(request); //分页设定
        return BaseResponse.success(new PageInfo(menuService.getExtListInfo(ext)));
    }

    @ApiOperation(value = "sysMenuSelect", notes = "获取菜单树信息")
    @RequestMapping("select")
    @OperLog(operModul = "菜单管理", operType = CommConstants.GET_DATA, operDesc = "获取菜单树信息")
    @RequiresPermissions("sys:menu:select")
    public BaseResponse<Object> sysMenuSelect(@ApiParam(value = "菜单请求参数", required = false) @RequestBody SysMenuExt ext,
                                              @ApiParam(value = "语言请求参数", required = false) Locale locale){
        return BaseResponse.success(new PageInfo(menuService.getMenuTreeData(ext)));
    }

    @ApiOperation(value = "saveOrUpdate", notes = "存储菜单信息")
    @RequestMapping("/save_or_update")
    @OperLog(operModul = "系统管理", operType = CommConstants.ADD_DATA, operDesc = "存储菜单信息")
    @RequiresPermissions("sys:menu:save")
    public BaseResponse<Object> saveOrUpdate(@ApiParam(value = "菜单请求参数", required = false) @RequestBody SysMenuExt ext,
                                             @ApiParam(value = "语言请求参数", required = false) Locale locale) {

        // 更新菜单信息
        SysMenuEntity entity = menuService.saveOrUpdate(ext);
        return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), entity);

    }

    @ApiOperation(value = "getMenuInfo", notes = "获取菜单信息")
    @RequestMapping("/get_menu_info")
    @OperLog(operModul = "系统管理", operType = CommConstants.GET_DATA, operDesc = "获取菜单信息")
    @RequiresPermissions("sys:menu:info")
    public BaseResponse<Object> getMenuInfo(@ApiParam(value = "菜单请求参数", required = false) @RequestBody SysMenuExt ext,
                                            @ApiParam(value = "语言请求参数", required = false) Locale locale) {

        SysMenuExt menuInfo = menuService.getExtInfo(ext);
        return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), menuInfo);
    }


    @ApiOperation(value = "delMenuInfo", notes = "删除菜单信息")
    @RequestMapping("/del_menu_info")
    @OperLog(operModul = "系统管理", operType = CommConstants.DEL_DATA, operDesc = "删除菜单信息")
    @RequiresPermissions("sys:menu:delete")
    public BaseResponse<Object> delMenuInfo(@ApiParam(value = "菜单请求参数", required = false) @RequestBody List<BigDecimal> idsList,
                                            @ApiParam(value = "语言请求参数", required = false) Locale locale) {

        menuService.delMenuInfo(idsList);
        return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale));
    }

}