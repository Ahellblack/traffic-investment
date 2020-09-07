package com.siti.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siti.comment.entity.CommentItem;
import com.siti.comment.entity.CommentTopic;
import com.siti.comment.mapper.CommentItemMapper;
import com.siti.comment.mapper.CommentMapper;
import com.siti.comment.service.ICommentService;
import com.siti.comment.vo.CommentTopicVo;
import com.siti.common.vo.LoginUser;
import com.siti.system.ctrl.LoginCtrl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 12293 on 2020/8/17.
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentTopic> implements ICommentService {

    @Resource
    LoginCtrl loginCtrl;
    @Resource
    CommentMapper commentMapper;
    @Resource
    CommentItemMapper commentItemMapper;

    @Override
    public List<CommentTopicVo> allComment(String constructionCode,String type) {

        LoginUser loginUserInfo = loginCtrl.getLoginUserInfo();
        if (loginUserInfo != null) {
            List<CommentTopicVo> list = commentMapper.allComment(loginUserInfo.getId(),constructionCode,type);
            list.forEach(data -> {
                if ("ask".equals(data.getTopicType())) {
                    QueryWrapper<CommentItem> queryWrapper = new QueryWrapper();

                    queryWrapper.eq("topic_id", data.getId());
                    List<CommentItem> sublist = commentItemMapper.selectList(queryWrapper);
                    if (sublist != null) {
                        data.setSubList(sublist);
                    }
                }
            });
            return list;
        }
        return null;
    }

    @Override
    public List<CommentTopicVo> allByMine(String constructionCode) {
        LoginUser loginUserInfo = loginCtrl.getLoginUserInfo();
        if (loginUserInfo != null) {
            List<CommentTopicVo> list = commentMapper.allByMine(loginUserInfo.getId(),constructionCode,"ask");
            list.forEach(data -> {
                if ("ask".equals(data.getTopicType())) {
                    QueryWrapper<CommentItem> queryWrapper = new QueryWrapper();

                    queryWrapper.eq("topic_id", data.getId());
                    List<CommentItem> sublist = commentItemMapper.selectList(queryWrapper);
                    if (sublist != null) {
                        data.setSubList(sublist);
                    }
                }
            });
            return list;
        }
        return null;
    }


}
