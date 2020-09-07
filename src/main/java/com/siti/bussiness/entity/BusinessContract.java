package com.siti.bussiness.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Solarie
 * @since 2020-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BusinessContract extends Model<BusinessContract> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 合同名称
     */
    private String cName;

    /**
     * 合同类别
     */
    private String cType;

    /**
     * 合同编号
     */
    private String cCode;

    /**
     * 甲方单位
     */
    private String aIdentity;

    /**
     * 乙方单位
     */
    private String bIdentity;

    /**
     * 丙方单位
     */
    private String cIdentity;

    /**
     * 资金流向
     */
    private String fundsDirec;

    /**
     * 合同金额
     */
    private Double payInfo;

    /**
     * 税率
     */
    private Double taxRate;

    /**
     * 税款
     */
    private Double tax;

    /**
     * 税后金额
     */
    private Double finalPayInfo;

    /**
     * 合同状态
     */
    private String cState;

    /**
     * 是否变更
     */
    private String isChange;

    /**
     * 是否作废
     */
    private String isDestory;

    /**
     * 承办用户id
     */
    private Integer cUserId;

    /**
     * 承办用户名称
     */
    private String cUserName;

    /**
     * 合同生效时间
     */
    private LocalDateTime cEffictTime;

    /**
     * 合同失效时间
     */
    private LocalDateTime cEndTime;

    /**
     * 合同签订时间
     */
    private LocalDateTime cAuditTime;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
