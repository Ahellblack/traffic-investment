package com.siti.contract.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Approver {

  private long id;
  private long workflowId;
  private long nodeId;
  private long userId;
  private long roleId;
  private long orgId;


}
