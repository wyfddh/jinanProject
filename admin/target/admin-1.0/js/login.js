layui.use(['form','layer','jquery'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer
        $ = layui.jquery;

    var pathName=window.document.location.pathname;
    projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

    //登录按钮
    form.on("submit(login)",function(data){
        var loginData = $("#loginForm").serialize();
        $.ajax({
            url : projectName+"/frontLogin.do",
            type : "post",
            data :  loginData,
            dataType : "json",
            success : function(result){
                if(result.success != 1){
                    //失败
                    $("#err_msg").show();
                }else{
                    window.location.href = projectName+"/page/workBench/workbench.html";
                }
            },
        })
    })
})
