package com.siti.comment.controller;


import com.siti.comment.entity.CommentItem;
import com.siti.comment.service.CommentItemService;
import com.siti.common.AutoLog;
import com.siti.common.Result;
import com.siti.common.vo.LoginUser;
import com.siti.system.ctrl.LoginCtrl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Solarie
 * @since 2020-09-02
 */
@RestController
@RequestMapping("/comment-item")
@Api(tags = "项目问询，评论")
public class CommentItemController {


    @Resource
    LoginCtrl loginCtrl;
    @Resource
    CommentItemService commentItemService;


    /**
     * 添加
     *
     * @param commentItem
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加评论")
    @ApiOperation(value = "添加评论", notes = "添加评论")
    public Result<?> add(@RequestBody CommentItem commentItem) {

        LoginUser loginUserInfo = loginCtrl.getLoginUserInfo();
        if (commentItem.getTopicId() != null) {
            commentItem.setFromUid(loginUserInfo.getId());
            // commentItemService.save(commentItem);
            int i = commentItemService.getLastInsertId(commentItem);
            if (i != 0) {
                return Result.ok("添加主题成功！", commentItemService.getById(commentItem.getId()));
            }
        }
        return Result.ok("添加评论成功！", commentItem);
    }

    /**
     * 删除评论
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/delete")
    @AutoLog(value = "删除评论（仅限本人发起）")
    @ApiOperation(value = "删除评论", notes = "删除评论")
    public Result<?> delete(int id) {

        LoginUser loginUserInfo = loginCtrl.getLoginUserInfo();
        CommentItem item = commentItemService.getById(id);
        if (item.getFromUid() == loginUserInfo.getId()) {
            commentItemService.removeById(id);
            return Result.ok("删除评论成功！");
        }
        return Result.ok("该评论无法删除！");
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getItem")
    @AutoLog(value = "详情")
    @ApiOperation(value = "详情", notes = "详情")
    public Result<?> getItem(Integer id) {
        CommentItem comment = commentItemService.getById(id);
        if (comment.getHasRead() != 1) {
            comment.setHasRead(1);
            boolean b = commentItemService.updateById(comment);
            if (b) {
                return Result.ok("获取详情成功", comment);
            }
            return Result.ok("已读状态修改失败");
        }
        return Result.ok("获取详情成功", comment);
    }
}
