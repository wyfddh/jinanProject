layui.use(['form', 'layer', 'layedit', 'laydate', 'upload'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;

    // laydate.render({
    //     elem: '#showDate'
    // });

    var pathName = window.document.location.pathname;
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    var index = parent.layer.getFrameIndex(window.name);
    //用于同步编辑器内容到textarea
    // layedit.sync(editIndex);

    form.on("submit(saveNews)", function (data) {
        var indexLoading = layer.open({
            type: 1,
            title: false, //不显示标题
            closeBtn: 0,
            shadeClose: false,
            skin: "msg",
            content: "<div class='msg loadingMsg'><div class='msg-icon'></div><div class='msg-title'>数据提交中，请稍候</div><div class='msg-txt'></div></div>"
        });
        var formData = $('#form').serialize();

        $.ajax({
            type: "post",
            data: formData,
            url: projectName + '/esaleNews/saveNews.do',
            success: function (result) {
                top.layer.close(indexLoading);
                if (result.success == 1) {
                    layer.open({
                        type: 1,
                        title: false, //不显示标题
                        closeBtn: 0,
                        shadeClose: false,
                        time: 2000,
                        skin: "msg",
                        content: "<div class='msg successMsg'><div class='msg-icon'></div><div class='msg-title'>提交成功</div><div class='msg-txt'></div></div>"
                    });
                    setTimeout(function () {
                        parent.layer.close(index);
                    }, 2000)
                } else {
                    layer.open({
                        type: 1,
                        title: false, //不显示标题
                        closeBtn: 0,
                        shadeClose: false,
                        time: 2000,
                        skin: "msg",
                        content: "<div class='msg errorMsg'><div class='msg-icon'></div><div class='msg-title'>提交失败</div><div class='msg-txt'>" + result.data + "</div></div>"
                    });
                }
            }
        });
        return false;
    });


    $(".cancelBtn").click(function () {
        parent.layer.close(index);
        return false;
    });

    $("#newsPic").click(function () {
        // //最大只能上传10张图片
        // var len =  $("#picUpload").find('img').length;
        // if(len==10){
        //     layer.msg("可上传图片数量已达最大限度",{icon:2});
        //     return false;
        // }
        var projectName = 'esaleNews';
        uploadPicture(projectName);
    });

    //删除图片
    $('.pad').on("click", ".img5", function () {
        var picId = $(this).attr("mark");
        var delpicids = $("#delpicids").val();
        delpicids += picId + ",";
        $("#delpicids").val(delpicids);
        var picids = $("#picids").val();
        picids = picids.replace(picId + ",", "");
        $("#picids").val(picids);
        $('#img' + picId).remove();
    })

});

//上传多图片
function uploadPicture(projectName, imgSize) {
    var content = "../../lib/cropHead.html";
    if (!isEmpty(imgSize)) {
        content = "../../lib/cropHead1.html";
    }
    var indexUpload = layui.layer.open({
        title: "裁剪图片",
        type: 2,
        area: ['80%', '700px'],
        content: content,
        success: function (layero, indexUpload) {
            localStorage.removeItem('map');
            var body = layui.layer.getChildFrame('body', indexUpload);
            body.find("#projectName").val(projectName);
            setTimeout(function () {
                layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            }, 500)
        },
        end: function () {
            var map = JSON.parse(localStorage["map"]);
            //插入图片
            var picStr = '<div class="img" id="img' + map.id + '">'
                + '<div class="img1"><img src=' + map.absolutePath + ' alt="" ></div>'
                + '<div class="img2"><span class="img5" mark=' + map.id + '>删除图片</span></div>'
                + '</div>'
            $("#newsPic").before(picStr);
            var picids = $("#picids").val();
            picids += map.id + ",";
            $("#picids").val(picids);
        }
    })
    layui.layer.full(indexUpload);
    window.sessionStorage.setItem("index", indexUpload);
    //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
    $(window).on("resize", function () {
        layui.layer.full(window.sessionStorage.getItem("index"));
    })
}


function isEmpty(obj) {
    if (typeof obj == "undefined" || obj == null || obj == "") {
        return true;
    } else {
        return false;
    }
}

