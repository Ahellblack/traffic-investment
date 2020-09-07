package com.siti.comment.vo;

import com.siti.comment.entity.CommentItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
  private Date createTime;
  private long toUid;
  private int hasRead;
  private List<CommentItem> subList;// 多级



}
