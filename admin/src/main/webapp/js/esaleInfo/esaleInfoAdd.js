layui.use(['form', 'layer', 'layedit', 'laydate', 'upload'], function () {
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;

    var pathName=window.document.location.pathname;
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    var dataObject = null;
    var index = parent.layer.getFrameIndex(window.name);
    //用于同步编辑器内容到textarea
   // layedit.sync(editIndex);

    form.on("submit(saveInfo)", function (data) {
        var content = $("#content").val();
        if(content == "" || content == null){
            layer.msg("资讯详情不能为空!",{icon:2});
            return false;
        }
        layedit.sync(editIndex);
        var indexLoading = layer.open({
            type: 1,
            title: false, //不显示标题
            closeBtn: 0,
            shadeClose: false,
            skin: "msg",
            content: "<div class='msg loadingMsg'><div class='msg-icon'></div><div class='msg-title'>数据提交中，请稍候</div><div class='msg-txt'></div></div>"
        });
        var formData =  $('#form').serialize();
        $.ajax({
            type:"post",
            data: formData,
            url:projectName + '/esaleInfo/saveInfo.do',
            success:function(result) {
                top.layer.close(indexLoading);
                if (result.success == 1) {
                    layer.open({
                        type: 1,
                        title: false, //不显示标题
                        closeBtn: 0,
                        shadeClose: false,
                        time:2000,
                        skin: "msg",
                        content: "<div class='msg successMsg'><div class='msg-icon'></div><div class='msg-title'>提交成功</div><div class='msg-txt'></div></div>"
                    });
                    /*setTimeout(function(){
                        parent.layer.close(index);
                    },2000)*/
                    parent.layer.close(index);
                    window.parent.location.reload();
                } else {
                    layer.open({
                        type: 1,
                        title: false, //不显示标题
                        closeBtn: 0,
                        shadeClose: false,
                        time:2000,
                        skin: "msg",
                        content: "<div class='msg errorMsg'><div class='msg-icon'></div><div class='msg-title'>提交失败</div><div class='msg-txt'>"+result.data+"</div></div>"
                    });
                    /*setTimeout(function(){
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    },2000)*/
                }
            }
        });
        return false;
    });

    //创建一个编辑器
    var editIndex = layedit.build('content', {
        height: 214,
        uploadImage: {
            url: projectName+"/attach/uploadEditPic.do?projectName=InfoEditPic",
            type:'post'
        }
    });


    $(function(){
        dataObject = JSON.parse(localStorage["InfoData"]);
        getOrgList();
    });
    function setData() {
        if (!isEmpty(dataObject)) {
            $("#id").val(dataObject.id);
            $("#infoTopic").val(dataObject.infoTopic);

            // alert(dataObject.type);
            // alert($("#type option[value='"+dataObject.type+"']").text());
            layedit.setContent(editIndex, dataObject.content, false);

            $("#museumId option[value='" + dataObject.museumId + "']").attr("selected", true);
            $("#type option[value='" + dataObject.type + "']").attr("selected", true);

            var pageId = dataObject.pageId;
            var pageUrl = dataObject.pageUrl;
            //1修改
            if (dataObject.operationStatus === "1") {
                $(".showStatus").hide();
                $("#picids").val(dataObject.pageId);

                var picStr = '<div class="img picDiv" id="img' + pageId + '">'
                    + '<div class="img1"><img src=' + pageUrl + ' alt="" ></div>'
                    + '<div class="img2"><span class="img3" id="span' + pageId + '" mark=' + pageId + '>更换图片</span><span class="img4" mark=' + pageId + '>删除图片</span></div>'
                    + '</div>';
                $(".picDiv").replaceWith(picStr);
                $(".uploadBtn").hide();

                form.render();
            }else if (dataObject.operationStatus === "2") {
                $(".editStatus").hide();
                $('input,select,textarea').attr("disabled","disabled");

                var picStr = '<div class="img picDiv" id="img'+ pageId +'">'
                    +'<div class="img1"><img src='+ pageUrl +' alt="" ></div>'
                    +'</div>';
                $(".picDiv").replaceWith(picStr);
                $(".uploadBtn").hide();

                form.render();
                $("iframe[textarea='content']").contents().find('body').attr("contenteditable",false);
                $(".layedit-tool-face").off();
                $(".layedit-tool-link").off();
            }
        }else{
            $(".showStatus").hide();
        }
    }
//获取页面下拉数据
    function getOrgList() {
        var data = {arr:['info_type_es']};
        $.ajax({
            type:"post",
            url:projectName + '/esaleMuseum/getSelectData.do',
            data:data,
            success:function(result) {
                if (result.success == 1) {
                    var map = result.data;
                    var museumList = map.museumList;
                    var museumStr = "";
                    for(var k = 0;k < museumList.length;k++) {
                        museumStr +="<option value='"+museumList[k].id+"' >"+museumList[k].museumName+"</option>"
                    }
                    $("#museumId").append(museumStr);
                    var info_type_es = map.info_type_es;
                    var typeStr = "";
                    for(var j = 0;j < info_type_es.length;j++) {
                        typeStr +="<option value='"+info_type_es[j].dictCode+"' >"+info_type_es[j].dictName+"</option>"
                    }
                    $("#type").append(typeStr);
                    setData();
                    form.render();
                }
            }
        });
    }


    $(".cancelBtn").click(function() {
        parent.layer.close(index);
        return false;
    });

    $(".uploadBtn").click(function() {
        var projectName = 'esaleInfoshow';
        uploadPicture(projectName);
    });

});

function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == "")	{
        return true;
    }else{
        return false;
    }
}

