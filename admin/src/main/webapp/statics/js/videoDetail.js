var videoSaveTypeList = null;
var videoTypeList = null;
var authSettingList = null;
var tableId = '';
var attachmentsList = null;
var commentsList = new Array();
var form1;
var userId;
var attCount = 0;
//页面属性
var pageType = "add";
var projectName = property.getProjectPath();
var collectArray = [];
var choseCollect = [];
var main={

    init:function () {
        //设置用户信息
        property.setUserInfo();
        if (null != localStorage.pageType){
            pageType = localStorage.pageType;
            localStorage.removeItem('pageType');
        }

        layui.config({
            base: '../../' //此处路径请自行处理, 可以使用绝对路径
        }).extend({
            // formSelects1: 'common/js/formSelects-v4.js',
            inputTags: 'layui/lay/modules/inputTags'
        });

        //加载业务字典
        getDictData();
        //设置下拉框
        setSelect();

        //设置日期选择框
        layui.use('laydate', function(){
            var laydate = layui.laydate;

            //执行一个laydate实例
            laydate.render({
                elem: '#makeTime' //指定元素
                ,max:getNowFormatDate()
            });
        });
        var isopen = getQueryString("isopen");
        if(isopen == '1'){
        }else{
            $("#cancel").hide();
        }
        if (null != localStorage.userInfo){
            var userInfo = JSON.parse(localStorage.userInfo);
            userId = userInfo.userId;
            //设置上传部门
            $("#uploadOrg").val(userInfo.orgId);
            //设置上传人
            $("#other1").val(userInfo.userId);
        }else
        {
            alert("用户未登陆，请先登录!");
            window.location.href= prototype.getProjectPath;
        }
        if (pageType == "add"){
            var ls = property.getLs("videoList","video");
            $("#videoCode").val(ls);
            $("#videoCode").attr("readonly",true);
            //设置文件批号
            tableId = property.getTimeJson();
            $("#attachment").val(tableId);
            var htmlStr = component.getRadio(authSettingList,"1","authSetting","dictCode","dictName");
            $("#authSettingRadio").append(htmlStr);
            initInputTags();
            this.initTable();
            this.tabBind();
        }else
        {
            if(localStorage.status == '4'){
                $("#save").hide();
            }
            var id = localStorage.videoId;
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
                            attachmentsList = loadAttachments(tableId);
                            var htmlStr = component.getRadio(authSettingList,result.data.authSetting,"authSetting","dictCode","dictName");
                            $("#authSettingRadio").append(htmlStr);
                            form.render();
                            if (null != attachmentsList){
                                attCount = attachmentsList.length;
                                $("#demoList").append(component.getAttachmentList(attachmentsList));
                            }
                            main.initTable();
                            main.tabBind();
                        } else {
                            top.layer.msg(result.error.message);
                        }
                    },
                    error:function(result) {
                        top.layer.msg("系统异常");
                    }
                });
            });

        }



    },
    initTable:function(){

        $('#searchCollect').click(function () {
            var formSelects = layui.formSelects;
            formSelects.config('select1', {
                keyName: 'culName',            //自定义返回数据中name的key, 默认 name
                keyVal: 'culId',            //自定义返回数据中value的key, 默认 value
                success: function(id, url, searchVal, result){      //使用远程方式的success回调
                    collectArray = result.data;
                },
            }, true);
            formSelects.data('select1', 'server', {
                url:property.getProjectPath()+"esaleCollection/getCollectionListByType.do?culCategory="+$('#culCategory').val()+"&culName="+$($($('.xm-select-label')[0]).children()[0]).val()
            });
        });
        layui.use('form', function(){
            var form = layui.form;
            form1 =layui.form;
            $.get(projectName + '/esaleInterfaceColelct/getInterfaceCollectSelectType.do?typegroupid=A0211', function (res) {
                if (res.success == 1) {
                    var collectionCategory = res.data;
                    var collectionCategoryStr = "";
                    for(var k = 0;k < collectionCategory.length;k++) {
                        collectionCategoryStr +="<option value='"+collectionCategory[k].typecode+"' >"+collectionCategory[k].typename+"</option>"
                    }
                    $("#culCategory").append(collectionCategoryStr);
                    form.render("select");
                }
            });

            var formSelects = layui.formSelects;
            formSelects.config('select1', {
                keyName: 'culName',            //自定义返回数据中name的key, 默认 name
                keyVal: 'culId',            //自定义返回数据中value的key, 默认 value
                success: function(id, url, searchVal, result){      //使用远程方式的success回调
                    collectArray = result.data;
                },
            }, true);
            var culName = $($($('.xm-select-label')[0]).children()[0]).val();
            formSelects.data('select1', 'server', {
                url:property.getProjectPath()+"esaleCollection/getCollectionListByType.do"
            });

            form.on('select(culCategory)', function(data){
                var formSelects = layui.formSelects;
                formSelects.config('select1', {
                    keyName: 'culName',            //自定义返回数据中name的key, 默认 name
                    keyVal: 'culId',            //自定义返回数据中value的key, 默认 value
                    success: function(id, url, searchVal, result){      //使用远程方式的success回调
                        collectArray = result.data;
                    },
                }, true);
                var culName = $($($('.xm-select-label')[0]).children()[0]).val();
                formSelects.data('select1', 'server', {
                    url:property.getProjectPath()+"esaleCollection/getCollectionListByType.do?culCategory="+data.value
                });
            });


            //添加藏品
            $("#addCollect").click(function(){
                var formSelects = layui.formSelects;
                var ids = formSelects.value('select1', 'val');
                var hasAddName = [];
                for (var i = 0, length = ids.length; i < length; i++) {
                    var id = ids[i];
                    var flg = false;
                    for (var j = 0, size = choseCollect.length; j < size; j++) {
                        var collect = choseCollect[j];
                        if (id == collect.culId) {
                            hasAddName.push(collect.culName);
                            flg = true;
                            break;
                        }
                    }
                    if (flg) {
                        continue;
                    }
                    for (var j = 0, size = collectArray.length; j < size; j++) {
                        var collect = collectArray[j];
                        if (id == collect.culId) {
                            choseCollect.push(collect);
                            $('#choseCollectList').append($('<div style="width: auto" class="layui-btn layui-btn-sm" id="'+collect.culId+'">'+collect.culName+'&nbsp;<i class="layui-icon" onclick="deleteCollect(\''+collect.culId+'\')">&#x1006;</i></div>'));
                            break;
                        }
                    }
                }
            });

            //监听提交
            form.on('submit(save)', function(data){
                var postVideo = property.getFormJson("#videoForm");attachment
                var culId = [];
                for (var i = 0; i < choseCollect.length; i++) {
                    culId.push({
                        culId: choseCollect[i].culId,
                        culName: choseCollect[i].culName
                    })
                }
                postVideo.relativeCollection = JSON.stringify(culId);
                var url = "PostVideo/addPostVideo.do";
                var actionType = "1";
                var labelName = $("#labelName").val();
                if (pageType == "edit"){
                    url = "PostVideo/updatePostVideo.do";
                }else {
                    if (attCount <= 0){
                        top.layer.msg("请上传附件");
                        return false;
                    }
                }
                var json = {"postVideo":postVideo,"actionType":actionType,"currentUserId":userId,"labelName":labelName};
                $.ajax({
                    url:property.getProjectPath()+url,
                    data:JSON.stringify(json),
                    contentType:"application/json",
                    type:'post',
                    async:false,
                    success:function(result) {
                        if (result.success == 1) {
                            top.layer.msg("保存成功");
                            location.href="page/video/videoList.html";
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

            form.on('submit(saveAndPublish)', function(data){
                var postVideo = property.getFormJson("#videoForm");
                var culId = [];
                for (var i = 0; i < choseCollect.length; i++) {
                    culId.push({
                        culId: choseCollect[i].culId,
                        culName: choseCollect[i].culName
                    })
                }
                postVideo.relativeCollection = JSON.stringify(culId);
                var url = "PostVideo/addPostVideo.do";
                var actionType = "3";
                var labelName = $("#labelName").val();
                if (pageType == "edit"){
                    url = "PostVideo/updatePostVideo.do";
                }else {
                    if (attCount <= 0){
                        top.layer.msg("请上传附件");
                        return false;
                    }
                }
                var json = {"postVideo":postVideo,"actionType":actionType,"currentUserId":userInfo.userId,"labelName":labelName};
                $.ajax({
                    url:property.getProjectPath()+url,
                    data:JSON.stringify(json),
                    contentType:"application/json",
                    type:'post',
                    async:false,
                    success:function(result) {
                        if (result.success == 1) {
                            top.layer.msg("发布成功");
                            parent.$t.goback("page/video/videoList.html");
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

        layui.use(['upload','element'], function(){
            var $ = layui.jquery
                ,upload = layui.upload,element = layui.element;
            var demoListView = $('#demoList')
                ,uploadListIns = upload.render({
                elem: '#test10'
                ,url: property.getProjectPath()+"attach/uploadForPost.do?tableName="+"post_video"+"&tableId="+tableId
                ,accept: 'file'
                ,multiple: true
                ,auto: false
                ,xhr:xhrOnProgress
                ,progress:function(index,value){//上传进度回调 value进度值
                    var tr = demoListView.find('#upload-'+ index)
                        ,tds = tr.children();
                    tds.eq(1).html('<span style="color: red;">正在上传</span>');
                    element.progress('progressBar'+index, value+'%')//设置页面进度条
                    // console.log(e,value);
                }
                ,bindAction: '#testListAction'
                ,choose: function(obj){
                    var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                    //读取本地文件
                    obj.preview(function(index, file, result){
                        /* var tr = $(['<tr id="upload-'+ index +'">'
                             ,'<td>'+ file.name +'</td>'
                             ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
                             ,'<td>等待上传</td>'
                             ,'<td>'
                             ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                             ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                             ,'</td>'
                             ,'</tr>'].join(''));*/
                        var tr=$(["<li>" +
                        "                                    <div class='upLeft' id='upload-"+index+"'>" +
                        "                                        <span class=\"fileName\">"+file.name+"</span>" +
                        "                                        <span class=\"fileState\">准备上传</span>" +
                        "                                    </div>" +
                        "                                    <div class=\"upRight\">" +
                        "                                        <div class='layui-progress layui-col-md8 layui-col-sm8' lay-showPercent='yes' lay-filter='progressBar"+index+"'>" +
                        "                                            <div class=\"layui-progress-bar layui-progress-big layui-bg-red\" lay-percent=\"30%\">" +
                        '<span class="layui-progress-text">'+'0%'+'</span>'+'</div>' +
                        "                                        </div>" +
                        "                                        <a href=\"javascript:void (0);\" style='margin-left:15px;' class=\"layui-col-md1 layui-col-sm1 layui-hide demo-reload\">重传</a>" +
                        "                                        <a href=\"javascript:void (0);\" style='margin-left:15px;' class=\"layui-col-md1 layui-col-sm1 demo-cancel\">取消</a>" +
                        "                                    </div>" +
                        "                                </li>"].join(''));
                        //单个重传
                        tr.find('.demo-reload').on('click', function(){
                            obj.upload(index, file);
                        });
                        demoListView.append(tr);
                        //删除

                    });
                }
                ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                    //layer.load(); //上传loading
                }
                ,done: function(res, index, upload){
                    if(res.code == 1){ //上传成功
                        attCount = attCount+1;
                        var tr = demoListView.find('#upload-'+ index)
                            ,tds = tr.children();
                        tds.eq(1).html('<span style="color: #3B82FF;">上传成功</span>');
                        //tds.eq(2).html(''); //清空操作
                        tr.siblings(".upRight").find(".demo-reload").remove();
                        tr.siblings(".upRight").find(".demo-cancel").addClass("demo-delete");
                        tr.siblings(".upRight").find(".demo-cancel").text("删除");
                        tr.siblings(".upRight").find(".demo-cancel").attr("data-id",res.data.id);
                        tr.siblings(".upRight").find(".demo-delete").removeClass("demo-cancel");

                        tr.siblings(".upRight").find(".layui-bg-red").addClass("layui-bg-green");
                        tr.siblings(".upRight").find(".layui-bg-green").removeClass("layui-bg-red");
                        //重新设置下拉框
                        form1.render('select');
                        return delete this.files[index]; //删除文件队列已经上传成功的文件
                    }else {
                        var tr = demoListView.find('#upload-'+ index)
                            ,tds = tr.children();
                        tds.eq(1).html('<span style="color: #3B82FF;">'+res.msg+'</span>');
                        //tds.eq(2).html(''); //清空操作
                        //tr.siblings(".upRight").find(".demo-reload").remove();
                        //tr.siblings(".upRight").find(".demo-delete").remove();
                        // return delete this.files[index]; //删除文件队列已经上传成功的文件
                    }
                    this.error(index, upload);
                }
                ,error: function(index, upload){
                    var tr = demoListView.find('#upload-'+ index)
                    //     ,tds = tr.children();
                    // tds.eq(1).html('<span style="color: #FF5722;">上传失败</span>');
                    tr.siblings(".upRight").find('.demo-reload').removeClass('layui-hide'); //显示重传
                }
            });

        });
        //删除附件
        $('#demoList').on('click','.demo-delete',function(){
            var attId = $(this).attr("data-id");
            // delete files[index]; //删除对应的文件
            $(this).parent().parent().remove();
            deleteAttachment(attId);
            attachmentsList = loadAttachments(tableId);
           /* var attachmentsListSelect  = component.getSelectSimplePlus(attachmentsList,null,"attachmentList","attId","attName");
            $("#attachmentsList").empty();
            $("#attachmentsList").append(attachmentsListSelect);
            form1.render('select');*/
            attCount = attCount-1;
            // uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
        });
        //取消上传
        $('#demoList').on('click','.demo-cancel',function(){
            var attId = $(this).attr("data-id");
            // delete files[index]; //删除对应的文件
            $(this).parent().parent().remove();
            // uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
        });

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
            parent.$(".myRefresh").click();
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

function initInputTags(tags){
    if(tags == null || tags == undefined){
        tags = [];
    }
    layui.use(['inputTags'],function(){
        var inputTags = layui.inputTags;
        inputTags.render({
            elem:'#inputTags',//定义输入框input对象
            content: tags,//默认标签
            aldaBtn: true,//是否开启获取所有数据的按钮
            done: function(value, events){ //回车后的回调
                var that = this;
                $(".labelName").val(that.content);
                console.log(that.content);
            }
        })
    })
}

/**
 * 获取全部业务字典数据
 */
function getDictData() {
    var keys = ['video_type','permissions_settings']
    var dictDataMulti = property.getDictDataMulti(keys);
    videoSaveTypeList = dictDataMulti.video_save_type;
    videoTypeList = dictDataMulti.video_type;
    authSettingList = dictDataMulti.permissions_settings;
}

/**
 * 设置下拉框
 */
function setSelect() {
    var videoTypeSelect  = component.getSelectSimplePlus(videoTypeList,null,"videoType","dictCode","dictName");
    $("#videoType").append(videoTypeSelect);
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
                    top.layer.msg(result.error.message);
                }
            },
            error:function(result) {
                top.layer.msg("系统异常");
            }
        });
    });
}

function setFormData(data) {
    property.setForm($("#videoForm"),data);
    var collects = eval(data.relativeCollection);
    for (var i = 0; i < collects.length; i++) {
        var collect = collects[i];
        choseCollect.push(collect);
        $('#choseCollectList').append($('<div style="width: auto" class="layui-btn layui-btn-sm" id="'+collect.culId+'">'+collect.culName+'&nbsp;<i class="layui-icon" onclick="deleteCollect(\''+collect.culId+'\')">&#x1006;</i></div>'));
    }
    $("#makeTime").val(formatSimpleDate(data.makeTime))
    $("#videoType").val(data.videoType);
    $("#source").val(data.source);
    tableId = data.attachment;
    $("#labelName").val(data.labelName);

    var labelNameString = (typeof(data.labelName) == "undefined"||typeof(data.labelName) == "null" || data.labelName == null)?"":data.labelName;
    var tags = [];
    if(labelNameString.length > 0){
        tags = labelNameString.split(",");
    }
    initInputTags(tags);
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
                top.layer.msg(result.data);
            }
        },
        error:function(result) {
            top.layer.msg("系统异常");
        }
    });
    return datas;
}



//删除附件

function deleteAttachment(attId) {
    var json = {"attId":attId};
    $.ajax({
        type:"post",
        data:json,
        async:false,
        url:property.getProjectPath()+"attach/deleteAttachment.do",
        success:function(result) {
            if (result.success == 1) {
                top.layer.msg("删除成功");
                /*attachmentsList = loadAttachments(tableId);
                var attachmentsListSelect  = component.getSelectSimplePlus(attachmentsList,null,"attachmentList","attId","attName");
                $("#attachmentsList").append(attachmentsListSelect);
                form1.render('select');*/
            } else {
                top.layer.msg(result.data);
            }
        },
        error:function(result) {
            top.layer.msg("系统异常");
        }
    });
}

function deleteCollect(id) {
    for (var i = 0, length = choseCollect.length; i < length; i++) {
        var culId = choseCollect[i].culId;
        if (id == culId) {
            choseCollect.splice(i,1);
            $('#'+id).remove();
            break;
        }
    }
}
