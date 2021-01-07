package ddp.web.controller.system;

import com.github.pagehelper.PageInfo;
import ddp.beans.BaseResponse;
import ddp.beans.CommConstants;
import ddp.ext.security.SysMenuExt;
import ddp.service.security.SysMenuService;
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

}
