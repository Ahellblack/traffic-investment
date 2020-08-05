package com.siti.common.base;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ht on 2017/12/22.
 */
public class BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 创建人ID
     */
    private Integer createBy;
    /**
     * 创建人名称
     */
    @Transient
    private String createName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人ID
     */
    private Integer updateBy;
    /**
     * 更新人名称
     */
    @Transient
    private String updateName;
    /**
     * 更新时间
     */
    private Date updateTime;


    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
