package com.siti.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siti.comment.entity.CommentTopic;
import com.siti.comment.vo.CommentTopicVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 12293 on 2020/8/17.
 */
public interface CommentMapper extends BaseMapper<CommentTopic> {
    @Select("SELECT " +
            " *  " +
            "FROM " +
            " `comment_topic` ct  " +
            "WHERE " +
            " (to_uid = #{userId}  " +
            " OR FROM_UID = #{userId}  " +
            " OR to_uid IS NULL )" +
            " AND pid IS NULL ")
    List<CommentTopicVo> allComment(@Param("userId") int id);

    @Select("SELECT * FROM comment_topic where pid = #{userId}")
    List<CommentTopicVo> getSubList(@Param("userId")long id);
}
