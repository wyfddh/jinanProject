var videoTypeDictList = null;
var videoMarkList = null;
var statusDictList = null;
var authSettingDictList = null;
var sourceDictList = null;
var saveTypeDictList = null;
var orgList = null;
var userType = "0";
var form1;
var main={
    init:function () {
        getDictData();
        setSelect();
        property.setUserInfo();
        if (userInfo.orgName == '影视部'){
            userType = '1';
        }
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

        /*$('#btnAdd1').click(function() {
         localStorage.pageType = "add";
         parent.$t.goToPage(this, "page/video/videoList.html");
         });*/

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
        var videoMark = $("#videoMark").val();
        var status = $("#status").val();
        table.render({
            elem: '#test'
            ,url:property.getProjectPath()+"labelManager/getlabelList.do"
            ,toolbar: '#toolbarDemo'
            ,title: '标签数据表'
            ,cols: [[
                {type: 'checkbox', fixed: 'left'}
                ,{field:'id', title:'编号', sort: true}
                ,{field:'createDate', title:'创建时间',templet:function (d) {
                    return formatDate(d.createDate);
                }}
                ,{field:'labelName', title:'标签'}
                ,{field:'vidoeNum', title:'资料数量'}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:300}
            ]]
            ,page: true
        });
        //头工具栏事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'getCheckData':
                    var data = checkStatus.data;
                    layer.alert(JSON.stringify(data));
                    break;
                case 'getCheckLength':
                    var data = checkStatus.data;
                    layer.msg('选中了：'+ data.length + ' 个');
                    break;
                case 'isAll':
                    layer.msg(checkStatus.isAll ? '全选': '未全选');
                    break;
                case 'batchAuthSetting':
                    var data = checkStatus.data;

                    var length = data.length;
                    if (length<=0){
                        alertMsg("未选中有效项！");
                    }else {
                        var ids = new Array();
                        for (var i=0;i<length;i++){
                            ids.push(data[i].id);
                        }
                        layer.confirm('', {
                            title:"设置确认",
                            area: ['450px', '218px'],
                            skin: 'demo-class',
                            success: function(layero, index){
                                form1.render();
                            },
                            content:"<form class='layui-form' id='batchSetForm'>"
                                + "<div class='layui-form-item'>"
                                + "<div><span>当前已选择"
                                + length
                                + "项有效数据，确认设置吗？</span></div>"
                                + "<div class='layui-input-block' style='margin-left: 0px' id='batchSetting'>"
                                + "<input type='radio' name='setting' value='1' title='不公开' checked>"
                                + "<input type='radio' name='setting' value='2' title='公开可查询'>"
                                + "<input type='radio' name='setting' value='3' title='公开可下载'>"
                                + "</div>"
                                + "</div>"
                                + "</form>",
                            btn: ['取消', '确认']
                        }, function(index, layero){
                            layer.close(index);
                        }, function(index){
                            var authSetting = $('#batchSetting input[name="setting"]:checked ').val()
                            var data = {"ids":ids,"map":authSetting};
                            $.ajax({
                                url:property.getProjectPath() + 'PostVideo/batchSetAuthSetting.do',
                                type:'post',
                                contentType:"application/json",
                                data:JSON.stringify(data),
                                success:function(result) {
                                    if (result.success == "1") {
                                        successMsg("批量权限设置成功！");
                                    } else {
                                        var resultMsg = result.error.message;
                                        errorMsg(resultMsg);
                                    }
                                    loadTable();
                                    layer.close(index);
                                }
                            })
                        });
                        return false;
                    }
                    batchAuthSetting();
            };
        });

        function addMuseumInfo(edit){
            //0:添加 1：查看 2：修改
            var title;
            var url;
            if (edit == "0") {
        /*        title = "添加博物馆";
                url = "../../page/video/videoDetail.html";*/

            } else if(edit == "1") {
                title = "查看";
                url = "../../page/labelManager/labelInfo.html";

            }else if(edit == "2"){
                title = "修改标签";
                url = "../../page/labelManager/updateLabels.html";

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
        $("#btnAdd").click(function(){
            addMuseumInfo("0");
        })

        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;
            //console.log(obj)
            if(obj.event === 'del'){
                var index = layer.confirm('真的删除行么', function(index){
                    // obj.del();
                    deletePostVideo(data.id);
                    window.location.reload();
                    loadTable();
                });
            } else if(obj.event === 'edit'){
                localStorage.videoId = data.id;
                localStorage.pageType = "edit";
                alert(localStorage.videoId);
                addMuseumInfo("2");
                //parent.$t.goToPage(this,"page/video/videoList.html");
            }else if(obj.event === 'detail'){
                localStorage.videoId = data.id;
                localStorage.pageType = "detail";
                addMuseumInfo("1");
                // parent.$t.goToPage(this,"page/video/videoList.html");
            }
            //审批
            else if(obj.event === 'approval'){
                localStorage.videoId = data.id;
                localStorage.pageType = "approval";
                parent.$t.goToPage(this,"page/video/videoList.html");
            }
            //权限设置
            else if (obj.event == "authSetting") {
                setAuthSetting(data.id);
            }
            //提交
            else if(obj.event == "commit"){
                var json = {"id":data.id,"currentUserId":userInfo.userId};
                $.ajax({
                    type:"post",
                    data:json,
                    async:false,
                    url:property.getProjectPath()+"PostVideo/commitProcess.do",
                    success:function(result) {
                        if (result.success == 1) {
                            successMsg("提交成功");
                            loadTable();
                        } else {
                            errorMsg(result.error.message);
                        }
                    },
                    error:function(result) {
                        errorMsg("系统异常");
                    }
                });
            }
            //撤回
            else if(obj.event == "revoke"){
                var json = {"id":data.id,"currentUserId":userInfo.userId};
                $.ajax({
                    type:"post",
                    data:json,
                    async:false,
                    url:property.getProjectPath()+"PostVideo/revokeProcess.do",
                    success:function(result) {
                        if (result.success == 1) {
                            successMsg("撤回成功");
                            loadTable();
                        } else {
                            errorMsg(result.error.message);
                        }
                    },
                    error:function(result) {
                        errorMsg("系统异常");
                    }
                });
            }
        });
    });
}

function setSelect() {
    // var videoTypeSelect  = component.getSelectPlus(videoTypeDictList,null,"videoType","dictCode","dictName");
    // $("#videoType").html(videoTypeSelect);
    var statusSelect  = component.getSelectSimplePlus(statusDictList,null,"status","dictCode","dictName");
    $("#status").append(statusSelect);
    var videoMarkSelect  = component.getSelectSimplePlus(videoMarkList,null,"videoMark","id","name");
    $("#videoMark").append(videoMarkSelect);
}

function getDictData() {
    var keys = ['video_type','video_status','permissions_settings','video_source','video_save_type']
    var dictDataMulti = property.getDictDataMulti(keys);
    statusDictList = dictDataMulti.video_status;
    videoSourceList = dictDataMulti.video_source;
    videoTypeList = dictDataMulti.video_type;
    authSettingDictList = dictDataMulti.permissions_settings;
    sourceDictList = dictDataMulti.video_source;
    saveTypeDictList = dictDataMulti.video_save_type;
    orgList = property.getAllOrgList();
    $.ajax({
        type:"get",
        async:false,
        url:property.getProjectPath()+"PostVideo/queryPostVideoTypeListTree.do",
        success:function(result) {
            if (result.code == 0) {
                videoMarkList = result.data;
            } else {
                top.layer.msg(result.message);
            }
        },
        error:function(result) {
            top.layer.msg("系统异常");
        }
    });
}
//删除
function deletePostVideo(id) {
    $.ajax({
        type:"post",
        async:false,
        url:property.getProjectPath()+"labelManager/delLabel.do?id="+id,
        success:function(result) {
            if (result.success == 1) {
                top.layer.msg("删除成功");
            } else {
                top.layer.msg(result.error.message);
            }
        },
        error:function(result) {
            top.layer.msg("系统异常");
        }
    });
}

function setAuthSetting(id){
    layer.confirm('', {
        title:"设置确认",
        area: ['450px', '218px'],
        skin: 'demo-class',
        success: function(layero, index){
            form1.render();
        },
        content:"<form class='layui-form' id='batchSetForm'>"
            + "<div class='layui-form-item'>"
            + "<div class='layui-input-block' style='margin-left: 0px' id='batchSetting'>"
            + "<input type='radio' name='setting' value='1' title='不公开' checked>"
            + "<input type='radio' name='setting' value='2' title='公开可查询'>"
            + "<input type='radio' name='setting' value='3' title='公开可下载'>"
            + "</div>"
            + "</div>"
            + "</form>",            btn: ['取消', '确认']
    }, function(index, layero){
        layer.close(index);
    }, function(index){
        var authSetting = $('#batchSetting input[name="setting"]:checked ').val()
        var data = {id:id,authSetting:authSetting};
        $.ajax({
            url:property.getProjectPath() + 'PostVideo/setAuthSetting.do',
            type:'post',
            data:data,
            success:function(result) {
                if (result.success == "1") {
                    successMsg("权限设置成功！");
                    loadTable();
                } else {
                    var resultMsg = result.error.message;
                    errorMsg(resultMsg);
                }
                loadTable();
                layer.close(index);
            }
        })
    });
    return false;
}

function checkMyOrg() {
    checkOrg(userInfo.userId);
}




