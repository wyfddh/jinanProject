layui.use(['form', 'layer', 'layedit', 'laydate', 'upload'], function () {
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        laydate = layui.laydate,
        $ = layui.jquery;

    var layedit = layui.layedit;
    layedit.build('content',{
        height: 150

    }); //建立编辑器

    laydate.render({
        elem: '#startDate'
    });

    laydate.render({
        elem: '#endDate'
    });

/*
    //创建一个编辑器
    var editIndex1 = layedit.build('content', {
        height: 214,
        uploadImage: {
            url: projectName+"/attach/uploadEditPic.do?projectName=esaleMuseumEditPic",
            type:"post"
        }
    });
*/

    var pathName=window.document.location.pathname;
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    var dataObject = null;
    var index = parent.layer.getFrameIndex(window.name);
    upload.render({
        elem: '#uploadVideo'
        ,url: projectName+'/attach/uploadVideo.do'
        ,data:{
            projectName:"introduceVideo"
        }
        ,field:'file'
        ,accept: 'video' //视频
        ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
            //加载层-风格4
            layer.msg('上传中', {
                icon: 16
                ,shade: 0.5
            });
        }
        ,done: function(res){
            if(res.success ==1){
                $("#videoId").val(res.data.id);
                $("#videoUrl").val(res.data.resultPath);
                $("#videoShow").attr("src",res.data.absolutePath);
                layer.msg("上传成功");
            }else{
                layer.msg("保存失败:"+res.data,{icon:2});
            }
        }
        ,error: function(){
            layer.msg("保存失败!",{icon:2});
        }
    });

    form.verify({
        newsName: function (val) {
            if (val == '') {
                return "文章标题不能为空";
            }
        },
        content: function (val) {
            if (val == '') {
                return "文章内容不能为空";
            }
        }
    })
    form.on("submit(saveIntroduce)", function (data) {
        var videoId = $("#videoId").val();
        if(videoId == "" || videoId == null){
            layer.msg("推介视频不能为空!",{icon:2});
            return false;
        }
        var indexLoading = layer.open({
            type: 1,
            title: false, //不显示标题
            closeBtn: 0,
            shadeClose: false,
            skin: "msg",
            content: "<div class='msg loadingMsg'><div class='msg-icon'></div><div class='msg-title'>数据提交中，请稍候</div><div class='msg-txt'></div></div>"
        });
        var formData = $("#form").serialize();
        $.ajax({
            type:"post",
            data:formData,
            url:projectName + '/introduce/saveIntroduce.do',
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
                }
            }
        })
        return false;
    })

    //初始化
    var initData = function() {
        dataObject = JSON.parse(localStorage["introduceData"]);
        getOrgList();
    }
    initData();
    function setData() {
        if (!isEmpty(dataObject)) {
            $("#id").val(dataObject.id);
            $("#videoName").val(dataObject.videoName);
            $("#museumId").val(dataObject.museumId);
            $("#content").val(dataObject.content);
            $("#videoId").val(dataObject.videoId);
            $("#videoUrl").val(dataObject.videoUrl);
            $("#videoShow").attr("src",dataObject.videoShowUrl);
            //1修改2查看
            if (dataObject.operationStatus == "1") {
                $(".showStatus").hide();
            } else if (dataObject.operationStatus == "2") {
                $(".editStatus").hide();
                $('input,select').attr("disabled","disabled");
                $("#uploadVideo").off();
              $('input,select,textarea').attr("disabled","disabled");
              form.render();
              $("iframe[textarea='content']").contents().find('body').attr("contenteditable",false);

              $(".layedit-tool-face").off();
              $(".layedit-tool-link").off();


            }
            form.render();
        }else{
            $(".showStatus").hide();
        }
    }

    //视频预览
    $("#videoShow").click(function(){
        var showUrl = $("#videoShow").attr("src");
        if(showUrl == ""){
            return false;
        }
        var index = layui.layer.open({
            title : "视频预览",
            type : 2,
            area: ['450px', '370px'],
            content : "../../page/localIntroduce/lookVideo.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                body.find("#video").attr("src",showUrl);
                console.log(showUrl);
            },
        })
    })

    $(".cancelBtn").click(function() {
        parent.layer.close(index);
        return false;
    })


    //获取页面下拉数据
    function getOrgList() {
        var data = {arr:['show_type_es','order_by']};
        $.ajax({
            type:"post",
            url:projectName + '/esaleMuseum/getSelectData.do',
            data:data,
            success:function(result) {
                if (result.success == 1) {
                    var map = result.data;
                    var showType = map.show_type_es;
                    var showStr = "";
                    for(var k = 0;k < showType.length;k++) {
                        showStr +="<option value='"+showType[k].dictCode+"' >"+showType[k].dictName+"</option>"
                    }
                    $("#type").append(showStr);
                    var dataList = map.museumList;
                    var orgStr = "";
                    for(var i = 0;i < dataList.length;i++) {
                        orgStr +="<option value='"+dataList[i].id+"' >"+dataList[i].museumName+"</option>"
                    }
                    $("#museumId").append(orgStr);
                    $("#joinMuseumId").append(orgStr);
                    setData(dataObject);
                    form.render();
                }
            }
        });
    }
})

function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == "")	{
        return true;
    }else{
        return false;
    }
}
