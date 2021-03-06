package ddp.service.security;

import ddp.BaseServiceImpl;
import ddp.entity.security.SysLangEntity;
import ddp.mapper.security.SysLangMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLangServiceImpl extends BaseServiceImpl<SysLangEntity> implements SysLangService {

  @Autowired
  private SysLangMapper langMapper;

  @Override
  public List<SysLangEntity> findAllInfo() {
    return langMapper.selectAll();
  }
}
