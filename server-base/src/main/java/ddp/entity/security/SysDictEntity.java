package ddp.entity.security;

import ddp.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;


/**
* 描述：数据字典表模型
* @author Ddp
* @date 2020-09-11 17:36:29
*/
@Entity
@Table(name = "sys_dict")
public class SysDictEntity extends BaseEntity {
    /**
    *主键ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dict_id", length = 19)
    private BigDecimal dictId;

    /**
    *大分类
    */
    @Column(name = "dict_type_big", length = 32)
    private String dictTypeBig;

    /**
    *小分类
    */
    @Column(name = "dict_type_small", length = 32)
    private String dictTypeSmall;

    /**
    *编码
    */
    @Column(name = "dict_code", length = 64)
    private String dictCode;

    /**
    *名称（英文）
    */
    @Column(name = "dict_name_en", length = 128)
    private String dictNameEn;

    /**
    *名称（中文）
    */
    @Column(name = "dict_name_zh", length = 256)
    private String dictNameZh;


    /**
    * 设置主键ID
    */
    public BigDecimal getDictId() {
        return this.dictId;
    }

    /**
    * 获取主键ID
    */
    public void setDictId(BigDecimal dictId) {
        this.dictId = dictId;
    }

    /**
    * 设置大分类
    */
    public String getDictTypeBig() {
        return this.dictTypeBig;
    }

    /**
    * 获取大分类
    */
    public void setDictTypeBig(String dictTypeBig) {
        this.dictTypeBig = dictTypeBig;
    }

    /**
    * 设置小分类
    */
    public String getDictTypeSmall() {
        return this.dictTypeSmall;
    }

    /**
    * 获取小分类
    */
    public void setDictTypeSmall(String dictTypeSmall) {
        this.dictTypeSmall = dictTypeSmall;
    }

    /**
    * 设置编码
    */
    public String getDictCode() {
        return this.dictCode;
    }

    /**
    * 获取编码
    */
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    /**
    * 设置名称（英文）
    */
    public String getDictNameEn() {
        return this.dictNameEn;
    }

    /**
    * 获取名称（英文）
    */
    public void setDictNameEn(String dictNameEn) {
        this.dictNameEn = dictNameEn;
    }

    /**
    * 设置名称（中文）
    */
    public String getDictNameZh() {
        return this.dictNameZh;
    }

    /**
    * 获取名称（中文）
    */
    public void setDictNameZh(String dictNameZh) {
        this.dictNameZh = dictNameZh;
    }




}