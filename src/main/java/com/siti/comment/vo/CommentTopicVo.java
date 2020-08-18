package com.siti.comment.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentTopicVo {

  private long id;
  private String topicType;
  private String content;
  private long fromUid;
  private java.sql.Timestamp createTime;
  private long toUid;
  private Long pid;

  private List<CommentTopicVo> subList;// 多级



}
