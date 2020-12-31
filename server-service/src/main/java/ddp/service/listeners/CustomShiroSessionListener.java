package ddp.service.listeners;

import ddp.service.security.SysIndexService;
import ddp.tools.SpringBeanUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  Session监听控制器
 */
public class CustomShiroSessionListener implements SessionListener {

    //用于统计在线Session的数量
    private final AtomicInteger sessionCount = new AtomicInteger(0);

    // 增加在线用户数
    public void addSessionCount(){
        sessionCount.getAndIncrement();
    }

    // 获取在线用户数
    public AtomicInteger getSessionCount(){
        return sessionCount;
    }

    @Override
    public void onStart(Session session) {
        // 不建议使用该处统计session增量【未登陆】
    }

    @Override
    public void onStop(Session session) {
        SysIndexService sysIndexService = SpringBeanUtils.getBean(SysIndexService.class);
        sysIndexService.deleteshirosession(session.getId().toString());
        sessionCount.decrementAndGet();
    }

    @Override
    public void onExpiration(Session session) {
        SysIndexService sysIndexService = SpringBeanUtils.getBean(SysIndexService.class);
        sysIndexService.deleteshirosession(session.getId().toString());
        sessionCount.decrementAndGet();
    }
}
