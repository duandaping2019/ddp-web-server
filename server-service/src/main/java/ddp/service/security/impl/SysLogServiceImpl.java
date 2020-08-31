package ddp.service.security.impl;

import ddp.BaseServiceImpl;
import ddp.entity.security.SysLogEntity;
import ddp.mapper.security.SysLogMapper;
import ddp.service.security.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLogEntity> implements SysLogService {

    @Autowired
    private SysLogMapper logMapper;

    @Override
    public void insertLogEntity(SysLogEntity logEntity) {
        logMapper.insert(logEntity);
    }

}
