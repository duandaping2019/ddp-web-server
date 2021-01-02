package ddp.web.beans;

import ddp.service.security.SysIndexService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 系统资源初始化
 */
@Component
public class MyInitializingBean implements InitializingBean {

    @Autowired
    private SysIndexService sysIndexService;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 在线用户清零操作
        sysIndexService.truncateshirosession();
    }

}
