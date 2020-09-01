package com.siti.comment.controller;

import com.siti.comment.service.ICommentService;
import com.siti.comment.vo.CommentTopicVo;
import com.siti.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 12293 on 2020/8/17.
 */
@RestController
@RequestMapping("comment")
@Api(tags = "项目问询，通知")
public class CommentController {

    @Resource
    ICommentService icommentService;

    /***
     * 查询本人所有未读的项目信息
     * */
    @GetMapping("all")
    @ApiOperation(value = "获取项目问询列表", notes = "获取项目问询列表")
    public Result<?> allComment(){
        List<CommentTopicVo> commentTopicVos = icommentService.allComment();
        return Result.ok(commentTopicVos);
    }



}
