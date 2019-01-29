layui.use(['form','layer','table','laydate'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        table = layui.table;

        //监听表格复选框选择
        table.on('checkbox(introduceManager)', function(obj){
            console.log(obj)
        });
    //头工具栏事件
    table.on('toolbar(introduceManager)', function(obj){
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
        };
    });



    $('.searchVal').bind('keydown', function (event) {
        var event = window.event || arguments.callee.caller.arguments[0];
        if (event.keyCode == 13){
            searKeywordCache = $('.search-data-input').val();
            reloadTable();
        }
    });


    $('.demoTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

        laydate.render({
            elem: '#dateRange',
            range: '~'
        });



    var pathName=window.document.location.pathname;
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

    //用户列表
    var tableIns = table.render({
        elem: '#introduceManager',
        url : projectName + '/esalePubUserManager/getEsalePubUserList.do',
        request:{
            pageName: 'currentPage',
            limitName: 'size'
        },
        cellMinWidth : 95,
        page : true,
        loading:false,
        limits : [10,15,20,25],
        limit : 10,
        id : "introduceManagerTable",
        cols : [[
            {type:'checkbox', fixed:"left"},
            {type:"numbers",title: '序号', width:70, align:"center"},
            {field:'id', title: '用户ID', width:200,align:'center',templet:"#mumeumImg"},
            {field: 'userName', title: '用户名',  align:'center'},
            {field: 'phone', title: '手机号',  align:'center'},
            {field: 'collectionNum', title: '收藏藏品',  align:'center'},
            {field: 'activityNum', title: '活动报名',  align:'center'},
            {field: 'notJoinStateNum', title: '活动缺席',  align:'center'},
            {field: 'isYoung', title: '青年用户',  align:'center'},
           /* {title: '推荐',  align:'center',templet: '#recommendBar'},*/
           /* {title: '操作',width:200,  toolbar:'#introduceManagerBar',align:"center"}*/
            {title:'操作', toolbar: '#barDemo', width:150}
        ]],
        done:function(d){
            $("[data-field = 'mumeumImg']").children(".layui-table-cell").css({"height":"100%","max-width":"100%","position":"relative"});
            $("[data-field = 'mumeumImg']").find("img").css({"max-width":"100%","max-height":"100%","position":"absolute","top":"50%","left":"50%","transform":"translate(-50%, -50%)"});
        }
    });


    $(".search_btn").on("click",function(){
        reloadTable();
    });
    /*$("#resetBtn").on("click",function(){
        table.reload("introduceManagerTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {

            }
        })
    })*/

    $("#resetBtn").on("click",function(){
        table.reload("introduceManagerTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {

            }
        })
        window.location.reload();
    })

    function reloadTable() {
        table.reload("introduceManagerTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                key:$("#key").val(),
                phone:$("#phone").val(),
                isYoung:$("#isYoung").val(),
                orderBy:$("#orderBy").val()
            }
        })
    }

    form.on('switch(recommend)', function(data){
        var recommendName = data.elem.name;
        var recommendId = data.elem.value;
        var recommendStatus;
        if (data.elem.checked) {
            recommendStatus = "1";
        } else {
            recommendStatus = "0";
        }
        $.ajax({
            type:"post",
            url:projectName + '/introduce/modifyIntroduceRecommend.do',
            data:{"recommendStatus":recommendStatus,"recommendId":recommendId,"recommendName":recommendName},
            success:function(result) {
                if (result.success == 1) {
                    reloadTable();
                    form.render();
                } else {
                    var resultMsg = result.data;
                    layer.open({
                        type: 1,
                        title: false,
                        closeBtn: 0,
                        time:2000,
                        shadeClose: true,
                        skin: "msg",
                        content: "<div class='msg alertMsg'><div class='msg-icon'></div><div class='msg-title'>"
                        + resultMsg
                        + "</div><div class='msg-txt'></div></div>"
                    });
                    $(data.elem).attr("checked",false);
                    form.render();
                }
            },
            error:function(result) {

            }
        })
    });

    form.on('select(orderBy)',function(){
        reloadTable();
    })

    var $ = layui.$, active = {
        getCheckData: function(){ //获取选中数据
            var checkStatus = table.checkStatus('idTest')
                ,data = checkStatus.data;
            layer.alert(JSON.stringify(data));
        }
        ,getCheckLength: function(){ //获取选中数目
            var checkStatus = table.checkStatus('idTest')
                ,data = checkStatus.data;
            layer.msg('选中了：'+ data.length + ' 个');
        }
        ,isAll: function(){ //验证是否全选
            var checkStatus = table.checkStatus('idTest');
            layer.msg(checkStatus.isAll ? '全选': '未全选')
        }
    };



    table.on('tool(introduceManager)', function(obj){
        var data = obj.data;
        //console.log(obj)
        if(obj.event === 'del'){
            layer.confirm('确定要冻结吗？',{icon:3, title:'确认'}, function(index){
                var json = {"id":data.id};
                $.ajax({
                    type:"get",
                    data:json,
                    async:false,
                    url:projectName+"/esalePubUserManager/freezeUser.do",
                    success:function(result) {
                        if (result.success == 1) {
                            top.layer.msg("冻结用户成功");
                            reloadTable();
                        } else {
                            top.layer.msg("冻结用户失败");
                        }
                    },
                    error:function(result) {
                        top.layer.msg("系统异常");
                    }
                });
                layer.close(index);
            });
        } else if(obj.event === 'edit'){
            localStorage.pubUserType = "edit";
            localStorage.id = data.id;
           /* window.$t.goToPage(this,"page/public/user/list.html");*/
            data.operationStatus="1"
            addMuseumInfo("2");

        }else if(obj.event === 'detail'){
            localStorage.pubUserType = "detail";
            localStorage.id = data.id;
            addMuseumInfo("1");
           /* window.$t.goToPage(this,"page/esaleUserManger/esaleUserList.html");*/
            /**
             * \page\esaleUserManger\esaleUserInfo.html
             */
        }


    });
//添加

    function addMuseumInfo(edit){
        //0:添加 1：查看 2：修改
        var title;
        var url;
        if (edit == "0") {
            title = "添加博物馆";
            url = "../../page/video/videoDetail.html";
        } else if(edit == "1") {
            title = "查看博物馆";
            url = "../../page/esaleUserManger/esaleUserInfo.html";
        }else if(edit == "2"){
            title = "修改";
            url = "../../page/esaleUserManger/esaleUpdate.html";
        }

        localStorage["activityData"]=JSON.stringify(edit);
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
        // window.sessionStorage.setItem("index",index);
        // //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        // $(window).on("resize",function(){
        //     layui.layer.full(window.sessionStorage.getItem("index"));
        // })
    }
    $("#btnAdd").click(function(){
        addMuseumInfo("0");
    })


    /*function addInformation(edit){
        var id = edit.id;
        var title;
        if (edit) {
            if (edit.operationStatus == "1") {
                title = "修改"
            } else {
                title = "查看"
                url = "../../page/video/videoShow.html";
            }
            localStorage["dataObject"]=JSON.stringify(edit);
        } else {
            title = "添加";
            localStorage["dataObject"]=JSON.stringify("");
        }
        var index = layui.layer.open({
            title : title,
            type : 2,
            area: ['80%', '700px'],
            content : "../../page/esaleUserManger/esaleUserInfo.html?id="+id,
            success : function(layero, index){
               /!* var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    form.render();
                }*!/
                var body = layer.getChildFrame('body', index);//建立父子联系
                var iframeWin = window[layero.find('iframe')[0]['name']];
                var inputList = body.find('input');
                $(inputList).val(id);
            },
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
    }*/





    //添加
   /* function addMuseumInfo(edit){
        debugger
        var title;
        if (edit) {
            if(edit.operationStatus == '1'){
                title = "修改信息"
            }else{
                title = "查看用户信息"
            }
            localStorage["introduceData"]=JSON.stringify(edit);
        } else {
            title = "添加信息"
            localStorage["introduceData"]=JSON.stringify("");
        }
        var index = layui.layer.open({
            title : title,
            type : 2,
            area: ['80%', '700px'],
            content : "../../page/esaleUserManger/esaleUserInfomation.html",
            success : function(layero, index){
            },
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
    }*/
  /*  $(".addNews_btn").click(function(){
        addMuseumInfo();
    })*/

/*
    function getSelectData() {
        var data = {arr:['order_by']};
        $.ajax({
            type:"post",
            url:projectName + '/museumManager/getSelectData.do',
            data:data,
            success:function(result) {
                if (result.success == 1) {
                    var map = result.data;
                    var order_by = map.order_by;
                    var orderStr = "";
                    for(var k = 0;k < order_by.length;k++) {
                        orderStr +="<option value='"+order_by[k].dictCode+"' >"+order_by[k].dictName+"</option>"
                    }
                    $("#orderBy").append(orderStr);

                    form.render();
                }
            }
        })
    }
    var initForm = function() {
        $(".hide").hide();
        getSelectData();
    }

    initForm();*/

    //列表操作
    /*table.on('tool(introduceManager)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            data.operationStatus = "1";
            addMuseumInfo(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('是否确定删除该地方推介？',{icon:3, title:'提示信息'},function(index){
                $.get(projectName + '/introduce/deleteIntroduce.do',{
                    id : data.id,
                },function(result){
                    if (result.success == 1) {
                        layer.open({
                            type: 1,
                            title: false, //不显示标题
                            closeBtn: 0,
                            time:1000,
                            shadeClose: true,
                            skin: "msg",
                            content: "<div class='msg successMsg'><div class='msg-icon'></div><div class='msg-title'>删除成功！</div><div class='msg-txt'></div></div>"
                        });
                    } else {
                        layer.open({
                            type: 1,
                            title: false, //不显示标题
                            closeBtn: 0,
                            shadeClose: true,
                            skin: "msg",
                            content: "<div class='msg errorMsg'><div class='msg-icon'></div><div class='msg-title'>删除失败</div><div class='msg-txt'>"+result.data+"</div></div>"
                        });
                    }
                    tableIns.reload();
                    layer.close(index);
                })
            });
        }else if(layEvent === 'show'){//查看
            data.operationStatus = "2";
            addMuseumInfo(data);
        }else if(layEvent === 'videoShow'){
            var showUrl = data.videoShowUrl;
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
        }
    });*/

    //批量删除
    $(".delAll_btn").click(function(){
        //得到当前表格里面的checkbox的选中对象集合
        var checkStatus = table.checkStatus('introduceManagerTable'),//选中状态
            data = checkStatus.data;//选中的对象集
        var ids="a=1";
        if(data.length > 0) {
            for (var i in data) {
                ids+="&ids="+data[i].id;
            }
            layer.confirm('确定删除？', {icon: 3, title: '提示信息'}, function (index) {
                $.post(projectName + "/esalePubUserManager/freezeUsers.do?"+ids,function(data){
                    //刷新table
                    tableIns.reload();
                    //关闭提示框
                    layer.close(index);
                })
            })
        }else{
            layer.msg("请选择! ");
        }
    })

    $(".exportData_btn").click(function(){
        exportData();
    })




    //数据导出
    function exportData() {
        //得到当前表格里面的checkbox的选中对象集合
        var checkStatus = table.checkStatus('introduceManagerTable'),//选中状态
            data = checkStatus.data;//选中的对象集
        var ids="a=1";
        if(data.length > 0) {
            for (var i in data) {
                ids+="&ids="+data[i].id;
            }
            window.location.href = projectName + "/esalePubUserManager/exportUserData.do?"+ids;

        }else{
            layer.msg("请选择! ");
        }

    }
   /* function exportData() {
        //得到当前表格里面的checkbox的选中对象集合
        var checkStatus = table.checkStatus('productManagerTable'),//选中状态
            data = checkStatus.data;//选中的对象集
        var ids="a=1";
        if(data.length > 0) {
            for (var i in data) {
                ids+="&ids="+data[i].id;
            }
            /!* layer.confirm('确定删除？', {icon: 3, title: '提示信息'}, function (index) {
                 alert("index  :  "+index)*!/
            $.post(projectName + "/esalePubUserManager/exportUserData.do?"+ids,function(data){
                //刷新table
                tableIns.reload();
                //关闭提示框
                //layer.close(index);
            })
            /!*   })*!/
        }else{
            layer.msg("请选择! ");
        }
    }*/

})
