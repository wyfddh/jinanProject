var videoSaveTypeList = null;
var videoTypeList = null;
var authSettingList = null;
var tableId = '';
var commentsList = new Array();
var form1;
var videoId = null;
//页面属性
var pageType = "detail";
var main={
    init:function () {

        //设置用户信息
        // property.setUserInfo();

        pageType = localStorage.pageType;
        videoId = localStorage.videoId;
        localStorage.removeItem('pageType');
        localStorage.removeItem('videoId');
        //加载业务字典
        getDictData();
        //设置下拉框
        setSelect();

            var id = videoId;
            // loadData(id);
            layui.use('form', function(){
                var form = layui.form;
                form1 = form;
                var index = parent.layer.getFrameIndex(window.name);
                var json = {"id":id};
                //加载数据
                $.ajax({
                    type:"post",
                    data:json,
                    async:false,
                    url:property.getProjectPath()+"PostVideo/queryPostVideoDtoById.do",
                    success:function(result) {
                        if (result.success == 1) {
                            setFormData(result.data);
                            attachmentsList = loadAttachments(tableId);
                            form.render();
                            //显示附件
                            if (null != attachmentsList){
                                var attList = '';
                                for (var i=0;i<attachmentsList.length;i++){
                                    var attachment = attachmentsList[i];
                                    var type = 'img'
                                    var srcStr = attachment.attPath;
                                    var data = attachment.attPath;
                                    if (attachment.attFileType == 1){
                                        type = 'img';
                                    }else if (attachment.attFileType == 4){
                                        type = 'video';
                                        srcStr = "../../statics/img/分组 2.svg";
                                    }else if (attachment.attFileType == 3){
                                        type = 'audio'
                                        srcStr = "../../statics/img/分组 3.svg";
                                    }
                                    var liStr = "<li class=\"imgItem\" data-type="+type+">\n" +
                                        "                                <img  src='"+srcStr+"' alt='' data='"+data+"'>\n" +
                                        "                                <p class='myfont'>"+attachment.attName+"</p>\n" +

                                        "                            </li>";
                                    attList = attList+liStr;

                                }
                                $(".imgList").append(attList);
                            }
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



    },

    tabBind:function () {
        $(".imgList li").on({
            'click':function () {
                if($(this).attr("data-type")=="img"){
                    layer.open({
                        type: 1,
                        title:false,
                        closeBtn: 0, //不显示关闭按钮
                        // area: ['420px', '240px'], //宽高
                        shadeClose: true, //开启遮罩关闭
                        content: "<div style='position: relative;height: 450px;min-width: 450px'><img style='max-width:" +
                            " 600px;position:" +
                            " absolute;max-height:100%;margin: auto;left: 0;right: 0;top:0;bottom:0'" +
                            " src='"+$(this).find("img").attr("data")+"'/></div> "
                        /*  content: "<div style='position: relative;height: 800px'><img style='max-width: 800px;position:" +
                          " absolute;margin: auto;left: 0;right: 0;top:0;bottom:0'" +
                          " src='"+$(this).find("img").attr("data")+"'/></div> "*/
                    })
                }else if($(this).attr("data-type")=="video"){
                    parent.layer.open({
                        type: 1,
                        title:false,
                        closeBtn: 0, //不显示关闭按钮
                        shadeClose: true, //开启遮罩关闭
                        content: "<video src='"+$(this).find("img").attr("data")+ "' controls='controls'" +
                            " height='300'>" +
                            "您的浏览器不支持 video 标签。" +
                            "'</video> "
                    })
                }else{
                    parent.layer.open({
                        type: 1,
                        title:false,
                        closeBtn: 0, //不显示关闭按钮
                        shadeClose: true, //开启遮罩关闭
                        content: "<audio src='"+$(this).find("img").attr("data")+"' controls='controls'>" +
                            "</audio> "
                    })
                }
            }
        })
    }
}
main.init();
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
    var keys = ['video_save_type','video_type','permissions_settings']
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
    var saveTypeSelect  = component.getSelectSimplePlus(videoSaveTypeList,null,"saveType","dictCode","dictName");
    $("#saveType").append(saveTypeSelect);
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
    tableId = data.attachment;
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




