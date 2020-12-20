package ddp.web.controller.system;

import ddp.beans.BaseResponse;
import ddp.constants.CommConstants;
import ddp.service.security.SysRoleService;
import ddp.web.aop.OperLog;
import ddp.web.tools.MessageSourceUtils;
import ddp.web.tools.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Api(tags = "角色管理类", value = "RoleRestController")
@RestController
@RequestMapping("/role")
public class RoleRestController {

    @Autowired
    private SysRoleService roleService;

    @ApiOperation(value = "sysRoleSelect", notes = "获取角色信息列表")
    @RequestMapping("select")
    @OperLog(operModul = "角色管理", operType = CommConstants.GET_DATA, operDesc = "获取角色信息列表")
    @RequiresPermissions("sys:role:select")
    public BaseResponse<Object> sysRoleSelect(@ApiParam(value = "语言请求参数", required = false) Locale locale){
        BigDecimal userId = ShiroUtils.getCurrUserInfo().getUserId();
        List<Map<String ,Object>> roleList = roleService.sysRoleSelect(userId);
        return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), roleList);
    }

}
