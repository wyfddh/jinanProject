layui.use(['form', 'layer', 'layedit', 'laydate', 'upload'], function () {
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;

    laydate.render({
        elem: '#activityTime'
        ,type: 'datetime'
        ,format: 'yyyy-MM-dd HH:mm'
    });

    var pathName=window.document.location.pathname;
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    var dataObject = null;
    var index = parent.layer.getFrameIndex(window.name);
    //用于同步编辑器内容到textarea
   // layedit.sync(editIndex);

    form.on("submit(saveActivity)", function (data) {
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
            url:projectName + '/esaleActivity/saveActivity.do',
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
                    setTimeout(function(){
                        parent.layer.close(index);
                    },2000)
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

    //添加表单整数验证
    form.verify({
        intNum: [
            /^[1-9][0-9]{0,3}$/
            ,'名额须大于0小于9999且仅能为正整数'
        ]
    });

    //创建一个编辑器
    var editIndex = layedit.build('description', {
        height: 214,
        uploadImage: {
            url: projectName+"/attach/uploadEditPic.do?projectName=ActivityEditPic",
            type:'post'
        }
    });

    $(function(){
        dataObject = JSON.parse(localStorage["activityData"]);
        getOrgList();
    });
    function setData() {
        if (!isEmpty(dataObject)) {
            $("#id").val(dataObject.id);
            $("#activityName").val(dataObject.activityName);
            $("#activityAddr").val(dataObject.activityAddr);
            $("#type").val(dataObject.type);
            $("#activityTime").val(dataObject.activityTime.substring(0,10));
            $("#quota").val(dataObject.quota);
            $("#cost").val(dataObject.cost);
            $("#demand").val(dataObject.demand);

            // alert(dataObject.type);
            // alert($("#type option[value='"+dataObject.type+"']").text());
            layedit.setContent(editIndex, dataObject.description, false);

            $("#type option[value='" + dataObject.type + "']").attr("selected", true);

            var pageId = dataObject.pictureId;
            var pageUrl = dataObject.pictureUrl;
            //1修改
            if (dataObject.operationStatus === "1") {
                $(".showStatus").hide();
                $("#picids").val(dataObject.pictureId);

                var picStr = '<div class="img picDiv" id="img' + pageId + '">'
                    + '<div class="img1"><img src=' + pageUrl + ' alt="" ></div>'
                    + '<div class="img2"><span class="img3" id="span' + pageId + '" mark=' + pageId + '>更换图片</span><span class="img4" mark=' + pageId + '>删除图片</span></div>'
                    + '</div>';
                $(".picDiv").replaceWith(picStr);
                $(".uploadBtn").hide();

                form.render();
            }
        }else{
            $(".showStatus").hide();
        }
    }
//获取页面下拉数据
    function getOrgList() {
        var data = {arr:['activity_type_es']};
        $.ajax({
            type:"post",
            url:projectName + '/esaleMuseum/getSelectData.do',
            data:data,
            success:function(result) {
                if (result.success == 1) {
                    var map = result.data;
                    var activityType = map.activity_type_es;
                    var activityStr = "";
                    for(var k = 0;k < activityType.length;k++) {
                        activityStr +="<option value='"+activityType[k].dictCode+"' >"+activityType[k].dictName+"</option>"
                    }
                    $("#type").append(activityStr);
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
        var projectName = 'esaleActivity';
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

