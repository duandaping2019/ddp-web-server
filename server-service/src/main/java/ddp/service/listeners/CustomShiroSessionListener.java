package ddp.service.listeners;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  Session监听控制器
 */
public class CustomShiroSessionListener implements SessionListener {

    private static final Logger logger = LoggerFactory.getLogger(CustomShiroSessionListener.class);

    //用于统计在线Session的数量
    private final AtomicInteger sessionCount = new AtomicInteger(0);

    /**
     *  对外调用
     */
    public void addSessionCount(){
        sessionCount.getAndIncrement();
    }

    public AtomicInteger getSessionCount(){
        return sessionCount;
    }

    @Override
    public void onStart(Session session) {
        // 不建议使用该处统计session增量【未登陆】
    }

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
        logger.info("用户登录人数减少一人" + sessionCount.get());
    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
        logger.info("用户登录过期一人" + sessionCount.get());
    }
}
