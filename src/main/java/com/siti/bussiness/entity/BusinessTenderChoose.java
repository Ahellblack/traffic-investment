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
 * 招标比选
 * </p>
 *
 * @author Solarie
 * @since 2020-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BusinessTenderChoose extends Model<BusinessTenderChoose> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long id;

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
     * 项目限价（万元）
     */
    private Float itemLimit;

    /**
     * 招标采购方式
     */
    private String purchaseWay;

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
