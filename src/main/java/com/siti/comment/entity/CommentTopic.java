package com.siti.comment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentTopic {

  private long id;
  private String topicType;
  private String content;
  private long fromUid;
  private java.sql.Timestamp createTime;
  private long toUid;
  private Long pid;



}
