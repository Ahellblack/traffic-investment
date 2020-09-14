package com.siti.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.siti.comment.entity.CommentTopic;
import com.siti.comment.service.ICommentService;
import com.siti.construction.entity.BusinessConstruction;
import com.siti.construction.service.IConstructionService;
import com.siti.utils.DateUtils;
import com.siti.workflow.entity.WorkflowReal;
import com.siti.workflow.entity.WorkflowRealTaskProgress;
import com.siti.workflow.service.IWorkflowRealService;
import com.siti.workflow.service.IWorkflowRealTaskProgressService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 12293 on 2020/9/14.
 */
public class CreateMsgTask {

    @Resource
    ICommentService iCommentService;
    @Resource
    IWorkflowRealService iWorkflowRealService;
    @Resource
    IWorkflowRealTaskProgressService iWorkflowRealTaskProgressService;
    @Resource
    IConstructionService iConstructionService;

    public void createRemindMsg() {
        // #TODO 发送次数限制
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type", 1);
        queryWrapper.eq("status", 1);
        List<WorkflowReal> list = iWorkflowRealService.list(queryWrapper);
        try {
            list.forEach(data -> {
                QueryWrapper<WorkflowRealTaskProgress> iwrtpWrapper = new QueryWrapper();
                iwrtpWrapper.eq("sheet_code", data.getSheetCode());
                List<WorkflowRealTaskProgress> alltask = iWorkflowRealTaskProgressService.list(iwrtpWrapper)
                        .stream().filter(task -> task.getStatus() != 1)
                        .filter(task -> task.getFinalTime() != null)
                        .collect(Collectors.toList());
                for (WorkflowRealTaskProgress rtask : alltask) {

                    if (DateUtils.str2Date2(rtask.getFinalTime()).after(new Date())) {
                        BusinessConstruction construction = iConstructionService.getById(rtask.getConstructionCode());
                        String content = new StringBuilder("您的").append(construction.getConstructionName()).append("项目中,")
                                .append(rtask.getTaskName()).append(" 任务已逾期，请尽快处理").toString();
                        int uid = 0;
                        //分别发送给前期经办人和工程经办人
                        if (rtask.getType() == 1) {
                            uid = construction.getPmId();
                        } else if (rtask.getType() == 2) {
                            uid = construction.getEnginPmId();
                        }
                        CommentTopic commentTopic = new CommentTopic().builder().topicType("over")
                                .constructionCode(rtask.getConstructionCode())
                                .hasRead(0).content(content).toUid(uid).fromUid(1).build();
                        iCommentService.save(commentTopic);
                    } else if (new Date().getTime() - DateUtils.str2Date2(rtask.getFinalTime()).getTime() < 3 * 24 * 60 * 60 * 1000) {

                        BusinessConstruction construction = iConstructionService.getById(rtask.getConstructionCode());
                        String content = new StringBuilder("您的").append(construction.getConstructionName()).append("项目中,")
                                .append(rtask.getTaskName()).append(" 任务已临近期限，请尽快处理").toString();
                        int uid = 0;
                        //分别发送给前期经办人和工程经办人
                        if (rtask.getType() == 1) {
                            uid = construction.getPmId();
                        } else if (rtask.getType() == 2) {
                            uid = construction.getEnginPmId();
                        }
                        CommentTopic commentTopic = new CommentTopic().builder().topicType("near")
                                .constructionCode(rtask.getConstructionCode())
                                .hasRead(0).content(content).toUid(uid).fromUid(1).build();
                        iCommentService.save(commentTopic);

                    }
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
