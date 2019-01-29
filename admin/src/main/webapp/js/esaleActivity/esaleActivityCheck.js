layui.use(['form','layer','laydate','table'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        table = layui.table;


    var pathName=window.document.location.pathname;
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

    // alert(localStorage["activityData"]);
    dataObject = JSON.parse(localStorage["activityData"]);
    var activityStatus;
    var col_filed;
    var col_title;
    var col_sign_bar;
    if(new Date(dataObject.activityTime) > new Date()){
        activityStatus = "正在报名";
        col_filed = 'dataState';
        col_title = "报名状态";
        col_sign_bar = 1;
    }else{
        activityStatus = "已结束";
        col_filed ='joinState';
        col_title = "参加状态";
        col_sign_bar = 2;
    }

    var url = projectName + '/esaleActivity/exportData.do?key='+dataObject.id+'&activityStates='+col_sign_bar;
    $("#printrepo").attr("href",url);

    parent.$(".layui-layer-title").text("查看活动-"+activityStatus);
    $("#activityStatus").text(activityStatus);

    $(function(){
        setData();
        // initForm();
    });

    form.on('switch(sexDemo)', function(obj){
        var flag = obj.elem.checked;
        var status = 0;
        // if (flag){
        //     status = 1;
        // }else {
        //     status = 0;
        // }
        var id = $(obj.elem).attr("data-id");
        if(flag){ //参加
            tips = '是否确定参加活动？';
            keyword = "参加";
            operate=2;
        }else{//缺席
            tips = '是否确定缺席活动？';
            keyword = "缺席";
            operate=3;
        }
        layer.confirm(tips,{icon:3, title:'提示信息'},function(index){
            $.get(projectName + '/esaleActivity/operationUserActivity.do',{
                id : id,
                operate:operate
            },function(result){
                if (result.success == 1) {
                    layer.open({
                        type: 1,
                        title: false, //不显示标题
                        closeBtn: 0,
                        time:1000,
                        shadeClose: true,
                        skin: "msg",
                        content: "<div class='msg successMsg'><div class='msg-icon'></div><div class='msg-title'>"+keyword+"成功！</div><div class='msg-txt'></div></div>",
                        success:function(){
                            if(operate == 1){
                                // alert(Number($(".signCounttd").eq(0).text())-1);
                                $(".signCounttd").text(Number($(".signCounttd").eq(0).text())-1);
                                $(".cancelSigntd").text(Number($(".cancelSigntd").eq(0).text())+1);
                            }else if(operate == 2){
                                $(".realJointd").text(Number($(".realJointd").eq(0).text())+1);
                            }else{
                                var num = Number($(".realJointd").eq(0).text());
                                if(num!="0"){
                                    $(".realJointd").text(num-1);
                                }else {
                                    $(".realJointd").text(0);
                                }

                            }
                        }
                    });
                } else {
                    layer.open({
                        type: 1,
                        title: false, //不显示标题
                        closeBtn: 0,
                        shadeClose: true,
                        skin: "msg",
                        content: "<div class='msg errorMsg'><div class='msg-icon'></div><div class='msg-title'>"+keyword+"失败</div><div class='msg-txt'>"+result.data+"</div></div>"
                    });
                }
                tableSign.reload();
                layer.close(index);
            });
        });
    });



    function setData() {
        if (!isEmpty(dataObject)) {
            var esaleActivitytable = '<tr><td rowspan="4"><div><img src=\'' + dataObject.pictureUrl + '\' alt="" ></div></td><td width="100">名称</td><td style="text-align: left;">'+dataObject.activityName+'</td><td width="150">状态</td><td width="150">'+activityStatus+'</td></tr>' +
                '<tr><td>时间</td><td style="text-align: left;">'+dataObject.activityTime.substring(0,10)+'</td><td>名额</td><td>'+dataObject.quota+'</td></tr>' +
                '<tr><td>地点</td><td style="text-align: left;">'+dataObject.activityAddr+'</td><td>报名</td><td class="signCounttd">'+dataObject.signCount+'</td></tr>' +
                '<tr><td>要求</td><td style="text-align: left;">'+dataObject.demand+'</td><td>取消报名</td><td class="cancelSigntd">'+dataObject.cancelSign+'</td></tr>';
            $("#esaleActivity").append(esaleActivitytable);
            var esaleCounttable;
            if(col_sign_bar == 1){
                esaleCounttable = '<tr><td></td><td>累计报名</td><td>已报名</td><td>已取消</td><td>活动评论</td></tr>' +
                    '<tr><td>人次</td><td class="sumCounttd">'+(dataObject.signCount+dataObject.cancelSign)+'</td><td class="signCounttd">'+dataObject.signCount+'</td><td class="cancelSigntd">'+dataObject.cancelSign+'</td><td class="assessCounttd">'+dataObject.assessCount+'</td></tr>';
            }else{
                esaleCounttable = '<tr><td ></td><td>累计报名</td><td>已报名</td><td>已取消</td><td>实际参加</td><td>活动评论</td></tr>' +
                    '<tr><td>人次</td><td class="sumCounttd">'+(dataObject.signCount+dataObject.cancelSign)+'</td><td class="signCounttd">'+dataObject.signCount+'</td><td class="cancelSigntd">'+dataObject.cancelSign+'</td><td class="realJointd">'+dataObject.realJoin+'</td><td class="assessCounttd">'+dataObject.assessCount+'</td></tr>';
            }
            $("#activityCount").append(esaleCounttable);
        }
    }
    //报名列表
    var tableSign = table.render({
        elem: '#esaleSign',
        url : projectName + '/esaleActivity/getSignListById.do',
        request:{
            pageName: 'currentPage',
            limitName: 'size'
        },
        where: {
            key: dataObject.id,
            activityStates:col_sign_bar
        },
        cellMinWidth : 70,
        page : true,
        loading:false,
        limits : [10,15,20,25],
        limit : 10,
        id : "esaleSign",
        cols : [[
            {type:"numbers",title: '序号', width: '10%', align:"center"},
            {field: 'enrollTime', title: '报名时间',  width: '15%', align:'center', templet:function(d){
                if(d.enrollTime == null){
                        return "-";
                    }else{
                        return timestampToDate(d.enrollTime);
                    }
                }},
            {field: 'realName', title: '姓名',width: '15%',  align:"center"},
            {field: 'userPhone', title: '手机',width: '15%',  align:'center'},
            /*{field: 'enrollType', title: '报名方式',width: '10%',  align:"center",templet:function(d){
                    if(d.enrollType == '0'){
                        return "PC端";
                    }else if(d.enrollType == '1'){
                        return "移动端";
                    }else {
                        return "";
                    }
                }},*/
            {field: 'enrollType', title: '报名方式',width: '10%',  align:'center'},
            {field: 'dataState', title: '报名状态',width: '10%',  align:'center'},
           /* {field: col_filed, title: col_title, align:'center',width: '10%', templet:function(d) {
                    if (d.dataState == '已取消') {
                        return "已取消";
                    } else {
                        return "已报名";
                    }
            }},*/
            {field: 'absentCount', title: '累计缺席次数',width: '15%',align:"center"},
            // {title: '操作',  toolbar:'#esaleSignBar', width: '10%', align:"center"}
            {fixed: 'right', title:'操作', toolbar: '#switchTpl', width:200}

        ]],
        done:function(d){
            form.render();
        }
    });

    //列表操作
    table.on('tool(esaleSign)', function(obj){
        var tips;
        var keyword;
        var operate;
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'cancel'){ //取消
            tips = '是否确定取消报名活动？';
            keyword = "取消";
            operate=1;
        }else if(layEvent === 'join'){ //参加
            tips = '是否确定参加活动？';
            keyword = "参加";
            operate=2;
        }else if(layEvent === 'absent'){//缺席
            tips = '是否确定缺席活动？';
            keyword = "缺席";
            operate=3;
        }
        layer.confirm(tips,{icon:3, title:'提示信息'},function(index){
            $.get(projectName + '/esaleActivity/operationUserActivity.do',{
                id : data.id,
                operate:operate
            },function(result){
                if (result.success == 1) {
                    layer.open({
                        type: 1,
                        title: false, //不显示标题
                        closeBtn: 0,
                        time:1000,
                        shadeClose: true,
                        skin: "msg",
                        content: "<div class='msg successMsg'><div class='msg-icon'></div><div class='msg-title'>"+keyword+"成功！</div><div class='msg-txt'></div></div>",
                        success:function(){
                            if(operate == 1){
                                // alert(Number($(".signCounttd").eq(0).text())-1);
                                $(".signCounttd").text(Number($(".signCounttd").eq(0).text())-1);
                                $(".cancelSigntd").text(Number($(".cancelSigntd").eq(0).text())+1);
                                $(".sumCounttd").text(Number($(".sumCounttd").eq(0).text())-1);

                            }else if(operate == 2){
                                $(".realJointd").text(Number($(".realJointd").eq(0).text())+1);
                            }else{
                                var num = Number($(".realJointd").eq(0).text());
                                if(num!="0"){
                                    $(".realJointd").text(num-1);
                                }else {
                                    $(".realJointd").text(0);
                                }

                            }
                        }
                    });
                } else {
                    layer.open({
                        type: 1,
                        title: false, //不显示标题
                        closeBtn: 0,
                        shadeClose: true,
                        skin: "msg",
                        content: "<div class='msg errorMsg'><div class='msg-icon'></div><div class='msg-title'>"+keyword+"失败</div><div class='msg-txt'>"+result.data+"</div></div>"
                    });
                }
                tableSign.reload();
                layer.close(index);

            });
        });
    });

    //评论列表
    var tableAssess = table.render({
        elem: '#esaleAssess',
        url : projectName + '/esaleActivity/getAssessListById.do',
        request:{
            pageName: 'currentPage',
            limitName: 'size'
        },
        where: {
            key: dataObject.id
        },
        cellMinWidth : 95,
        page : true,
        loading:false,
        limits : [10,15,20,25],
        limit : 10,
        id : "esaleAssess",
        skin :'nob',
        cols : [[
            {field:'userPicUrl', hide:true, style:'height:auto;overflow:visible;text-overflow:inherit;white-space:normal;',templet:"#esaleAssessBar"}
        ]],
        done:function(d){
            $("#esaleAssess").next().find("thead").hide();
            $("#esaleAssess").next().find("tr").css("height","100px");
            $("#esaleAssess").next().find("td").css("height","100px");
            $("#esaleAssess").next().find(".layui-table-cell").css("overflow","inherit");
            // $("[data-field = 'userPicUrl']").children(".layui-table-cell").css({"height":"100%","max-width":"100%","position":"relative"});
            // $("[data-field = 'userPicUrl']").find("img").css({"max-width":"100%","max-height":"100%","position":"absolute","top":"50%","left":"50%","transform":"translate(-50%, -50%)"});
        }
    });

    //评论操作
    table.on('tool(esaleAssess)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

       if(layEvent === 'remove'){ //删除
            layer.confirm('是否确定删除该评论？',{icon:3, title:'提示信息'},function(index){
                $.get(projectName + '/esaleActivity/deleteAssessActivity.do',{
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
                            content: "<div class='msg successMsg'><div class='msg-icon'></div><div class='msg-title'>删除成功！</div><div class='msg-txt'></div></div>",
                            success:function(){
                                $(".assessCounttd").text(Number($(".assessCounttd").eq(0).text())-1);
                        }
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
                    tableAssess.reload();
                    layer.close(index);
                })
            });
        }
    });


    $("#addrepo").click(function(){

        var index = layui.layer.open({
            title : '添加报名',
            type : 2,
            offset: '80px',
            area: ['20%', '280px'],
            content : "../../page/esaleActivity/activityRegist.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
            },
            end :function() {
               var coun =  Number($(".signCounttd").eq(0).text());
                coun+=1;
                tableSign.reload();
                $(".signCounttd").text(coun);

                $(".sumCounttd").text(Number($(".sumCounttd").eq(0).text())+1);
            }
        });
    });

    //打印报名表
    // $("#printrepo").click(function () {
    //     var url = projectName + '/esaleActivity/exportData.do?key='+dataObject.id+'&activityStates='+col_sign_bar;
    //     $("#printrepo").attr("href",url);
    // });


    function reloadTable(tablename) {
        table.reload(tablename,{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                id:dataObject.id
            }
        })
    }

//时间戳转时间格式
    function timestampToDateTime(obj) {
        var date =  new Date(obj);
        var y = 1900+date.getYear();
        var m = "0"+(date.getMonth()+1);
        var d = "0"+date.getDate();
        var h = date.getHours() + ':';
        var mi = date.getMinutes();
        var s = date.getSeconds();
        return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length)+" "+h+mi + ':'+s;
    };

    function timestampToDate(obj) {
        var date =  new Date(obj);
        var y = 1900+date.getYear();
        var m = "0"+(date.getMonth()+1);
        var d = "0"+date.getDate();
        var h = date.getHours() + ':';
        var mi = date.getMinutes();
        var s = date.getSeconds();
        return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
    };
});

function isEmpty(obj) {
    if (typeof obj == "undefined" || obj == null || obj == "") {
        return true;
    } else {
        return false;
    }
}
