package com.siti.comment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siti.comment.entity.CommentItem;
import com.siti.comment.mapper.CommentItemMapper;
import com.siti.comment.service.CommentItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Solarie
 * @since 2020-09-02
 */
@Service
public class CommentItemServiceImpl extends ServiceImpl<CommentItemMapper, CommentItem> implements CommentItemService {

}
