package ddp.web.controller.system;

import ddp.constants.CommConstants;
import ddp.service.security.SysRoleService;
import ddp.web.aop.OperLog;
import ddp.web.tools.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
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
    public Map<String, List<Map<String ,Object>>> sysRoleSelect(){
        Map<String, List<Map<String ,Object>>> map = new HashMap<>();
        List<Map<String ,Object>> roleList = null;

        BigDecimal userId = ShiroUtils.getCurrUserInfo().getUserId();
        if (userId.compareTo(new BigDecimal("1")) == 0){ //超级管理员
            roleList = roleService.querySuperRoleList();
        } else { // 自己所拥有的角色列表
            roleList = roleService.queryUserRoleList(userId);
        }

        map.put("roleList", roleList);
        return map;
    }

}
