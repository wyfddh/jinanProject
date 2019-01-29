layui.use(['form', 'layer', 'layedit', 'laydate', 'upload'], function () {
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;

    // laydate.render({
    //     elem: '#showDate'
    // });

    var pathName=window.document.location.pathname;
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    var dataObject = null;
    var index = parent.layer.getFrameIndex(window.name);
    //用于同步编辑器内容到textarea
   // layedit.sync(editIndex);

   /* laydate.render({
        elem: '#showDate'
    });*/

    form.on("submit(saveShow)", function (data) {
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
        // alert(formData);
        $.ajax({
            type:"post",
            data: formData,
            url:projectName + '/esaleShow/saveShow.do',
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

    //创建一个编辑器
    var editIndex = layedit.build('showDescription', {
        height: 214,
        uploadImage: {
            url: projectName+"/attach/uploadEditPic.do?projectName=showEditPic",
            type:'post'
        }
    });

    $(function(){
        dataObject = JSON.parse(localStorage["showData"]);
        getOrgList();
    });
    function setData() {
        if (!isEmpty(dataObject)) {
            $("#id").val(dataObject.id);
            $("#showName").val(dataObject.showName);
            $("#type").val(dataObject.type);
            $("#showDate").val(dataObject.showDate);
            $("#address").val(dataObject.address);
            $("#pagePictureid").val(dataObject.pagePictureid);
            // alert(dataObject.type);
            // alert($("#type option[value='"+dataObject.type+"']").text());
            layedit.setContent(editIndex, dataObject.showDescription, false);

            $("#museumId option[value='"+dataObject.museumId+"']").attr("selected",true);
            $("#joinMuseumId option[value='"+dataObject.joinMuseumId+"']").attr("selected",true);
            $("#type option[value='"+dataObject.type+"']").attr("selected",true);

            var pageId = dataObject.pagePictureid;
            var pageUrl = dataObject.pageUrl;
            var picList = dataObject.picList;
            //1修改2查看
            if (dataObject.operationStatus === "1") {
                $(".showStatus").hide();
                $("#picids").val(dataObject.showPictureids);
                var picStr = '<div class="img picDiv" id="img'+ pageId +'">'
                    +'<div class="img1"><img src='+ pageUrl +' alt="" ></div>'
                    +'<div class="img2"><span class="img3" id="span'+ pageId +'" mark='+ pageId +'>更换图片</span><span class="img4" mark='+ pageId +'>删除图片</span></div>'
                    +'</div>';
                $(".picDiv").replaceWith(picStr);
                $("#pagePic").hide();

                for (var i = 0;i < picList.length;i++) {
                    var picStr1;

                    picStr1 = '<div class="img" id="img'+ picList[i].attId +'">'
                        +'<div class="img1"><img src='+ picList[i].attPath +' alt="" ></div>'
                        +'<div class="img2"><span class="img5" mark='+ picList[i].attId +'>删除图片</span></div>'
                        +'</div>';

                    $("#showPic").before(picStr1);
                }
                form.render();
            } else if (dataObject.operationStatus === "2") {
                $(".editStatus").hide();
                $('input,select,textarea').attr("disabled","disabled");

                var picStr = '<div class="img picDiv" id="img'+ pageId +'">'
                    +'<div class="img1"><img src='+ pageUrl +' alt="" ></div>'
                    +'</div>';
                $(".picDiv").replaceWith(picStr);
                $("#pagePic").hide();

                for (var j = 0;j < picList.length;j++) {
                    var picStr2;

                    picStr2 = '<div class="img" id="img'+ picList[j].attId +'">'
                        +'<div class="img1"><img src='+ picList[j].attPath +' alt="" ></div>'
                        +'<div class="img2"></div>'
                        +'</div>';

                    $("#showPic").before(picStr2);
                }
                $("#showPic").hide();

                form.render();
                $("iframe[textarea='showDescription']").contents().find('body').attr("contenteditable",false);
                $(".layedit-tool-face").off();
                $(".layedit-tool-link").off();
            }
        }else{
            $(".showStatus").hide();
        }
    }
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

    $("#pagePic").click(function() {
        var projectName = 'esaleShow';
        uploadPictureSingle(projectName);
    });

    $("#showPic").click(function() {
        // //最大只能上传20张图片
        var len =  $("#picUpload").find('img').length;
        if(len==20){
            layer.msg("可上传图片数量已达最大限度",{icon:2});
            return false;
        }
        var projectName = 'esaleShow';
        uploadPicture(projectName);
    });

    //更换封面
    $('.pad').on("click",".img3",function(){
        $('#pagePic').click();
    });

    //删除封面
    $('.pad').on("click",".img4",function(){
        $("#pagePictureid").val("");
        $(".picDiv").remove();
        $("#pagePic").before("<div class='picDiv'></div>");
        $("#pagePic").show();
    })


    //删除图片
    $('.pad').on("click",".img5",function(){
        var picId = $(this).attr("mark");
        var delpicids = $("#delpicids").val();
        delpicids += picId + ",";
        $("#delpicids").val(delpicids);
        var picids = $("#picids").val();
        picids=picids.replace(picId + ",","");
        $("#picids").val(picids);
        $('#img'+ picId).remove();
    })

});

//上传单图片
function uploadPictureSingle(projectName){
    var indexUpload = layui.layer.open({
        title : "裁剪图片",
        type : 2,
        area: ['80%', '700px'],
        content : "../../lib/cropHead.html",
        success : function(layero, indexUpload){
            localStorage.removeItem('map');
            var body = layui.layer.getChildFrame('body', indexUpload);
            body.find("#projectName").val(projectName);
            setTimeout(function(){
                layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            },500)
        },
        end :function() {
            var map = JSON.parse(localStorage["map"]);
            console.log(map);
            //插入图片
            var picStr = '<div class="img picDiv" id="img'+ map.id +'">'
                +'<div class="img1"><img src='+ map.absolutePath +' alt="" ></div>'
                +'<div class="img2"><span class="img3" id="span'+ map.id +'" mark='+ map.id +'>更换图片</span><span class="img4" mark='+ map.id +'>删除图片</span></div>'
                +'</div>'
            $(".picDiv").replaceWith(picStr);
            var pagePictureid = map.id;
            $("#pagePictureid").val(pagePictureid);
            $("#pagePic").hide();
        }
    })
    layui.layer.full(indexUpload);
    window.sessionStorage.setItem("index",indexUpload);
    //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
    $(window).on("resize",function(){
        layui.layer.full(window.sessionStorage.getItem("index"));
    })
};

//上传多图片
function uploadPicture(projectName,imgSize){
    var content =  "../../lib/cropHead.html";
    if (!isEmpty(imgSize)) {
        content = "../../lib/cropHead1.html";
    }
    var indexUpload = layui.layer.open({
        title : "裁剪图片",
        type : 2,
        area: ['80%', '700px'],
        content : content,
        success : function(layero, indexUpload){
            localStorage.removeItem('map');
            var body = layui.layer.getChildFrame('body', indexUpload);
            body.find("#projectName").val(projectName);
            setTimeout(function(){
                layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            },500)
        },
        end :function() {
            var map = JSON.parse(localStorage["map"]);
            //插入图片
            var picStr = '<div class="img" id="img'+ map.id +'">'
                +'<div class="img1"><img src='+ map.absolutePath +' alt="" ></div>'
                +'<div class="img2"><span class="img5" mark='+ map.id +'>删除图片</span></div>'
                +'</div>'
            $("#showPic").before(picStr);
            var picids = $("#picids").val();
            picids += map.id + ",";
            $("#picids").val(picids);
            console.log($("#picids").val());
        }
    })
    layui.layer.full(indexUpload);
    window.sessionStorage.setItem("index",indexUpload);
    //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
    $(window).on("resize",function(){
        layui.layer.full(window.sessionStorage.getItem("index"));
    })
}


function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == "")	{
        return true;
    }else{
        return false;
    }
}

