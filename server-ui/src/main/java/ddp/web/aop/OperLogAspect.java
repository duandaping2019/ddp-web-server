package ddp.web.aop;

import ddp.entity.security.SysLogEntity;
import ddp.ext.security.SysUserExt;
import ddp.service.security.SysLogService;
import ddp.service.security.SysUserService;
import ddp.tools.HttpInfoUtils;
import ddp.tools.MyStringUtils;
import ddp.web.tools.ShiroUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 操作日志切面处理类
 */
@Aspect
@Component
public class OperLogAspect {

    @Autowired
    private SysLogService logService;

    @Autowired
    private SysUserService userService;

    /**
     * 设置操作日志切入点
     */
    @Pointcut("@annotation(ddp.web.aop.OperLog)")
    public void operLogPoinCut() {}

    /**
     * 设置操作异常切入点记录异常日志
     */
    @Pointcut("execution(* ddp.web.controller..*.*(..))")
    public void operExceptionLogPoinCut() {}

    /**
     * 保存操作日志
     */
    @AfterReturning(value = "operLogPoinCut()", returning = "keys")
    public void saveOperLog(JoinPoint joinPoint, Object keys) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                                    .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OperLog opLog = method.getAnnotation(OperLog.class);

            if (opLog != null) {
                SysLogEntity logEntity = new SysLogEntity();
                logEntity.setLogId(MyStringUtils.getUUID()); //获取主键
                logEntity.setLogModel(opLog.operModul()); //操作模块
                logEntity.setLogType(opLog.operType()); //操作类型
                logEntity.setLogContent(opLog.operDesc()); //操作说明
                logEntity.setLogReqUri(request.getRequestURI()); //请求URI
                logEntity.setLogReqClass(joinPoint.getTarget().getClass().getName()); //请求类名
                logEntity.setLogReqMethod(method.getName()); //请求方法名
                logEntity.setLogIp(HttpInfoUtils.getIpAdrress(request)); //操作人IP
                SysUserExt currUser = userService.findByLoginId(ShiroUtils.getCurrUserInfo().getLoginId());
                logEntity.setLogOperatorId(currUser.getUserId().toString()); //操作人ID
                logEntity.setLogOperatorName(currUser.getUserName()); //操作人Name
                logEntity.setLogTime(new Date()); //操作时间

                logService.insertLogEntity(logEntity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
