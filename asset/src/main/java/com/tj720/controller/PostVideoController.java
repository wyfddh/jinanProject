package com.tj720.controller;

import com.tj720.controller.base.controller.BaseController;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.controller.springbeans.Config;
import com.tj720.model.common.video.*;
import com.tj720.model.common.wf.*;
import com.tj720.service.*;
import com.tj720.utils.ExportExcelUtil;
import com.tj720.utils.FtpUtil;
import com.tj720.utils.Page;
import com.tj720.utils.common.IdUtils;
import com.tj720.video.LayUiTableJson;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipOutputStream;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/22 11:41
 */
@RestController
@RequestMapping("/PostVideo")
public class PostVideoController extends BaseController {
    @Autowired
    PostVideoService postVideoService;
    @Autowired
    PostVideoCommentsService postVideoCommentsService;
    @Autowired
    WfService wfService;
    @Autowired
    RoleAuthService roleAuthService;
    @Autowired
    PostVideoTypeService postVideoTypeService;
    //消息通知
    @Autowired
    SysNoticeService sysNoticeService;
    @Autowired
    Config config;
    @Autowired
    PostLsService postLsService;
    @Autowired
    AssetEsaleLableVideoService esaleLableVideoService;

    private String userCode = "sysadmin";

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
    /**
     * 查询影视资料列表
     * @param keywords 关键词
     * @param videoMark 影视分类
     * @param status 状态
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/queryPostVideoList")
    public LayUiTableJson queryPostVideoList(String keywords, String videoMark, String status,@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "10") int limit){
        HttpSession session = request.getSession();
        SysUserVo sysUser = (SysUserVo)session.getAttribute("user");
        String userId = sysUser.getId();
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(page);
        pageInfo.setSize(limit);
        JsonResult postVideoList = postVideoService.getPostVideoListForCsy(keywords, videoMark, status
                , pageInfo,userId);
        if (postVideoList != null && postVideoList.getSuccess() == 1){
            if (null != postVideoList){
                List<PostVideo> postVideos = (List<PostVideo>)postVideoList.getData();
                return new LayUiTableJson(0,postVideoList.getMsg(),pageInfo.getAllRow(),postVideos);
            }else {
                return new LayUiTableJson(0,postVideoList.getMsg(),0,null);
            }
        }else {
            return new LayUiTableJson(1,postVideoList.getMsg(),0,null);
        }

    }

    @RequestMapping("/queryVedilListForCompreQuery")
    public LayUiTableJson queryVedilListForCompreQuery(String keywords, String videoMark, String status, @RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "10") int limit, String orderBy) {
        try {
            Page pageInfo = new Page();
            pageInfo.setCurrentPage(page);
            pageInfo.setSize(limit);
            JsonResult postVideoList = postVideoService.queryVedilListForCompreQuery(keywords, videoMark, status
                    , pageInfo, orderBy);
            if (postVideoList != null && postVideoList.getSuccess() == 1){
                if (null != postVideoList){
                    List<PostVideo> postVideos = (List<PostVideo>)postVideoList.getData();
                    return new LayUiTableJson(0,postVideoList.getMsg(),pageInfo.getAllRow(),postVideos);
                }else {
                    return new LayUiTableJson(0,postVideoList.getMsg(),0,null);
                }
            }else {
                return new LayUiTableJson(1,postVideoList.getMsg(),0,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LayUiTableJson(1,"20000003",0,null);
    }

    /**
     * 影视资料dto
     * @param postVideoDto
     * @return
     */
    @RequestMapping("/addPostVideo")
    public JsonResult addPostVideo(@RequestBody PostVideoDto postVideoDto){
        PostVideo postVideo = postVideoDto.getPostVideo();
        String actionType = postVideoDto.getActionType();
        postVideo.setStatus("1");//草稿状态，提交
        if("3".equals(actionType)){
            //提交并且发布
            postVideo.setStatus("4");
            postVideoService.checkVideoCode(postVideo);
        }
        JsonResult jsonResult = postVideoService.addPostVideo(postVideo);
        String labelName = postVideoDto.getLabelName();
        if(StringUtils.isNotBlank(labelName)){
            String[] split = labelName.split(",");
            List<String> stringList = Arrays.asList(split);
            int insertOrUpdate = esaleLableVideoService.insertOrUpdate(stringList, postVideo.getId());
        }
        if (null != jsonResult && jsonResult.getSuccess() == 1){
            postLsService.addLsCode("videoList","video");
            return jsonResult;
        }else {
            return jsonResult;
        }
    }

    /**
     * 修改影视资料
     * @param postVideoDto 影视资料dto
     * @return
     */
    @RequestMapping("/updatePostVideo")
    public JsonResult updatePostVideo(@RequestBody PostVideoDto postVideoDto){

        PostVideo postVideo = postVideoDto.getPostVideo();
        String actionType = postVideoDto.getActionType();
        postVideo.setStatus("1");//草稿状态，提交
        if("3".equals(actionType)){
            //提交并且发布
            postVideo.setStatus("4");
        }
        String labelName = postVideoDto.getLabelName();
        if(StringUtils.isNotBlank(labelName)){
            String[] split = labelName.split(",");
            List<String> stringList = Arrays.asList(split);
            int insertOrUpdate = esaleLableVideoService.insertOrUpdate(stringList, postVideo.getId());
        }
        JsonResult jsonResult = postVideoService.updateVideo(postVideo);
        return jsonResult;
    }

    /**
     * 删除影视资料
     * @param id 资料id
     * @return
     */
    @RequestMapping("/deletePostVideo")
    public JsonResult deletePostVideo(@RequestParam String id){
        JsonResult jsonResult = postVideoService.deleteVideo(id);
        if (null != jsonResult && jsonResult.getSuccess() == 1){
            /*//根据id删除标注信息
            JsonResult PostVideoCommentsResult = postVideoCommentsService.deletePostVideoCommentList(id);
            if (null == PostVideoCommentsResult || PostVideoCommentsResult.getSuccess() == 0){
                return PostVideoCommentsResult;
            }*/
            //删除关联流程
            return jsonResult;
        }else {
            return jsonResult;
        }
    }

    /**
     * 撤回影视资料流程
     * @param id
     * @param currentUserId 用户
     * @return
     */
    @RequestMapping("/revokeProcess")
    public JsonResult revokeProcess(@RequestParam String id, String currentUserId){
        JsonResult jsonResult = postVideoService.revokeProcess(id,currentUserId);
        return jsonResult;
    }

    /**
     * 提交影视资料流程
     * @param id
     * @return
     */
    @RequestMapping("/commitProcess")
    public JsonResult commitProcess(@RequestParam String id, @RequestParam String currentUserId){
        JsonResult jsonResult = postVideoService.commitProcess(id,currentUserId);
        return jsonResult;
    }

    @RequestMapping("/publishProcess")
    public JsonResult publishProcess(@RequestParam String id,@RequestParam String currentUserId){
        JsonResult jsonResult = postVideoService.publishProcess(id,currentUserId);
        return jsonResult;
    }

    /**
     * 审批
     * @param approvalDto 审批信息
     * @return
     */
    @RequestMapping("/approvalProcess")
    public JsonResult approvalProcess(@RequestBody ApprovalDto approvalDto){
        JsonResult jsonResult = postVideoService.approvalProcess(approvalDto.getId(),approvalDto.getAuthSetting()
                ,approvalDto.getActionType(),approvalDto.getApproval(),approvalDto.getRemark(),approvalDto.getCurrentUserId());
        return jsonResult;
    }

    @RequestMapping("/batchApprovalProcess")
    public JsonResult batchApprovalProcess(@RequestBody ApprovalDto approvalDto){
        JsonResult jsonResult = postVideoService.batchApprovalProcess(approvalDto.getIds(),approvalDto.getAuthSetting()
                ,approvalDto.getActionType(),approvalDto.getApproval(),approvalDto.getRemark(),approvalDto.getCurrentUserId());
        return jsonResult;
    }

    /**
     * 设置权限
     * @param id
     * @param authSetting
     * @return
     */
    @RequestMapping("/setAuthSetting")
    public JsonResult setAuthSetting(@RequestParam String id, @RequestParam String authSetting){
        JsonResult jsonResult = postVideoService.changeAuthSetting(id,authSetting);
        return jsonResult;
    }

    /**
     * 批量设置权限
     * @param map
     * @return
     */
    @RequestMapping("/batchSetAuthSetting")
    public JsonResult batchSetAuthSetting(@RequestBody HashMap<String,Object> map){
        List<String> ids = (List<String>) map.get("ids");
        String authSetting = map.get("map").toString();
        JsonResult jsonResult = postVideoService.batchChangeAuthSetting(ids,authSetting);
        return jsonResult;
    }

    @RequestMapping("queryPostVideoDtoById")
    public JsonResult queryPostVideoDtoById(@RequestParam String id){
        JsonResult postVideo = postVideoService.getPostVideo(id);
        if (null != postVideo && postVideo.getSuccess() == 1){
            PostVideo postVideoInfo = (PostVideo)postVideo.getData();
            //查询标注信息
            JsonResult postVideoCommentList = postVideoCommentsService.getPostVideoCommentList(id);
            //查询编目信息
            return postVideo;
        }else {
            return postVideo;
        }
    }

    @RequestMapping("/getActionInfoListByProcessId")
    public LayUiTableJson getActionInfoListByProcessId(@RequestParam String processInstId){
        JsonResult actionInfo = wfService.getWfDetailByProcessId(processInstId);
        if (actionInfo != null && actionInfo.getSuccess() == 1){
            List<WfDetail> wfDetailList = (List<WfDetail>)actionInfo.getData();
            return new LayUiTableJson(0,null,wfDetailList.size(),wfDetailList);
        }else {
            return new LayUiTableJson(1,actionInfo.getMsg(),0,null);
        }
    }

    /**
     * 查询上传资料的审批记录
     * @param id
     * @return
     */
    @RequestMapping("/getUploadVideoInfo")
    public LayUiTableJson getUploadVideoInfo(@RequestParam String id){
        WfAction wfActionByPartyId = wfService.getWfActionByPartyId(id, "1");
        if (null != wfActionByPartyId){
            String processId = wfActionByPartyId.getXid();
            JsonResult actionInfo = wfService.getWfDetailByProcessId(processId);
            if (actionInfo != null && actionInfo.getSuccess() == 1){
                List<WfDetail> wfDetailList = (List<WfDetail>)actionInfo.getData();
                return new LayUiTableJson(0,null,wfDetailList.size(),wfDetailList);
            }else {
                return new LayUiTableJson(1,actionInfo.getMsg(),0,null);
            }
        }else {
            return new LayUiTableJson(1,"无数据",0,null);
        }


    }

    /**
     * 查询上传审批列表
     * @param keywords 关键词
     * @param source 来源
     * @param status 状态
     * @param currentUserId 当前登录用户
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getUploadApprovalList")
    public LayUiTableJson getUploadApprovalList(String keywords,String source,String status
            ,String currentUserId
            ,@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "10") int limit){
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(page);
        pageInfo.setSize(limit);
        JsonResult uplodApprovalList = postVideoService.getUplodApprovalList(keywords, source, status, pageInfo,currentUserId);
        if (null == uplodApprovalList || uplodApprovalList.getSuccess()==0){
            return new LayUiTableJson(1,"系统异常",0,null);
        }else {
            return new LayUiTableJson(0,null,pageInfo.getAllRow(),(List<UploadApprovalDto>) uplodApprovalList.getData());
        }
    }

    /**
     * 查询影视资料查询列表
     * @param keywords 关键词
     * @param videoMark 影视分类
     * @param status 状态
     * @param userType 用户类型（影视部/非影视部）
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/queryPostVideoQueryList")
    public LayUiTableJson queryPostVideoQueryList(String keywords, String videoMark, String status
            ,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        HttpSession session = request.getSession();
        SysUserVo sysUser = (SysUserVo)session.getAttribute("user");
        String userId = sysUser.getId();
        String orgId = sysUser.getOrgId();
        String orgType = "0";//0:非资料部 1：资料部
        if(config.getDepartmentId().equals(orgId)){
            orgType = "1";
        }
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(page);
        pageInfo.setSize(limit);
        JsonResult postVideoList = postVideoService.getPostVideoQueryListForCsy(keywords, videoMark, status,pageInfo,orgType,userId);
        if (postVideoList != null && postVideoList.getSuccess() == 1){
            if (null != postVideoList){
                List<PostVideo> postVideos = (List<PostVideo>)postVideoList.getData();
                return new LayUiTableJson(0,postVideoList.getMsg(),pageInfo.getAllRow(),postVideos);
            }else {
                return new LayUiTableJson(0,postVideoList.getMsg(),0,null);
            }
        }else {
            return new LayUiTableJson(1,"查询失败",0,null);
        }
    }

    /**
     * 保存查询申请
     * @param queryApplyDto
     * @return
     */
    @RequestMapping("/saveQueryApply")
    public JsonResult saveQueryApply(@RequestBody QueryApplyDto queryApplyDto){
        JsonResult jsonResult = postVideoService.saveQueryApply(queryApplyDto.getPostVideoId(), queryApplyDto.getApply(),
                queryApplyDto.getApplyOrg(), queryApplyDto.getApplyTime(), queryApplyDto.getApplyReason(),
                queryApplyDto.getRemarks(), queryApplyDto.getApproval());
        return jsonResult;
    }

    /**
     * 保存查询申请
     * @param queryApplyDto
     * @return
     */
    @RequestMapping("/batchSaveQueryApply")
    public JsonResult batchSaveQueryApply(@RequestBody QueryApplyDto queryApplyDto){
        JsonResult jsonResult = postVideoService.batchSaveQueryApply(queryApplyDto.getIds(), queryApplyDto.getApply(),
                queryApplyDto.getApplyOrg(), queryApplyDto.getApplyTime(), queryApplyDto.getApplyReason(),
                queryApplyDto.getRemarks(), queryApplyDto.getApproval());
        return jsonResult;
    }


    /**
     * 保存并提交查询申请
     * @param queryApplyDto
     * @return
     */
    @RequestMapping("/saveAndSumitQueryApply")
    public JsonResult saveAndSumitQueryApply(@RequestBody QueryApplyDto queryApplyDto){
        JsonResult jsonResult = postVideoService.saveQueryApply(queryApplyDto.getPostVideoId(), queryApplyDto.getApply(),
                queryApplyDto.getApplyOrg(), queryApplyDto.getApplyTime(), queryApplyDto.getApplyReason(),
                queryApplyDto.getRemarks(), queryApplyDto.getApproval());
        if (null == jsonResult || jsonResult.getSuccess() == 0){
            return jsonResult;
        }
        WfAction wfAction = wfService.getWfActionByPartyId(queryApplyDto.getPostVideoId(),"2",queryApplyDto.getApply());
        JsonResult jsonResult1 = postVideoService.submitQueryApply(wfAction.getXid(), queryApplyDto.getApplyTime(), queryApplyDto.getApply()
                , queryApplyDto.getApplyOrg(), queryApplyDto.getRemarks(), queryApplyDto.getApproval());
        return jsonResult1;
    }

    /**
     * 保存并提交查询申请
     * @param queryApplyDto
     * @return
     */
    @RequestMapping("/batchSaveAndSumitQueryApply")
    public JsonResult batchSaveAndSumitQueryApply(@RequestBody QueryApplyDto queryApplyDto){
        try {
            JsonResult jsonResult = postVideoService.batchSaveQueryApply(queryApplyDto.getIds(), queryApplyDto.getApply(),
                    queryApplyDto.getApplyOrg(), queryApplyDto.getApplyTime(), queryApplyDto.getApplyReason(),
                    queryApplyDto.getRemarks(), queryApplyDto.getApproval());
            if (null == jsonResult || jsonResult.getSuccess() == 0){
                return jsonResult;
            }
            String[] ids = queryApplyDto.getIds();
            for (int i=0;i<ids.length;i++){
                WfAction wfAction = wfService.getWfActionByPartyId(ids[i],"2",queryApplyDto.getApply());
                JsonResult jsonResult1 = postVideoService.submitQueryApply(wfAction.getXid(), queryApplyDto.getApplyTime(), queryApplyDto.getApply()
                        , queryApplyDto.getApplyOrg(), queryApplyDto.getRemarks(), queryApplyDto.getApproval());
            }
            return new JsonResult(1);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200504");
        }
    }

    /**
     * 撤回查询申请
     * @param queryApplyDto
     */
    @RequestMapping("/revokeQueryApply")
    public JsonResult revokeQueryApply(@RequestBody QueryApplyDto queryApplyDto){
        WfAction wfAction = wfService.getWfActionByPartyId(queryApplyDto.getPostVideoId(),"2",queryApplyDto.getApply());
        JsonResult jsonResult = postVideoService.revokeQueryApply(wfAction.getXid(), queryApplyDto.getApply()
                , queryApplyDto.getApplyOrg());
        return jsonResult;
    }

    /**
     * 提交查询申请
     * @param queryApplyDto
     * @return
     */
    @RequestMapping("/submitQueryApply")
    public JsonResult submitQueryApply(@RequestBody QueryApplyDto queryApplyDto){
        WfAction wfAction = wfService.getWfActionByPartyId(queryApplyDto.getPostVideoId(),"2",queryApplyDto.getApply());
        JsonResult jsonResult1 = postVideoService.submitQueryApply(wfAction.getXid(), queryApplyDto.getApplyTime(), queryApplyDto.getApply()
                , queryApplyDto.getApplyOrg(), queryApplyDto.getRemarks(), queryApplyDto.getApproval());
        return jsonResult1;
    }

    /**
     * 审批查询申请
     * @param queryApplyDto
     * @return
     */
    @RequestMapping("/approvalQueryApply")
    public JsonResult approvalQueryApply(@RequestBody QueryApplyDto queryApplyDto){
        JsonResult jsonResult = postVideoService.approvalQueryApply(queryApplyDto.getProcessInstId(), queryApplyDto.getActionType()
                , queryApplyDto.getApproval(), queryApplyDto.getApply(), queryApplyDto.getApplyOrg(), queryApplyDto.getRemarks(),
                queryApplyDto.getCurrentUserId());
        return jsonResult;
    }

    /**
     * 查询查询资料的审批记录
     * @param id
     * @return
     */
    @RequestMapping("/getQueryVideoInfo")
    public LayUiTableJson getQueryVideoInfo(@RequestParam String id,@RequestParam String currentUserId){
        WfAction wfActionByPartyId = wfService.getWfActionByPartyId(id, "2",currentUserId);
        if (null != wfActionByPartyId){
            String processId = wfActionByPartyId.getXid();
            JsonResult actionInfo = wfService.getWfDetailByProcessId(processId);
            if (actionInfo != null && actionInfo.getSuccess() == 1){
                List<WfDetail> wfDetailList = (List<WfDetail>)actionInfo.getData();
                return new LayUiTableJson(0,null,wfDetailList.size(),wfDetailList);
            }else {
                return new LayUiTableJson(1,actionInfo.getMsg(),0,null);
            }
        }else {
            return new LayUiTableJson(1,"无数据",0,null);
        }


    }

    /**
     * 查询查询资料的审批记录
     * @param processInstId 流程id
     * @return
     */
    @RequestMapping("/getQueryApplyRecord")
    public LayUiTableJson getQueryApplyRecord(@RequestParam String processInstId){
        JsonResult actionInfo = wfService.getWfDetailByProcessId(processInstId);
        if (actionInfo != null && actionInfo.getSuccess() == 1){
            List<WfDetail> wfDetailList = (List<WfDetail>)actionInfo.getData();
            return new LayUiTableJson(0,null,wfDetailList.size(),wfDetailList);
        }else {
            return new LayUiTableJson(1,actionInfo.getMsg(),0,null);
        }
    }

    @RequestMapping("/getQueryVideoList")
    public LayUiTableJson getQueryVideoList(String keywords,String applyOrg,String applyStatus,String currentUserId
            ,@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "10") int limit){
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(page);
        pageInfo.setSize(limit);
        JsonResult jsonResult = postVideoService.getQueryVideoList(keywords,applyOrg,applyStatus,currentUserId,
                pageInfo);
        if (null == jsonResult || jsonResult.getSuccess()==0){
            return new LayUiTableJson(1,"查询失败",0,null);
        }else {
            return new LayUiTableJson(0,null,pageInfo.getAllRow(),(List<QueryVideoDto>)jsonResult.getData());
        }
    }

    @RequestMapping("/getApplyInfo")
    public JsonResult getApplyInfo(@RequestParam String processInstId){
        try {
            WfAction wfAction = wfService.getWfActionById(processInstId);
            return new JsonResult(1,wfAction);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200507");
        }
    }

    @RequestMapping("/updateApply")
    @ResponseBody
    public JsonResult updateApply(@RequestParam String processInstId, String applyReason, String remarks, String approval,String applyTime){
        try {
            WfAction wfAction = wfService.getWfActionById(processInstId);
            wfAction.setActionName(applyReason);
            wfAction.setApproval(approval);
            if(StringUtils.isNotEmpty(applyTime)){
                SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                wfAction.setApplyTime(sDateFormat.parse(applyTime));
            }
            wfAction.setRemark(remarks);
            wfService.updateWfAction(wfAction);
            return new JsonResult(1);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200505");
        }

    }
    @RequestMapping("/updateApplyAndSubmit")
    @ResponseBody
    public JsonResult updateApplyAndSubmit(String processInstId,String applyReason,String remarks,String approval,String applyTime){
        try {
            WfAction wfAction = wfService.getWfActionById(processInstId);
            wfAction.setActionName(applyReason);
            wfAction.setApproval(approval);
            if(StringUtils.isNotEmpty(applyTime)){
                SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                wfAction.setApplyTime(sDateFormat.parse(applyTime));
            }
            wfAction.setRemark(remarks);
            wfService.updateWfAction(wfAction);
            JsonResult jsonResult1 = postVideoService.submitQueryApply(wfAction.getXid(), wfAction.getApplyTime(), wfAction.getApply()
                    , wfAction.getApplyOrg(),remarks,approval);
            return jsonResult1;
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("200505");
        }

    }

    @RequestMapping("/deleteApply")
    public JsonResult deleteApply(@RequestParam String processInstId){
        JsonResult jsonResult = wfService.deleteWfAction(processInstId);
        return jsonResult;
    }

    @RequestMapping("/queryKeywordsList")
    public LayUiTableJson queryKeywordsList(@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "10") int limit){
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(page);
        pageInfo.setSize(limit);
        JsonResult jsonResult = postVideoService.queryKeywordsList(pageInfo);
        if (null != jsonResult && jsonResult.getSuccess() == 1){
            return new LayUiTableJson(0,null,pageInfo.getAllRow(),(List<HashMap>)jsonResult.getData());
        }else {
            return new LayUiTableJson(1,"查询失败",0,null);
        }
    }

    /**
     * 查询资料分类列表
     * @param keywords 关键词
     * @param typeLevel 级别
     * @param status 状态
     * @param page 当前页数
     * @param limit 每页大小
     * @return
     */
    @RequestMapping("/queryPostVideoTypeList")
    public LayUiTableJson queryPostVideoTypeList(String keywords,String typeLevel,String status,@RequestParam(defaultValue = "0") Integer orderBy
            ,@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "10") int limit){
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(page);
        pageInfo.setSize(limit);
        JsonResult jsonResult = postVideoTypeService.queryPostVideoTypeList(keywords, typeLevel, status,orderBy, pageInfo);
        if (null != jsonResult && jsonResult.getSuccess() == 1){
            return new LayUiTableJson(0,null,pageInfo.getAllRow(),(List<PostVideoType>)jsonResult.getData());
        }else {
            return new LayUiTableJson(1,"查询失败",0,null);
        }
    }

    /**
     * 添加分类
     * @param postVideoType
     * @return
     */
    @RequestMapping("/addPostVideoType")
    public JsonResult addPostVideoType(PostVideoType postVideoType){
        JsonResult jsonResult = postVideoTypeService.addPostVideoType(postVideoType);
        return jsonResult;
    }

    /**
     * 修改分类
     * @param postVideoType
     * @return
     */
    @RequestMapping("/updatePostVideoType")
    public JsonResult updatePostVideoType(PostVideoType postVideoType){
        JsonResult jsonResult = postVideoTypeService.updatePostVideoType(postVideoType);
        return jsonResult;
    }

    /**
     * 删除分类
     * @param id 分类id
     * @return
     */
    @RequestMapping("/deletePostVideoType")
    public JsonResult deletePostVideoType(@RequestParam String id){
        JsonResult jsonResult = postVideoTypeService.deletePostVideoType(id);
        return jsonResult;
    }

    @RequestMapping("/queryPostVideoTypeById")
    public JsonResult queryPostVideoTypeById(@RequestParam String id){
        JsonResult jsonResult = postVideoTypeService.queryPostVideoTypeById(id);
        return jsonResult;
    }

    @RequestMapping("/queryPostVideoTypeListTree")
    public LayUiTableJson queryPostVideoTypeListTree(){
        JsonResult jsonResult = postVideoTypeService.queryPostVideoTypeListTree();
        List<MenuTreeDto> list = (List<MenuTreeDto>)jsonResult.getData();
        List<HashMap> data = new ArrayList<HashMap>();
        for (MenuTreeDto menuTreeDto : list) {
            HashMap map = new HashMap();
            map.put("id",menuTreeDto.getId());
            map.put("name",menuTreeDto.getFunctionname());
            map.put("pId",menuTreeDto.getPid());
            map.put("open",null);
            map.put("chkDisabled","false");
            data.add(map);
        }

        return new LayUiTableJson(0,null,data.size(),data);

    }

    @RequestMapping("/queryPostVideoTypeListDist")
    public JsonResult queryPostVideoTypeListDist(String currentId){
        JsonResult jsonResult = postVideoTypeService.queryPostVideoTypeListDist(currentId);
        return jsonResult;

    }

    @RequestMapping("/changeTypeStatus")
    public JsonResult changeTypeStatus(@RequestParam String id, @RequestParam int status){
        JsonResult jsonResult = postVideoTypeService.changeTypeStatus(id,status);
        return jsonResult;
    }

    @RequestMapping("/getVideoReport1")
    public JsonResult getVideoReport1(String status, String startTime, String endTime, String type){
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("status",status);
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);
        JsonResult jsonResult = postVideoService.getVideoCjReport1(condition,type);
        return jsonResult;
    }
    @RequestMapping("/getVideoReport2")
    public JsonResult getVideoReport2(String status, String startTime, String endTime, String type){
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("status",status);
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);
        JsonResult jsonResult = postVideoService.getVideoCjReport2(condition,type);
        return jsonResult;
    }

    /**
     * 查询待办
     * @param currentUserId 用户id
     * @return
     */
    @RequestMapping("/getUndoTask")
    public JsonResult getUndoTask(@RequestParam String currentUserId){
        JsonResult undoTask = postVideoService.getUndoTask(currentUserId);
        return undoTask;
    }
    /**
     * 查询已办
     * @param currentUserId 用户id
     * @return
     */
    @RequestMapping("/getDoneTask")
    public JsonResult getDoneTask(@RequestParam String currentUserId){
        JsonResult doneTask = postVideoService.getDoneTask(currentUserId);
        return doneTask;
    }
    /**
     * 查询已办结
     * @param currentUserId 用户id
     * @return
     */
    @RequestMapping("/getFinishTask")
    public JsonResult getFinishTask(@RequestParam String currentUserId){
        JsonResult finishTask = postVideoService.getFinishTask(currentUserId);
        return finishTask;
    }

    /**
     * 查询影视采集统计
     * @return
     */
    @RequestMapping("/getVideoCjCount")
    public JsonResult getVideoCjCount(){
        JsonResult count = postVideoService.getVideoCjCount();
        return count;
    }
    /**
     * 查询影视查询统计
     * @return
     */
    @RequestMapping("/getVideoCxCount")
    public JsonResult getVideoCxCount(){
        JsonResult count = postVideoService.getVideoCxCount();
        return count;
    }

    /**
     * 加载快捷入口
     * @param currentUserId
     * @return
     */
    @RequestMapping("/getShortcutEntrance")
    public JsonResult getShortcutEntrance(@RequestParam String currentUserId, @RequestParam String currentId, @RequestParam String type){
        JsonResult jsonResult = postVideoService.getShortcutEntrance(currentUserId,currentId,type);
        return jsonResult;
    }
    @RequestMapping("/setShortcutEntrance")
    public JsonResult setShortcutEntrance(@RequestBody ShortcutEntranceDto shortcutEntranceDto){
        JsonResult jsonResult = postVideoService.setShortcutEntrance(shortcutEntranceDto.getCurrentUserId()
                ,shortcutEntranceDto.getShortcutEntrance(),shortcutEntranceDto.getType());
        return jsonResult;
    }

    /**
     * 查询流程记录
     * @param processInstId
     * @return
     */
    @RequestMapping("/getWfAction")
    public JsonResult getWfAction(@RequestParam String processInstId){
        try {
            WfAction wfActionById = wfService.getWfActionById(processInstId);
            return new JsonResult(1,wfActionById);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    @RequestMapping("/getWfActionByNotice")
    public JsonResult getWfActionByNotice(@RequestParam String id){
        JsonResult jsonResult = wfService.getWfActionByNotice(id);
        return jsonResult;
    }

    @RequestMapping("/downloadVideoFile")
    public void  downloadVideoFile(String postVideoId, HttpServletRequest request, HttpServletResponse response)throws IOException{
        JsonResult postVideo = postVideoService.getPostVideo(postVideoId);
        PostVideo video = (PostVideo)postVideo.getData();
        String zipName = video.getVideoName()+".zip";
        List<Attachment> fileList = postVideoService.getPostVideoAttachment(postVideoId);//查询数据库中记录
        for (Attachment attachment : fileList) {
            String downPath = config.getFtpRootPath() + config.getRootPath() + attachment.getAttPath();
            attachment.setAttPath(downPath);
        }
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());

        try {
            request.setCharacterEncoding("UTF-8");//设定请求字符编码
            response.setContentType("application/x-msdownload;charset=utf-8");
            response.setHeader("Content-disposition", "attachment; filename=" +  URLEncoder.encode(zipName, "UTF-8"));
            FtpUtil ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
            ftpUtil.downFtpFiletoZip(fileList,out,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/batchDownloadVideoFile")
    public void  batchDownloadVideoFile(String postVideoId, HttpServletRequest request, HttpServletResponse response)throws IOException{
        String[] list = postVideoId.split(",");
        String zipName = "影视资料.zip";
        List<HashMap<String,Object>> attachments = new ArrayList<HashMap<String,Object>>();
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        for (String id : list) {
            JsonResult postVideo = postVideoService.getPostVideo(id);
            PostVideo video = (PostVideo)postVideo.getData();
            String fileName = video.getVideoName();
            List<Attachment> fileList = postVideoService.getPostVideoAttachment(id);//查询数据库中记录
            for (Attachment attachment : fileList) {
                String downPath = config.getFtpRootPath() + config.getRootPath() + attachment.getAttPath();
                attachment.setAttPath(downPath);
            }
            HashMap<String,Object> temp = new HashMap<String,Object>();
            temp.put("fileName",fileName);
            temp.put("attachment",fileList);
            attachments.add(temp);
        }
        try {
            request.setCharacterEncoding("UTF-8");//设定请求字符编码
            response.setContentType("application/x-msdownload;charset=utf-8");
            response.setHeader("Content-disposition", "attachment; filename=" +  URLEncoder.encode(zipName, "UTF-8"));
            FtpUtil ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
            ftpUtil.downFtpFiletoZipPlus(attachments,out,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/exportVideoCjReport1")
    public void exportVideoCjReport1(String status,String startTime,String endTime,String type,
                                     HttpServletRequest request, HttpServletResponse response)throws IOException{
        String zipName = "影视采集统计1.zip";
        //交叉表数据
        String[] tableRow = new String[]{"存储类型","文物馆藏","中心活动","收集资料","拍摄素材","播出成片","其他"};
        if (type.equals("2")){
            tableRow = new String[]{"操作类型","展陈","社教","研究","新闻发布","其他"};
        }
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("status",status);
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);
        JsonResult videoCjTable = postVideoService.getVideoCjTable(condition);
        List<Object[]> tableData = new ArrayList<Object[]>();
        if (type.equals("2")){
            videoCjTable = postVideoService.getVideoCxTable(condition);
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjTable.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[6];
                temp[0] = map.get("type").toString();
                temp[1] = map.get("zc").toString();
                temp[2] = map.get("sj").toString();
                temp[3] = map.get("yj").toString();
                temp[4] = map.get("xwfb").toString();
                temp[5] = map.get("qt").toString();
                tableData.add(temp);
            }
        }else {
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjTable.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[7];
                temp[0] = map.get("saveType").toString();
                temp[1] = map.get("W").toString();
                temp[2] = map.get("Z").toString();
                temp[3] = map.get("S").toString();
                temp[4] = map.get("P").toString();
                temp[5] = map.get("B").toString();
                temp[6] = map.get("Q").toString();
                tableData.add(temp);
            }
        }


        //饼图数据
        String[] pieRow = new String[]{"存储类型","总数"};
        if (type.equals("2")){
            pieRow = new String[]{"申请原因","总数"};
        }
        JsonResult videoCjPie = postVideoService.getVideoCjPie(condition);
        List<Object[]> pieData = new ArrayList<Object[]>();
        if (type.equals("2")){
            videoCjPie = postVideoService.getVideoCxPie(condition);
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjPie.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[2];
                temp[0] = map.get("name").toString();
                temp[1] = map.get("value").toString();
                pieData.add(temp);
            }
        }else{
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjPie.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[2];
                temp[0] = map.get("name").toString();
                temp[1] = map.get("value").toString();
                pieData.add(temp);
            }
        }

        //折线图数据
        String[] lineRow = new String[]{"操作日期","图片","视频","音频"};
        if (type.equals("2")){
            lineRow = new String[]{"操作日期","申请","下载"};
        }
        JsonResult videoCjLine = postVideoService.getVideoCjLine(condition);
        List<Object[]> linedata = new ArrayList<Object[]>();
        if (type.equals("2")){
            videoCjLine = postVideoService.getVideoCxLine(condition);
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjLine.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[3];
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String updateTime = simpleDateFormat.format((Date) map.get("updateTime"));
                temp[0] = updateTime;
                temp[1] = map.get("申请").toString();
                temp[2] = map.get("下载").toString();
                linedata.add(temp);
            }
        }else {
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjLine.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[4];
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String updateTime = simpleDateFormat.format((Date) map.get("updateTime"));
                temp[0] = updateTime;
                temp[1] = map.get("图片").toString();
                temp[2] = map.get("视频").toString();
                temp[3] = map.get("音频").toString();
                linedata.add(temp);
            }
        }
        String execelName1 = "采集类型交叉表统计";
        String execelName2 = "采集类型饼图统计";
        String execelName3 = "采集类型折线图统计";
        if (type.equals("2")){
            execelName1 = "操作类型交叉表统计";
            execelName2 = "操作类型饼图统计";
            execelName3 = "操作类型折线图统计";
        }
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil(execelName1,tableRow,tableData);
        HSSFWorkbook sheets = exportExcelUtil.exportMulti(response);
        ExportExcelUtil exportExcelUtil2 = new ExportExcelUtil(execelName2,pieRow,pieData);
        HSSFWorkbook sheets2 = exportExcelUtil2.exportMulti(response);
        ExportExcelUtil exportExcelUtil3 = new ExportExcelUtil(execelName3,lineRow,linedata);
        HSSFWorkbook sheets3 = exportExcelUtil3.exportMulti(response);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        try
        {
            request.setCharacterEncoding("UTF-8");//设定请求字符编码
            response.setContentType("application/x-msdownload;charset=utf-8");
            response.setHeader("Content-disposition", "attachment; filename=" +  URLEncoder.encode(zipName, "UTF-8"));
            ExportExcelUtil.doCompress(sheets,execelName1+".xls",out);
            ExportExcelUtil.doCompress(sheets2,execelName2+".xls",out);
            ExportExcelUtil.doCompress(sheets3,execelName3+".xls",out);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }finally {
            if(out != null){
                try {out.close();} catch (IOException e) {}
            }
        }


    }

    @RequestMapping("/exportVideoCjReport2")
    public void exportVideoCjReport2(String status,String startTime,String endTime,String type,
                                     HttpServletRequest request, HttpServletResponse response)throws IOException{
        String zipName = "影视采集统计2.zip";
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("status",status);
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);
        if (type.equals("2")){
            //折线图数据
            String[] barRow = new String[]{"部门","申请","下载"};
            JsonResult videoCjBar = postVideoService.getVideoCxBar(condition);
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjBar.getData();
            List<Object[]> barData = new ArrayList<Object[]>();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[3];
                temp[0] = map.get("orgName").toString();
                temp[1] = map.get("申请").toString();
                temp[2] = map.get("下载").toString();
                barData.add(temp);
            }

            ExportExcelUtil exportExcelUtil = new ExportExcelUtil("操作类型部门统计",barRow,barData);
            HSSFWorkbook sheets = exportExcelUtil.exportMulti(response);

            //折线图数据
            String[] barRow1 = new String[]{"用户名","申请","下载"};
            JsonResult videoCjBar1 = postVideoService.getVideoCxBarTop10(condition);
            List<HashMap<String,Object>> list1 = (List<HashMap<String,Object>>)videoCjBar1.getData();
            List<Object[]> barData1 = new ArrayList<Object[]>();
            for (HashMap<String, Object> map : list1) {
                Object[] temp = new String[3];
                temp[0] = map.get("apply").toString();
                temp[1] = map.get("申请").toString();
                temp[2] = map.get("下载").toString();
                barData1.add(temp);
            }

            ExportExcelUtil exportExcelUtil1 = new ExportExcelUtil("操作类型用户统计",barRow1,barData1);
            HSSFWorkbook sheets1 = exportExcelUtil1.exportMulti(response);
            ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
            try
            {
                request.setCharacterEncoding("UTF-8");//设定请求字符编码
                response.setContentType("application/x-msdownload;charset=utf-8");
                response.setHeader("Content-disposition", "attachment; filename=" +  URLEncoder.encode(zipName, "UTF-8"));
                ExportExcelUtil.doCompress(sheets,"操作类型部门统计.xls",out);
                ExportExcelUtil.doCompress(sheets1,"操作类型用户统计.xls",out);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }finally {
                if(out != null){
                    try {out.close();} catch (IOException e) {}
                }
            }
        }else {
            //折线图数据
            String[] barRow = new String[]{"部门","图片","视频","音频"};
            JsonResult videoCjBar = postVideoService.getVideoCjBar(condition);
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjBar.getData();
            List<Object[]> barData = new ArrayList<Object[]>();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[4];
                temp[0] = map.get("orgName").toString();
                temp[1] = map.get("图片").toString();
                temp[2] = map.get("视频").toString();
                temp[3] = map.get("音频").toString();
                barData.add(temp);
            }

            ExportExcelUtil exportExcelUtil = new ExportExcelUtil("采集类型部门统计",barRow,barData);
            HSSFWorkbook sheets = exportExcelUtil.exportMulti(response);
            ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
            try
            {
                request.setCharacterEncoding("UTF-8");//设定请求字符编码
                response.setContentType("application/x-msdownload;charset=utf-8");
                response.setHeader("Content-disposition", "attachment; filename=" +  URLEncoder.encode(zipName, "UTF-8"));
                ExportExcelUtil.doCompress(sheets,"采集类型部门统计.xls",out);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }finally {
                if(out != null){
                    try {out.close();} catch (IOException e) {}
                }
            }
        }
    }

    @RequestMapping("/checkOrg")
    @ResponseBody
    public JsonResult checkOrg(@RequestParam String userId){
        boolean result = postVideoService.checkUserOrg(userId);
        return new JsonResult(1,result);
    }

    @RequestMapping("/getProcessUserStatus")
    public JsonResult getProcessUserStatus(@RequestParam String userId, @RequestParam String postVideoId, @RequestParam
            String type){
        JsonResult jsonResult = postVideoService.getProcessUserStatus(userId,postVideoId,type);
        return jsonResult;
    }

    /**
     * 根据关键词查询影视列表
     * @param keyId
     * @return
     */
    @RequestMapping("/getVideoListByKeywords")
    public LayUiTableJson getVideoListByKeywords(@RequestParam String keyId){
        JsonResult jsonResult = postVideoService.getVideoListByKeywords(keyId);
        if (jsonResult.getSuccess()==1){
            if (null != jsonResult.getData()){
                List<PostVideo> postVideos = (List<PostVideo>)jsonResult.getData();
                return new LayUiTableJson(0,"查询成功",postVideos.size(),postVideos);
            }else {
                return new LayUiTableJson(0,"查询成功",0,null);
            }
        }else {
            return new LayUiTableJson(1,"查询失败",0,null);
        }
    }

    /**
     * 查询展陈页面影视资料
     * @param postVideo
     * @return
     */
    @RequestMapping("/getPostVideoForExhib")
    public JsonResult getPostVideoForExhib(PostVideo postVideo){
        try {
            return postVideoService.getPostVideoForExhib(postVideo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000003");
    }

    /**
     *
     * 功能描述: 查询可以申请的影视资料-下拉框
     *
     * @param: [currentUserId]
     * @return: com.tj720.controller.framework.JsonResult
     * @auther: caiming
     * @date: 2018/12/6 18:42
     */
    @RequestMapping("/getVideoListForSelect")
    public JsonResult getVideoListForSelect(String currentUserId){
        return postVideoService.getVideoListForSelect(currentUserId);
    }

    /**
     * 查询审批列表
     * @param keywords
     * @param applyOrg
     * @param applyStatus
     * @param
     * @param
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getQueryApprovalVideoList")
    public LayUiTableJson getQueryApprovalVideoList(String keywords,String applyOrg,String applyStatus,@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "10") int limit){
        HttpSession session = request.getSession();
        SysUserVo sysUser = (SysUserVo)session.getAttribute("user");
        String userId = sysUser.getId();
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(page);
        pageInfo.setSize(limit);
        JsonResult jsonResult = postVideoService.getQueryApprovalVideoList(keywords,applyOrg,applyStatus,userId,
                pageInfo);
        if (null == jsonResult || jsonResult.getSuccess()==0){
            return new LayUiTableJson(1,"查询失败",0,null);
        }else {
            return new LayUiTableJson(0,null,pageInfo.getAllRow(),(List<QueryVideoDto>)jsonResult.getData());
        }
    }


}
