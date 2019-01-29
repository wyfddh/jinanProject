layui.use(['form','layer','table','laydate'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        table = layui.table;

        laydate.render({
            elem: '#dateRange',
            range: '~'
        });

    var pathName=window.document.location.pathname;
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

    //用户列表
    var tableIns = table.render({
        elem: '#introduceManager',
        url : projectName + '/esalePubUserManager/getUserBlacklist.do',
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
            {field:'id', title: '用户ID', width:100,align:'center',templet:"#mumeumImg"},
            {field: 'userName', title: '用户名',  align:'center'},
            {field: 'phone', title: '手机号',  align:'center'},
            {field: 'activityNum', title: '活动报名',  align:'center'},
            {field: 'notJoinStateNum', title: '活动缺席',  align:'center'},
            {field: 'freezTime', title: '冻结时间',  align:'center', templet: '<div>{{ layui.laytpl.toDateString(d.time) }}</div>'},
           /* {title: '推荐',  align:'center',templet: '#recommendBar'},*/
            {title: '操作',width:200,  toolbar:'#introduceManagerBar',align:"left"}
        ]],
        done:function(d){
            $("[data-field = 'mumeumImg']").children(".layui-table-cell").css({"height":"100%","max-width":"100%","position":"relative"});
            $("[data-field = 'mumeumImg']").find("img").css({"max-width":"100%","max-height":"100%","position":"absolute","top":"50%","left":"50%","transform":"translate(-50%, -50%)"});
        }
    });

    $('.searchVal').bind('keydown', function (event) {
        var event = window.event || arguments.callee.caller.arguments[0];
        if (event.keyCode == 13){
            searKeywordCache = $('.search-data-input').val();
            reloadTable();
        }
    });

    $(".search_btn").on("click",function(){
        reloadTable();
    });
    $("#resetBtn").on("click",function(){
        table.reload("introduceManagerTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {

            }
        })
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

    //添加
    function addMuseumInfo(edit){
        title = "查看用户信息"
        url = "../../page/esaleUserManger/esaleUserInfo.html";
        var title;
        if (edit) {
            if(edit.operationStatus == '1'){
                title = "修改信息"
            }else{

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
            content : "../../page/esaleUserManger/esaleUserInfo.html",
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
    }
  /*  $(".addNews_btn").click(function(){
        addMuseumInfo();
    })*/


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

    initForm();

    //列表操作
    table.on('tool(introduceManager)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            data.operationStatus = "1";
            addMuseumInfo(data);
        }else if(layEvent === 'del'){ //解冻
            layer.confirm('是否确定解冻该用户？',{icon:3, title:'提示信息'},function(index){
                $.get(projectName + '/esalePubUserManager/moveOutUser.do',{
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
                            content: "<div class='msg successMsg'><div class='msg-icon'></div><div class='msg-title'>解冻成功！</div><div class='msg-txt'></div></div>"
                        });
                    } else {
                        layer.open({
                            type: 1,
                            title: false, //不显示标题
                            closeBtn: 0,
                            shadeClose: true,
                            skin: "msg",
                            content: "<div class='msg errorMsg'><div class='msg-icon'></div><div class='msg-title'>解冻失败</div><div class='msg-txt'>"+result.data+"</div></div>"
                        });
                    }
                    tableIns.reload();
                    layer.close(index);
                })
            });
        }else if(layEvent === 'detail'){//查看
            localStorage.id = data.id;
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
    });

    //时间戳的处理
    layui.laytpl.toDateString = function(d, format){
        var date = new Date(d || new Date())
            ,ymd = [
            this.digit(date.getFullYear(), 4)
            ,this.digit(date.getMonth() + 1)
            ,this.digit(date.getDate())
        ]
            ,hms = [
            this.digit(date.getHours())
            ,this.digit(date.getMinutes())
            ,this.digit(date.getSeconds())
        ];

        format = format || 'yyyy-MM-dd HH:mm:ss';

        return format.replace(/yyyy/g, ymd[0])
            .replace(/MM/g, ymd[1])
            .replace(/dd/g, ymd[2])
            .replace(/HH/g, hms[0])
            .replace(/mm/g, hms[1])
            .replace(/ss/g, hms[2]);
    };
//数字前置补零
    layui.laytpl.digit = function(num, length, end){
        var str = '';
        num = String(num);
        length = length || 2;
        for(var i = num.length; i < length; i++){
            str += '0';
        }
        return num < Math.pow(10, length) ? str + (num|0) : num;
    };

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
                $.post(projectName + "/esalePubUserManager/moveOutUsers.do?"+ids,function(data){
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
            window.location.href = projectName + "/esalePubUserManager/exportBlacklistData.do?"+ids;

        }else{
            layer.msg("请选择! ");
        }

    }


//上传模板进行解析excel
    layui.use('upload', function () {
        var $ = layui.jquery
            , upload = layui.upload;
        //文件上传   已关闭
        upload.render({
            elem: '#fileForm'
            , url: projectName + 'admin/interceptKeyWord/uploadTemp.do'
            , accept: 'file'
            , auto: false
            // , bindAction: '#upfile' //关闭的上传按钮   html中此id所在元素也被注释
            ,multiple: true
            , done: function (res) {
                layer.msg("成功！ ");
            }
        });
    })

//下载模板
    function downloadMuban() {
           /* $.post(projectName + "admin/interceptKeyWord/downloadTemp.do",function(data){
              /!*  //刷新table
                tableIns.reload();
                //关闭提示框
                //layer.close(index);*!/
            })*/
           window.location.href=projectName + "admin/interceptKeyWord/downloadTemp.do";
    }

})
