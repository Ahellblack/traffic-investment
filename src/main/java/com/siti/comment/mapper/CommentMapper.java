package com.siti.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siti.comment.entity.CommentTopic;
import com.siti.comment.vo.CommentTopicVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
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
            " <if test = \' type!= null \'>AND if(#{type} = \"remind\", topic_type in(\"near\",\"over\") , topic_type = #{type})</if>" +
            " <if test = \' hasRead!= null \'>AND has_read = #{hasRead} </if>" +
            " order by create_time desc " +
            "</script>")
    List<CommentTopicVo> allComment(@Param("userId") int id,@Param("constructionCode")String constructionCode,
            @Param("type") String type,@Param("hasRead")Integer hasRead);

    @Select("<script>SELECT " +
            " id,topic_type,content,create_time,to_uid,from_uid ,0,has_read " +
            " FROM " +
            " `comment_topic` ct  " +
            "WHERE " +
            "(IF( topic_type IN ( \"ask\", \"system\" ), " +
            "(to_uid = #{userId} OR FROM_UID = #{userId} OR to_uid IS NULL ), 1 = 1 ) )" +
            " <if test = \' constructionCode!= null \'>AND construction_code = #{constructionCode} </if>" +
            " <if test = \' type!= null \'>AND if(#{type} = \"remind\", topic_type in(\"near\",\"over\") , topic_type = #{type})</if>" +
            " <if test = \' hasRead!= null \'>AND has_read = #{hasRead} </if>" +
            " UNION " +
            " select id,\"ask\",content,create_time ,to_uid,from_uid ,topic_id,has_read " +
            " from comment_item WHERE has_read = #{hasRead} " +
            " and  topic_id in (select id from `comment_topic` where to_uid = #{userId}) " +
            " <if test = \' type!= null \'> and #{type} = \"ask\" </if> " +
            " order by create_time desc " +
            "</script>")
    List<CommentTopicVo> unread(@Param("userId") int id,@Param("constructionCode")String constructionCode,
                                    @Param("type") String type,@Param("hasRead")Integer hasRead);


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



        @Insert("INSERT INTO `comment_topic`" +
                "(`topic_type`, `content`, `from_uid`, `to_uid`, `construction_code`, `has_read`) " +
                "VALUES (#{comment.topicType}, #{comment.content}, #{comment.fromUid}, #{comment.toUid}, #{comment.constructionCode}, 0)")
        @Options(useGeneratedKeys=true, keyProperty="comment.id", keyColumn="id")
        int getLastInsertId(@Param("comment") CommentTopic comment);



}
