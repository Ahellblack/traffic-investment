package com.siti.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siti.comment.entity.CommentItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Solarie
 * @since 2020-09-02
 */
public interface CommentItemMapper extends BaseMapper<CommentItem> {

    @Insert("INSERT INTO `comment_item`" +
            "(`topic_id`, `content`, `from_uid`, `to_uid`,  `has_read`) " +
            "VALUES (#{comment.topicId}, #{comment.content}, #{comment.fromUid}, #{comment.toUid},  0)")
    @Options(useGeneratedKeys=true, keyProperty="comment.id", keyColumn="id")
    int getLastInsertId(@Param("comment") CommentItem commentItem);
}
