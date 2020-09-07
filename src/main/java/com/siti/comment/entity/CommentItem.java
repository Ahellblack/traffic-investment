package com.siti.comment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author Solarie
 * @since 2020-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comment_item")
public class CommentItem extends Model<CommentItem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评论信息的主题id
     */
    private String topicId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论用户id
     */
    private Integer fromUid;

    /**
     * 指定查看用户
     */
    private Integer toUid;


    /**
     * 创建时间
     */
    private Date createTime;

    private int hasRead;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
