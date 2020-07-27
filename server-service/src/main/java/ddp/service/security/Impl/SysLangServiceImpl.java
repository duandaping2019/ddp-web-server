package ddp.service.security.Impl;

import ddp.BaseServiceImpl;
import ddp.entity.security.SysLangEntity;
import ddp.mapper.security.SysLangMapper;
import ddp.service.security.SysLangService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLangServiceImpl extends BaseServiceImpl<SysLangEntity> implements SysLangService {

  @Autowired
  private SysLangMapper langMapper;

  @Override
  public List<SysLangEntity> findAllInfo() {
    return langMapper.selectAll();
  }
}
