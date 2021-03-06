package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.video.PostVideo;
import com.tj720.model.common.wf.WfAction;
import com.tj720.utils.common.ApplicationContextUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/26 15:07
 */
public class MyTask extends TimerTask{
    private String processId;
    private String currentUserId;
    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        System.out.println("定时任务执行开始");
        WfAction wfAction = new WfAction();
        wfAction.setXid(processId);
        wfAction.setApplyStatus("3");
        WfService wfService = (WfService)ApplicationContextUtil.getBean("wfService");
        JsonResult jsonResult = wfService.updateWfAction(wfAction);
        wfAction = (WfAction) jsonResult.getData();

        PostVideo postVideo = new PostVideo();
        postVideo.setId(wfAction.getPartyId());
        postVideo.setStatus("3");
        PostVideoService postVideoService = (PostVideoService) ApplicationContextUtil.getBean("postVideoService");
        JsonResult jsonResult1 = postVideoService.updateVideo(postVideo);
        if (jsonResult1 ==null || jsonResult1.getSuccess()==0){
            System.out.println("定时任务执行失败");
        }else {
            JsonResult postVideo1 = postVideoService.getPostVideo(wfAction.getPartyId());
            postVideo = (PostVideo)postVideo1.getData();
            //添加审批人的待办流程记录
            wfService.addWfDetail(wfAction.getXid(),"2","1","2",
                    "上传资料",new Date(),postVideo.getApproval(),currentUserId,postVideo.getUploadOrg());
            ;
            //添加消息通知
            SysNoticeService sysNoticeService = (SysNoticeService)ApplicationContextUtil.getBean("sysNoticeService");
            String content = getNowDate()+";您有一条"+postVideo.getVideoName()+"上传申请需要审批";
            sysNoticeService.sendNoticePlus(postVideo.getApproval(),"1","提交",currentUserId,
                    content,wfAction.getXid(),currentUserId);
            System.out.println("定时任务执行完成");
        }

    }

    public MyTask(String processId, String currentUserId) {
        this.processId = processId;
        this.currentUserId =currentUserId;
    }

    /**
     * 获取当前时间
     * @return
     */
    private String getNowDate(){
        Date date= new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }
}
