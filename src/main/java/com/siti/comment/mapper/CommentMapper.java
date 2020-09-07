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
    @Select("<script>SELECT " +
            " *  " +
            "FROM " +
            " `comment_topic` ct  " +
            "WHERE " +
            "(IF( topic_type IN ( \"ask\", \"system\" ), " +
            "(to_uid = #{userId} OR FROM_UID = #{userId} OR to_uid IS NULL ), 1 = 1 ) )" +
            " <if test = \' constructionCode!= null \'>AND construction_code = #{constructionCode} </if>" +
            " <if test = \' type!= null \'>AND topic_type = #{type} </if>" +
            "</script>")
    List<CommentTopicVo> allComment(@Param("userId") int id,@Param("constructionCode")String constructionCode,
            @Param("type") String type);

    @Select("SELECT * FROM comment_topic where pid = #{userId}")
    List<CommentTopicVo> getSubList(@Param("userId")long id);

    @Select("<script>SELECT " +
            " *  " +
            "FROM " +
            " `comment_topic` ct  " +
            "WHERE " +
            " FROM_UID = #{userId}" +
            " <if test = \' constructionCode!= null \'>AND construction_code = #{constructionCode} </if>" +
            " <if test = \' type!= null \'>AND topic_type = #{type} </if>" +
            "</script>")
    List<CommentTopicVo> allByMine(@Param("userId") int id,@Param("constructionCode")String constructionCode,
                                   @Param("type") String type);
}
