var tableId = '';
var statusDictList = property.getDictData("video_status");
var form1;
var videoId = null;
//所有用户列表
var userList = property.getAllUserList();
//所有部门列表
var orgList = property.getAllOrgList();
//页面属性
var pageType = "detail";
var videoList = new Array();
var main={
    init:function () {
        //设置用户信息
        property.setUserInfo();
        pageType = localStorage.pageType;
        videoId = localStorage.videoId;
        localStorage.removeItem('videoId');
        localStorage.removeItem('pageType');
        //设置下拉框
        setSelect();

                //设置日期选择框
                layui.use('laydate', function(){
                    var laydate = layui.laydate;

            //执行一个laydate实例
            laydate.render({
                elem: '#makeTime' //指定元素
            });

            laydate.render({
                elem: '#applyTime' //指定元素
            });
        });
        var id = videoId;
        // loadData(id);
        layui.use('form', function(){
            var form = layui.form;
            form1 = form;
            var index = parent.layer.getFrameIndex(window.name);
            var json = {"id":id};
        });
    },
    initTable:function(){
        layui.use('form', function(){
            var form = layui.form;
            form1 =layui.form;
            form.render();
            //监听提交
            form.on('submit(save)', function(data){
                var postVideoId = $("#id").val();
                var apply = $("#apply").val();
                var applyOrg = $("#applyOrg").val();
                var applyTime = $("#applyTime").val();
                var applyReason = $("#applyReason").val();
                var remarks = $("#remarks").val();
                var approval = $("#approval").val();
                if (null == applyReason || null == approval || '' == applyReason || '' == approval){
                    top.layer.msg("必填项未填写");
                    return false;
                }
                var url = "PostVideo/saveQueryApply.do";
                var json = {"postVideoId":postVideoId,"apply":apply,"applyOrg":applyOrg
                    ,"applyTime":applyTime,"applyReason":applyReason,"remarks":remarks,"approval":approval};
                $.ajax({
                    url:property.getProjectPath()+url,
                    data:JSON.stringify(json),
                    contentType:"application/json",
                    type:'post',
                    async:false,
                    success:function(result) {
                        if (result.success == 1) {
                            top.layer.msg("申请成功");
                            parent.$t.goback("page/video/videoQueryList.html");
                        } else {
                            top.layer.msg(result.error.message);
                        }
                    },
                    error:function(result) {
                        top.layer.msg("系统异常");
                    }
                });
                return false;
            });

            form.on('submit(saveAndSubmit)', function(data){
                var postVideoId = $("#id").val();
                var apply = $("#apply").val();
                var applyOrg = $("#applyOrg").val();
                var applyTime = $("#applyTime").val();
                var applyReason = $("#applyReason").val();
                var remarks = $("#remarks").val();
                var approval = $("#approval").val();
                if (null == applyReason || null == approval || '' == applyReason || '' == approval){
                    top.layer.msg("必填项未填写");
                    return false;
                }
                var url = "PostVideo/saveAndSumitQueryApply.do";
                var json = {"postVideoId":postVideoId,"apply":apply,"applyOrg":applyOrg
                    ,"applyTime":applyTime,"applyReason":applyReason,"remarks":remarks,"approval":approval};
                $.ajax({
                    url:property.getProjectPath()+url,
                    data:JSON.stringify(json),
                    contentType:"application/json",
                    type:'post',
                    async:false,
                    success:function(result) {
                        if (result.success == 1) {
                            top.layer.msg("申请成功");
                            parent.$t.goback("page/video/videoQueryList.html");
                        } else {
                            top.layer.msg(result.error.message);
                        }
                    },
                    error:function(result) {
                        top.layer.msg("系统异常");
                    }
                });
                return false;
            });
        });
        loadTable();
    },
}
main.init();
main.initTable();

function xhrOnProgress(fun) {
    xhrOnProgress.onprogress = fun;
    return function() {
        var xhr = $.ajaxSettings.xhr();
        if (typeof xhrOnProgress.onprogress !== 'function')
            return xhr;
        if (xhrOnProgress.onprogress && xhr.upload) {
            xhr.upload.onprogress = xhrOnProgress.onprogress;
        }                return xhr;
    }
}

/**
 * 设置下拉框
 */
function setSelect() {
    var applySelect = component.getSelectSimplePlus(userList,null,"apply","id","name");
    $("#apply").append(applySelect);
    var applyOrgSelect = component.getSelectSimplePlus(orgList,null,"applyOrg","departmentId","departmentName");
    $("#applyOrg").append(applyOrgSelect);

    var applyReasonList = property.getDictData('apply_reason');
    var applyReasonSelect = component.getSelectSimplePlus(applyReasonList,null,"applyReason","dictCode","dictName");
    $("#applyReason").append(applyReasonSelect);

    videoList = setIdSelect();
    var videoListSelect = component.getSelectSimplePlus(videoList,null,"id","id","videoName");
    $("#id").append(videoListSelect);

    var approvalList = property.getApprovalList("zlglb");
    var approvalSelect = component.getSelectSimplePlus(approvalList,null,"approval","id","name");
    $("#approval").html(approvalSelect);

    $("#apply").val(userInfo.userId);
    $("#applyOrg").val(userInfo.orgId);
    $("#applyTime").val(formatSimpleDate(new Date()))
    $("#id").val(videoId);
}

function setFormData(data) {
    property.setForm($("#videoForm"),data);
    $("#makeTime").val(formatSimpleDate(data.makeTime))
    $("#videoType").val(data.videoType);
    $("#videoType").attr("disabled","disabled");
    $("#saveType").val(data.saveType);
    $("#saveType").attr("disabled","disabled");
    $("#source").val(data.source);
    $("#source").attr("disabled","disabled");
    $("#videoMark").val(data.videoMark);
    $("#videoMark").attr("disabled","disabled");
    tableId = data.attachment;
    checkStatus(data.status);
}
//检测状态
function checkStatus(status) {
    status =  property.getTextByValuePlus(statusDictList,status,"dictCode","dictName");
    $("#currentStatus").text(status);
}

function setIdSelect(){
    var datas = null;
    var json = {"currentUserId":userInfo.userId};
    $.ajax({
        type:"post",
        data:json,
        async:false,
        url:property.getProjectPath()+"PostVideo/getVideoListForSelect.do",
        success:function(result) {
            if (result.success == 1) {
                datas = result.data;
            } else {
                errorMsg(result.data);
            }
        },
        error:function(result) {
            errorMsg("系统异常");
        }
    });
    return datas;
}

