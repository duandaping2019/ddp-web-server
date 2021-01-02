package ddp.service.listeners;

import ddp.service.security.SysIndexService;
import ddp.utils.spring.SpringBeanUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 *  Session监听控制器
 */
public class CustomShiroSessionListener implements SessionListener {

    @Override
    public void onStart(Session session) {
        // 不建议使用该处统计session增量【详见登陆】
    }

    @Override
    public void onStop(Session session) {
        SysIndexService sysIndexService = SpringBeanUtils.getBean(SysIndexService.class);
        sysIndexService.deleteshirosession(session.getId().toString());
    }

    @Override
    public void onExpiration(Session session) {
        SysIndexService sysIndexService = SpringBeanUtils.getBean(SysIndexService.class);
        sysIndexService.deleteshirosession(session.getId().toString());
    }
}
