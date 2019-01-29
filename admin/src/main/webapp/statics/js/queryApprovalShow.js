var videoSaveTypeList = null;
var videoSourceList = null;
var videoTypeList = null;
var authSettingList = null;
var tableId = '';
var attachmentsList = null;
var commentsList = new Array();
var statusDictList = property.getDictData("video_status");
var approvalStatusDictList = property.getDictData("approval_status");
var form1;
var videoId = null;
var processInstId = null;
//所有用户列表
var userList = property.getAllUserList();
//所有部门列表
var orgList = property.getAllOrgList();
//页面属性
var pageType = "detail";
var main={
    init:function () {
        //设置用户信息
        property.setUserInfo();
        //加载业务字典
        getDictData();
        //设置下拉框
        setSelect();
        pageType = localStorage.pageType;
        videoId = localStorage.videoId;
        localStorage.removeItem('videoId');
        localStorage.removeItem('pageType');
        if (pageType == 'edit' ||  pageType == 'detail'){
            processInstId = localStorage.processInstId;
            loadApplyInfo(processInstId);
            if (pageType == 'detail'){
                $("#applyReason").attr("disabled","disabled");
                $("#remark").attr("disabled","disabled")
                $("#approval").attr("disabled","disabled")
                $("#btn").hide();
            }else {
                $("#btn").show();
            }
        }


        //设置日期选择框
        layui.use('laydate', function(){
            var laydate = layui.laydate;

            //执行一个laydate实例
            laydate.render({
                elem: '#applyTime' //指定元素
            });
        });
        // loadData(id);
        layui.use('form', function(){
            var form = layui.form;
            form1 = form;
            var index = parent.layer.getFrameIndex(window.name);
            var json = {"id":videoId};
            //加载数据
            $.ajax({
                type:"post",
                data:json,
                async:false,
                url:property.getProjectPath()+"PostVideo/queryPostVideoDtoById.do",
                success:function(result) {
                    if (result.success == 1) {
                        console.log(result.data);
                        setFormData(result.data);
                        form.render();
                        main.initTable();
                        main.tabBind();
                    } else {
                        errorMsg(result.error.message);
                    }
                },
                error:function(result) {
                    errorMsg("系统异常");
                }
            });
        });



    },
    initTable:function(){
        layui.use('form', function(){
            var form = layui.form;
            form1 =layui.form;
            //监听提交
            //监听提交
            $("#save").click( function(data){
                var postVideoId = $("#id").val();
                var apply = $("#apply").val();
                var applyOrg = $("#applyOrg").val();
                var applyTime = $("#applyTime").val();
                var applyReason = $("#applyReason").val();
                var remarks = $("#remarks").val();
                var approval = $("#approval").val();
                if (null == applyReason || null == approval || '' == applyReason || '' == approval){
                    errorMsg("必填项未填写");
                    return false;
                }
                var url = "PostVideo/updateApply.do";
                var json = {"processInstId":processInstId,"apply":apply,"applyOrg":applyOrg
                    ,"applyTime":applyTime,"applyReason":applyReason,"remarks":remarks,"approval":approval};
                $.ajax({
                    url:property.getProjectPath()+url,
                    data:json,
                    type:'post',
                    async:false,
                    success:function(result) {
                        if (result.success == 1) {
                            successMsg("提交成功");
                            setTimeout(function(){
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index);
                            },2000)
                        } else {
                            errorMsg(result.error.message);
                        }
                    },
                    error:function(result) {
                        errorMsg("系统异常");
                    }
                });
                return false;
            });

            $("#saveAndSubmit").click(function(data){
                var postVideoId = $("#id").val();
                var apply = $("#apply").val();
                var applyOrg = $("#applyOrg").val();
                var applyTime = $("#applyTime").val();
                var applyReason = $("#applyReason").val();
                var remarks = $("#remarks").val();
                var approval = $("#approval").val();
                if (null == applyReason || null == approval || '' == applyReason || '' == approval){
                    errorMsg("必填项未填写");
                    return false;
                }
                var url = "PostVideo/updateApplyAndSubmit.do";
                var json = {"processInstId":processInstId,"apply":apply,"applyOrg":applyOrg
                    ,"applyTime":applyTime,"applyReason":applyReason,"remarks":remarks,"approval":approval};
                $.ajax({
                    url:property.getProjectPath()+url,
                    data:json,
                    type:'post',
                    async:false,
                    success:function(result) {
                        if (result.success == 1) {
                            successMsg("申请成功");
                            setTimeout(function(){
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index);
                            },2000)
                        } else {
                            errorMsg(result.error.message);
                        }
                    },
                    error:function(result) {
                        errorMsg("系统异常");
                    }
                });
                return false;
            });
        });
        loadTable();
    },

    tabBind:function () {
        //导出函数
        $(".layui-btn-green").on({
            'click':function () {
                return false
            }
        })
        //时间切换
        $(".searchBtn").on({
            'click':function () {
                var index=$(this).index();
                if($(this).hasClass('active'))return false
                if(index==1){
                    $(".searchBtn").removeClass("active");
                    $(".searchBtn").eq(0).addClass("active");
                }else{
                    $(".searchBtn").removeClass("active");
                    $(".searchBtn").eq(1).addClass("active");
                }

                return false
            }
        });



        $("#cancel").click(function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            return false;
        });
    }
}
main.init();
//日历切换
function cDayFunc() {
    main.initTable()
}
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
 * 获取全部业务字典数据
 */
function getDictData() {
    var keys = ['video_save_type','video_source','video_type','permissions_settings']
    var dictDataMulti = property.getDictDataMulti(keys);
    videoSaveTypeList = dictDataMulti.video_save_type;
    videoSourceList = dictDataMulti.video_source;
    videoTypeList = dictDataMulti.video_type;
    authSettingList = dictDataMulti.permissions_settings;
}

/**
 * 设置下拉框
 */
function setSelect() {
    var videoTypeSelect  = component.getSelectSimplePlus(videoTypeList,null,"videoType","dictCode","dictName");
    $("#videoType").append(videoTypeSelect);
    var sourceSelect  = component.getSelectSimplePlus(videoSourceList,null,"source","dictCode","dictName");
    $("#source").append(sourceSelect);
    var saveTypeSelect  = component.getSelectSimplePlus(videoSaveTypeList,null,"saveType","dictCode","dictName");
    $("#saveType").append(saveTypeSelect);

    var attachmentsListSelect  = component.getSelectSimplePlus(attachmentsList,null,"attachmentList","attId","attName");
    $("#attachmentsList").append(attachmentsListSelect);

    var approvalList = property.getApprovalList("zlglb");
    var approvalSelect = component.getSelectSimplePlus(approvalList,null,"approval","id","name");
    $("#approval").append(approvalSelect);

    var applySelect = component.getSelectSimplePlus(userList,null,"apply","id","name");
    $("#apply").append(applySelect);
    var applyOrgSelect = component.getSelectSimplePlus(orgList,null,"applyOrg","departmentId","departmentName");
    $("#applyOrg").append(applyOrgSelect);

    var applyReasonList = property.getDictData('apply_reason');
    var applyReasonSelect = component.getSelectSimplePlus(applyReasonList,null,"applyReason","dictCode","dictName");
    $("#applyReason").html(applyReasonSelect);

    $("#apply").val(userInfo.userId);
    $("#applyOrg").val(userInfo.orgId);
    $("#applyTime").val(formatSimpleDate(new Date()))
}

function loadData(id) {
    layui.use('form', function(){
        var form = layui.form;
        var index = parent.layer.getFrameIndex(window.name);
        var json = {"id":id};
        //加载数据
        $.ajax({
            type:"get",
            data:json,
            async:false,
            url:property.getProjectPath()+"PostVideo/queryPostVideoDtoById.do",
            success:function(result) {
                if (result.success == 1) {
                    console.log(result.data);
                    setFormData(result.data);
                    form.render('select');
                } else {
                    errorMsg(result.error.message);
                }
            },
            error:function(result) {
                errorMsg("系统异常");
            }
        });
    });
}

function setFormData(data) {
    property.setForm($("#videoForm"),data);
    var relativeCollection = eval(data.relativeCollection);
    var culName = [];
    if (null != relativeCollection) {
        for (var i = 0; i < relativeCollection.length; i++) {
            culName.push(relativeCollection[i].culName);
        }
        $("#relativeCollection").val(culName.join("，"));
    } else {
        $('#relativeCollection').val("");
    }
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
}
//检测状态
function checkStatus(status) {
    status =  property.getTextByValuePlus(approvalStatusDictList,status,"dictCode","dictName");
    $("#currentStatus").text(status);
}

function loadAttachments(fkId) {
    var datas = null;
    var json = {"fkId":fkId};
    $.ajax({
        type:"get",
        data:json,
        async:false,
        url:property.getProjectPath()+"attach/getAttachmentsByFkId.do",
        success:function(result) {
            if (result.success == 1) {
                console.log(result.data);
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

function queryPostVideoComments(postVideoId) {
    var datas = null;
    var json = {"postVideoId":postVideoId};
    $.ajax({
        type:"post",
        data:json,
        async:false,
        url:property.getProjectPath()+"PostVideoComments/queryPostVideoComments.do",
        success:function(result) {
            if (result.success == 1) {
                console.log(result.data);
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

function loadTable() {
    layui.use('table', function(){
        var table = layui.table;
        table.render({
            elem: '#approvalInfo'
            ,url:property.getProjectPath()+'PostVideo/getQueryApplyRecord.do?processInstId='+processInstId
            ,cols: [[
                {type:'numbers',title:'序号'}
                ,{field:'actionTime', title:'时间',templet: function(res){
                    return formatDate(res.actionTime);
                }}
                ,{field:'actionName', title:'操作'}
                ,{field:'apply', title:'操作人',templet: function(res){
                    return property.getTextByValuePlus(userList,res.apply,"id",'name');
                }}
                ,{field:'applyOrg', title:'所属部门',templet: function(res){
                    return property.getTextByValuePlus(orgList,res.applyOrg,"departmentId",'departmentName');
                }}
                ,{field:'actionResult', title:'资料状态',templet: function(res){
                        return property.getTextByValuePlus(approvalStatusDictList,res.actionResult,"dictCode",'dictName');
                }}
                ,{field:'remark', title:'备注'}
            ]]
        });


    });
}

function loadApplyInfo(processInstId){
    var json = {"processInstId":processInstId};
    $.ajax({
        type:"post",
        data:json,
        async:false,
        url:property.getProjectPath()+"PostVideo/getApplyInfo.do",
        success:function(result) {
            if (result.success == 1) {
                var datas = result.data;
                $("#apply").val(datas.apply);
                $("#applyOrg").val(datas.applyOrg);
                $("#applyTime").val(formatSimpleDate(datas.applyTime));
                $("#applyReason").val(datas.actionName);
                $("#remark").val(datas.remark);
                $("#approval").val(datas.approval);
                checkStatus(datas.applyStatus);
            } else {
                errorMsg(result.data);
            }
        },
        error:function(result) {
            errorMsg("系统异常");
        }
    });
}




