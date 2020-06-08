package com.siti.contract.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contract {

  private long id;
  private String cName;
  private String cType;
  private String cCode;
  private String aIdentity;
  private String bIdentity;
  private String cIdentity;
  private String fundsDirec;
  private double payInfo;
  private double taxRate;
  private double tax;
  private double finalPayInfo;
  private String cState;
  private String isChange;
  private String isDestory;
  private long cUserId;
  private String cUserName;
  private java.sql.Timestamp cEffictTime;
  private java.sql.Timestamp cEndTime;
  private java.sql.Timestamp cAuditTime;
  private String remark;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;

}
