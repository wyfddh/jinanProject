var videoTypeDictList = null;
var statusDictList = null;
var authSettingDictList = null;
var sourceDictList = null;
var saveTypeDictList = null;
var userList = null;
var orgList = null;
var applyReasonList = null;
var form1;
var tableIns;
var main={
    init:function () {
        getDictData();
        setSelect();
        property.setUserInfo();
        this.initTable();
        this.tabBind();
    },
    initTable:function(){
        var _this=this;
        loadTable();
    },
    tabBind:function () {
        layui.use(['form'], function () {
            var form = layui.form;
            form1 = form;
            //监听查询
            form.on('submit(formDemo)', function(data){
                loadTable();
                return false;
            });
            //监听重置
            $("[type='reset']").click(function () {
                $(this).parents(".layui-form").find("input").val("");
                $(this).prev().click();
            });
        });


        //查询事件
        layui.use('form', function(){
            var form = layui.form;

        });
    }
}
main.init();

/**
 * 加载表格数据
 */
function loadTable() {
    layui.use('table', function(){
        var table = layui.table;
        var keywords = $("#keywords").val();
        var applyStatus = $("#applyStatus").val();
        tableIns = table.render({
            elem: '#test'
            ,url:property.getProjectPath()+"PostVideo/getQueryVideoList.do?keywords="+keywords+
            "&applyStatus="+applyStatus
            +"&currentUserId="+userInfo.userId
            // ,toolbar: '#toolbarDemo'
            ,title: '影视资料数据表'
            ,cols: [[
                {type: 'checkbox', fixed: 'left'}
                ,{field:'videoCode', title:'编号', sort: true}
                ,{field:'videoName', title:'资料名称'}
                ,{field:'apply', title:'申请人',templet: function(res){
                    return property.getTextByValuePlus(userList,res.apply,"id","name");
                }}
                ,{field:'applyOrg', title:'所属部门',templet: function(res){
                    return property.getTextByValuePlus(orgList,res.applyOrg,"departmentId","departmentName");
                }}
                ,{field:'applyReason', title:'申请原因',templet: function(res){
                    return property.getTextByValuePlus(applyReasonList,res.applyReason,"dictCode","dictName");
                }}
                ,{field:'applyTime', title:'申请日期',templet: function(res){
                    return formatSimpleDate(res.applyTime);
                }}
                ,{field:'applyStatus', title:'申请状态',templet: function(res){
                    return property.getTextByValuePlus(statusDictList,res.applyStatus,"dictCode","dictName");
                }}
                ,{field:'remarks', title:'备注'}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:300}
            ]]
            ,page: true
        });


        function addMuseumInfo(edit){
            // 1：查看 2：审批
            var title;
            var url;
            if (edit == "1") {
                title = "查看";
                url = "../../page/video/videoQueryShow.html";

            } else if(edit == "2") {
                title = "修改";
                url = "../../page/video/videoQueryShow.html";
            }
            var index = layui.layer.open({
                title : title,
                type : 2,
                area: ['80%', '700px'],
                content : url,
                end :function() {
                    tableIns.reload();
                }
            })
            layui.layer.full(index);
            window.sessionStorage.setItem("index",index);
            //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
            $(window).on("resize",function(){
                layui.layer.full(window.sessionStorage.getItem("index"));
            })
        }

        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;
            //console.log(obj)
            if(obj.event === 'detail'){
                localStorage.videoId = data.partyId;
                localStorage.pageType = "detail";
                localStorage.processInstId = data.id;
                addMuseumInfo(1)
            }
            //审批
            else if(obj.event === 'edit'){
                localStorage.videoId = data.partyId;
                localStorage.pageType = "edit";
                localStorage.processInstId = data.id;
                addMuseumInfo(2)
            }else if(obj.event === 'del'){
                var json = {"processInstId":data.id};
                $.ajax({
                    type:"post",
                    data:json,
                    async:false,
                    url:property.getProjectPath()+"PostVideo/deleteApply.do",
                    success:function(result) {
                        if (result.success == 1) {
                            successMsg("删除成功!")
                            loadTable();
                        } else {
                            errorMsg(result.error.message);
                        }
                    },
                    error:function(result) {
                        errorMsg("系统异常");
                    }
                });
            }else if (obj.event === 'revoke'){
                var json = {"postVideoId":data.partyId,"apply":userInfo.userId};
                $.ajax({
                    type:"post",
                    data:JSON.stringify(json),
                    contentType:"application/json",
                    async:false,
                    url:property.getProjectPath()+"PostVideo/revokeQueryApply.do",
                    success:function(result) {
                        if (result.success == 1) {
                            successMsg("撤回成功!")
                            loadTable();
                        } else {
                            errorMsg(result.error.message);
                        }
                    },
                    error:function(result) {
                        errorMsg("系统异常");
                    }
                });
            }else if (obj.event === 'commit'){
                var json = {"postVideoId":data.partyId,"apply":userInfo.userId};
                $.ajax({
                    type:"post",
                    data:JSON.stringify(json),
                    contentType:"application/json",
                    async:false,
                    url:property.getProjectPath()+"PostVideo/submitQueryApply.do",
                    success:function(result) {
                        if (result.success == 1) {
                            successMsg("提交成功!")
                            loadTable();
                        } else {
                            errorMsg(result.error.message);
                        }
                    },
                    error:function(result) {
                        errorMsg("系统异常");
                    }
                });
            }else if(obj.event === 'download'){
                window.location.href = property.getProjectPath()+'PostVideo/downloadVideoFile.do?postVideoId='+data.partyId;
            }

        });
    });
}

function setSelect() {
    var applyStatusSelect  = component.getSelectSimplePlus(statusDictList,null,"applyStatus","dictCode","dictName");
    $("#applyStatus").append(applyStatusSelect);
}

function getDictData() {
    var keys = ['video_type','approval_status','permissions_settings','video_source','video_save_type','apply_reason']
    var dictDataMulti = property.getDictDataMulti(keys);
    statusDictList = dictDataMulti.approval_status;
    videoSourceList = dictDataMulti.video_source;
    videoTypeList = dictDataMulti.video_type;
    authSettingDictList = dictDataMulti.permissions_settings;
    sourceDictList = dictDataMulti.video_source;
    saveTypeDictList = dictDataMulti.video_save_type;
    applyReasonList = dictDataMulti.apply_reason;
    orgList = property.getAllOrgList();
    userList = property.getAllUserList();
}




