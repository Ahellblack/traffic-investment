package com.siti.contract.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowLink {

  private long id;
  private long workflowId;
  private String workflowLinkName;
  private String workflowLinkPreNode;
  private String workflowLinkNextNode;




}
