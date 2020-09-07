package com.siti.comment.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siti.comment.entity.CommentTopic;
import com.siti.comment.service.ICommentService;
import com.siti.comment.vo.CommentTopicVo;
import com.siti.common.AutoLog;
import com.siti.common.Result;
import com.siti.common.vo.LoginUser;
import com.siti.system.ctrl.LoginCtrl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
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
    @Resource
    LoginCtrl loginCtrl;

    /***
     * 查询本人所有未读的项目信息
     * */
    @GetMapping("all")
    @ApiOperation(value = "获取项目问询列表", notes = "获取项目问询列表")
    public Result<?> allComment(String constructionCode, String type,
                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, Integer hasRead) {
        List<CommentTopicVo> commentTopicVos = icommentService.allComment(constructionCode, type,hasRead);

        /**
         *手动添加分页 测试
         */
        Page<CommentTopicVo> page = new Page<>(pageNo, pageSize);
        int count = commentTopicVos.size();
        List<CommentTopicVo> pageList = new ArrayList<>();
        //计算当前页第一条数据的下标
        int currId = pageNo > 1 ? (pageNo - 1) * pageSize : 0;
        for (int i = 0; i < pageSize && i < count - currId; i++) {
            pageList.add(commentTopicVos.get(currId + i));
        }
        page.setSize(pageSize);
        page.setCurrent(pageNo);
        page.setTotal(count);
        //计算分页总页数
        page.setPages(count % 10 == 0 ? count / 10 : count / 10 + 1);
        page.setRecords(pageList);
        return Result.ok(page);
    }

    /***
     * 查询本人所有未读的项目信息
     * */
    @GetMapping("unread")
    @ApiOperation(value = "获取未读消息", notes = "获取未读消息")
    public Result<?> unread(String constructionCode, String type) {
        HashMap map = new HashMap<String,Object>();
        List<CommentTopicVo> commentTopicVos = icommentService.unread(constructionCode, type);
        map.put("comment",commentTopicVos);
        map.put("num",commentTopicVos.size());
        return Result.ok(map);
    }


    /***
     * 查询本人所有未读的项目信息
     * */
    @GetMapping("allByMine")
    @ApiOperation(value = "我发出的", notes = "我发出的")
    public Result<?> allByMine(String constructionCode, String type,
                               @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        List<CommentTopicVo> commentTopicVos = icommentService.allByMine(constructionCode);

        /**
         *手动添加分页 测试#TODO
         */
        Page<CommentTopicVo> page = new Page<>(pageNo, pageSize);
        int count = commentTopicVos.size();
        List<CommentTopicVo> pageList = new ArrayList<>();
        //计算当前页第一条数据的下标
        int currId = pageNo > 1 ? (pageNo - 1) * pageSize : 0;
        for (int i = 0; i < pageSize && i < count - currId; i++) {
            pageList.add(commentTopicVos.get(currId + i));
        }
        page.setSize(pageSize);
        page.setCurrent(pageNo);
        page.setTotal(count);
        //计算分页总页数
        page.setPages(count % 10 == 0 ? count / 10 : count / 10 + 1);
        page.setRecords(pageList);
        return Result.ok(page);

    }


    /**
     * 添加
     *
     * @param commentTopic
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加主题")
    @ApiOperation(value = "添加主题", notes = "添加主题")
    public Result<?> add(@RequestBody CommentTopic commentTopic) {
        LoginUser loginUserInfo = loginCtrl.getLoginUserInfo();
        commentTopic.setFromUid(loginUserInfo.getId());
        //icommentService.save(commentTopic);
        int i = icommentService.getLastInsertId(commentTopic);
        if (i != 0) {
            return Result.ok("添加主题成功！", icommentService.getById(commentTopic.getId()));
        }
        return Result.ok("添加主题失败，检查参数！", commentTopic);
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
        CommentTopic item = icommentService.getById(id);
        if (item.getTopicType() == null || !"ask".equals(item.getTopicType())) {
            return Result.ok("无权限删除该记录");
        }
        if (item.getFromUid() == loginUserInfo.getId()) {
            icommentService.removeById(id);
            return Result.ok("删除评论成功！");
        }
        return Result.ok("该评论无法删除！");
    }

    /**
     * 主题信息详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getTopic")
    @AutoLog(value = "主题信息详情")
    @ApiOperation(value = "主题信息详情", notes = "主题信息详情")
    public Result<?> getTopic(Integer id) {
        CommentTopic comment = icommentService.getById(id);
        if (comment.getHasRead() != 1) {
            comment.setHasRead(1);
            boolean b = icommentService.updateById(comment);
            if (b) {
                return Result.ok("获取详情成功", comment);
            }
            return Result.ok("已读状态修改失败");
        }
        return Result.ok("获取详情成功", comment);
    }


    /**
     * 批量删除评论
     *
     * @param ids
     * @return
     */
    @GetMapping(value = "/batchDelete")
    @AutoLog(value = "批量删除评论（仅限本人发起）")
    @ApiOperation(value = "批量删除评论", notes = "批量删除评论")
    public Result<?> batchDelete(List<Integer> ids) {

        if(ids!=null && ids.size()!=0)
            ids.forEach(id->{
                delete(id);
            });
        return Result.ok("批量删除异常 检查参数！");
    }

    /**
     * 全部已读
     *
     * @param ids
     * @return
     */
    @GetMapping(value = "/batchRead")
    @AutoLog(value = "全部已读")
    @ApiOperation(value = "全部已读", notes = "全部已读")
    public Result<?> batchRead(List<Integer> ids) {

        if(ids!=null && ids.size()!=0)
            ids.forEach(id->{
                CommentTopic comment = icommentService.getById(id);
                if (comment.getHasRead() != 1) {
                    comment.setHasRead(1);
                    icommentService.updateById(comment);
                }
            });
        return Result.ok("全部已读完成");
    }

}
