package ddp.entity.security;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  系统国际化
 */
@Entity
@Table(name = "sys_lang")
public class SysLangEntity implements Serializable {
  /**
   *  用户主键
   *  IDENTITY：主键由数据库自动生成（主要是自动增长型） AUTO：主键由程序控制。
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lang_id", length = 32)
  private Long langId;

  /**
   *  语言编码
   */
  @Column(name = "lang_code", length = 32)
  private String langCode;

  /**
   *  简体中文
   */
  @Column(name = "lang_zh", length = 255)
  private String langZh;

  /**
   *  英文
   */
  @Column(name = "lang_en", length = 255)
  private String langEn;

  public Long getLangId() {
    return langId;
  }

  public void setLangId(Long langId) {
    this.langId = langId;
  }

  public String getLangCode() {
    return langCode;
  }

  public void setLangCode(String langCode) {
    this.langCode = langCode;
  }

  public String getLangZh() {
    return langZh;
  }

  public void setLangZh(String langZh) {
    this.langZh = langZh;
  }

  public String getLangEn() {
    return langEn;
  }

  public void setLangEn(String langEn) {
    this.langEn = langEn;
  }
}
