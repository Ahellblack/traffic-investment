package com.siti.comment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siti.comment.entity.CommentTopic;
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

    @Override
    public List<CommentTopicVo> allComment() {

        LoginUser loginUserInfo = loginCtrl.getLoginUserInfo();
        if (loginUserInfo != null) {
            List<CommentTopicVo> list = commentMapper.allComment(loginUserInfo.getId());
           /* list.stream().forEach(data -> {
                if ("ask".equals(data.getTopicType())) {
                    if (data.getPid() == null) {
                        List<CommentTopicVo> sublist = commentMapper.getSubList(data.getId());
                        data.setSubList(sublist);
                    }
                }
            });*/
            setSubList(list);
            return list;
        }
        return null;
    }

    public void setSubList(List<CommentTopicVo> list) {
        list.forEach(data -> {
            if ("ask".equals(data.getTopicType()) ) {
                List<CommentTopicVo> sublist = commentMapper.getSubList(data.getId());
                if (sublist != null) {
                    data.setSubList(sublist);
                    setSubList(sublist);
                }
            }
        });
    }
}
