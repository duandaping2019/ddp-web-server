package ddp.web.controller.system;

import ddp.service.security.SysRoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "角色管理类", value = "RoleRestController")
@RestController
@RequestMapping("/role")
public class RoleRestController {

    @Autowired
    private SysRoleService roleService;

}
