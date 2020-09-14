package com.siti.comment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentTopic {

  private long id;
  private long topicId;
  private String topicType;
  private String content;
  private long fromUid;
  private Date createTime;
  private long toUid;
  private String constructionCode;
  private int hasRead;


}
