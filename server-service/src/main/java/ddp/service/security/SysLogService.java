package ddp.service.security;

import ddp.BaseService;
import ddp.entity.security.SysLogEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统日志服务类
 */
public interface SysLogService extends BaseService<SysLogEntity> {

    /**
     * 数据新增
     */
    @Transactional
    void insertLogEntity(SysLogEntity logEntity);
}
