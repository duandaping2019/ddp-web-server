package ddp.web.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注解类
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented
public @interface OperLog {

    /**
     * 操作模块
     */
    String operModul() default "";

    /**
     * 操作类型
     */
    String operType() default "";

    /**
     * 操作说明
     */
    String operDesc() default "";

}
