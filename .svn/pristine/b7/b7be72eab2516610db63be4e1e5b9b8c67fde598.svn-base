var form1;
var currentPage1 = 0;
var layedit1;
var layedit_index;
var main={
    init:function () {
        this.tabBind();
        $("#id").val(localStorage.diaryId);
        getContent(localStorage.diaryId);
        // $("#content").val(localStorage.diaryContent);
    },
    tabBind:function () {
        layui.use(['form','layedit'], function () {
            var form = layui.form;
            form1 = form;
            //发布
            form.on('submit(publish)', function(data){
                layedit1.sync(layedit_index);
                save();
                $("#content").val("");
                return false;
            });
        });

        layui.use('layedit', function(){
            layedit1 = layui.layedit;
            layedit_index = layedit1.build('content',{
                height: 200, //设置编辑器高度
                tool:[]
            }); //建立编辑器
        });
    }
}
main.init();

function save(){
    $.ajax({
        type:"post",
        async:false,
        data:{content: $("#content").val(), id:$("#id").val()},
        url:property.getProjectPath()+"personDiary/addOrUpdate.do",
        success:function(result) {
            if (result.success == 1) {
                top.layer.msg(result.data);
            } else {
                top.layer.msg(result.data);
            }
            var index=parent.layui.layer.getFrameIndex(window.name);
            parent.layui.layer.close(index);
        },
        error:function(result) {
            top.layer.msg("系统异常");
        }
    });
}

function getContent(id) {
    $.ajax({
        type:"post",
        async:false,
        data:{id:id},
        url:property.getProjectPath()+"personDiary/getDiaryById.do",
        success:function(result) {
            if (result.success == 1) {
                $("#content").val(result.data.content);
            } else {
                top.layer.msg("获取失败");
            }
        },
        error:function(result) {
            top.layer.msg("系统异常");
        }
    });
}

function checkMyOrg() {
    checkOrg(userInfo.userId);
}




