package com.siti.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.siti.comment.entity.CommentTopic;
import com.siti.comment.vo.CommentTopicVo;

import java.util.List;

/**
 * Created by 12293 on 2020/8/17.
 */

public interface ICommentService extends IService<CommentTopic> {
    List<CommentTopicVo> allComment();

}
