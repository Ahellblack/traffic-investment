package com.siti.bussiness.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class BusinessTenderApply extends Model<BusinessTenderApply> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 招标发起部门
     */
    private String iniDepart;

    /**
     * 招标采购编号
     */
    private String tenderCode;

    /**
     * 采购项目
     */
    private String purchaseItem;

    /**
     * 采购内容
     */
    private String purchaseContent;

    /**
     * 预估采购金额（万元）
     */
    private Float purchaseMoney;

    /**
     * 采购资金来源，1.财政资金预算2集团资金3.公司自有资金4.其他
     */
    private String purchaseResource;

    /**
     * 招标采购方式，1.法定公开招标2.公开招标3.比选4.直接委托5.其他
     */
    private String purchaseWay;

    /**
     * 供应商来源，1.供应商名录2.交易平台3.其他
     */
    private String supplyResource;

    /**
     * 发起部门经办人
     */
    private String initDepartOperator;

    /**
     * 发起部门部门经理
     */
    private String initDepartManager;

    /**
     * 计划合约部门经办人
     */
    private String planDepartOperator;

    /**
     * 计划合约部门部门经理
     */
    private String planDepartManager;

    /**
     * 财务部门
     */
    private String financeDepart;

    /**
     * 发起部门分管领导
     */
    private String initDepartHead;

    /**
     * 计合部部门分管领导
     */
    private String planDepartHead;

    /**
     * 总经理
     */
    private String generalManager;

    /**
     * 招标采购时间
     */
    private String purchaseTime;

    /**
     * 供应商名称仅供非公开招标使用
     */
    private String supplyName;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
