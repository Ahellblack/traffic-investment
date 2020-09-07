package com.siti.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.siti.comment.entity.CommentItem;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Solarie
 * @since 2020-09-02
 */
public interface CommentItemService extends IService<CommentItem> {

    int getLastInsertId(CommentItem commentItem);
}
