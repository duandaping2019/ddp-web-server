package ddp.web.controller.system;

import ddp.service.security.SysMenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "菜单管理类", value = "MenuRestController")
@RestController
@RequestMapping("/menu")
public class MenuRestController {
    @Autowired
    private SysMenuService menuService;

}
