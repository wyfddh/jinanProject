layui.use(['form','layer','laydate','table'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        table = layui.table;

    laydate.render({
        elem: '#createDate'
    });

    var pathName=window.document.location.pathname;
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);

    //活动列表
    var tableIns = table.render({
        elem: '#esaleActivity',
        url : projectName + '/esaleActivity/getActivityList.do',
        request:{
            pageName: 'currentPage',
            limitName: 'size'
        },
        cellMinWidth : 95,
        page : true,
        loading:false,
        limits : [10,15,20,25],
        limit : 10,
        id : "esaleActivityTable",
        cols : [[
            {type:"numbers",title: '序号', width:70, align:"center"},
            // {field: 'id',title: '编号', align:"center"},
            {field: 'activityName', title: '活动名称',  align:'center'},
            {field: 'type', title: '活动类型', width:140,align:"center",templet:function(d){
                    if(d.type == "1"){
                        return "游学活动";
                    }else if(d.type == "2"){
                        return "讲解员培训";
                    }else if(d.type == "3"){
                        return "讲座";
                    }else if(d.type == "4"){
                        return "非遗";
                    }else if(d.type == "5"){
                        return "手工";
                    }else{
                        return "其他";
                    }
                }},
            {title: '活动状态', align:"center",width:120,templet:function(d){
                    d.activityTime = d.activityTime.replace(/-/g,"/");
                    d.activityTime = d.activityTime.split(" ")[0];

                if(new Date(d.activityTime)-new Date()>0){
                        return "报名中";
                    }else{
                        return "已结束";
                    }
                }},
            {field: 'activityAddr', title: '活动地点',  align:'center'},
            {field: 'activityTime', title: '活动时间', width:120,align:"center", templet:function(d){
                if(d.activityTime == null){
                    return "-";
                }else{
                    return timestampToDate(d.activityTime);
                }
            }},
            {field: 'quota', title: '名额', width:70, align:'center'},
            {field: 'signCount', title: '已报名', width:100, align:"center"},
            {field: 'assessCount', title: '评论', width:70,align:"center"},
            {title: '操作',width:200,  toolbar:'#esaleActivityBar',align:"center"}
        ]],
        done:function(d){
        }
    });


    $(".search_btn").on("click",function(){
        reloadTable();
    });
    $("#resetBtn").on("click",function(){
        table.reload("esaleActivityTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                key:"",
                type:"",
                status:"",
                orderBy:""
            }
        })
    });

    function reloadTable() {
        table.reload("esaleActivityTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                key:$("#key").val(),
                type:$("#type").val(),
                status:$("#status").val(),
                orderBy:$("#orderBy").val()
            }
        })
    }


    //添加
    function addActivityInfo(edit){
        var title;
        var content;
        if (edit) {
            if(edit.operationStatus == '1'){
                title = "编辑活动";
                content = "../../page/esaleActivity/esaleActivityAdd.html";
            }else if(edit.operationStatus == '2'){
                title = "查看活动";
                content = "../../page/esaleActivity/esaleActivityCheck.html";
            }else if(edit.operationStatus == '3'){
                title = "活动资料管理";
                content = "../../page/esaleActivity/esaleActivityFile.html";
            }
            // else if(edit.operationStatus == '4'){
            //     title = "分享";
            // }
            localStorage["activityData"]=JSON.stringify(edit);
        } else {
            title = "创建活动";
            content = "../../page/esaleActivity/esaleActivityAdd.html";
            localStorage["activityData"]=JSON.stringify("");
        }
        var index = layui.layer.open({
            title : title,
            type : 2,
            area: '80%',
            content : content,
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
            },
            end :function() {
                reloadTable();
            }
        });
        layui.layer.full(index);
        // window.sessionStorage.setItem("index",index);
        // //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        // $(window).on("resize",function(){
        //     layui.layer.full(window.sessionStorage.getItem("index"));
        // });
    }

    $(".addNews_btn").click(function(){
        addActivityInfo();
    })


    function getSelectData() {
        var data = {arr:['activity_type_es','activity_status_es','order_by']};
        $.ajax({
            type:"post",
            url:projectName + '/esaleMuseum/getSelectData.do',
            data:data,
            success:function(result) {
                if (result.success == 1) {
                    var map = result.data;
                    var activity_type_es = map.activity_type_es;
                    var typeStr = "";
                    for(var k = 0;k < activity_type_es.length;k++) {
                        typeStr +="<option value='"+activity_type_es[k].dictCode+"' >"+activity_type_es[k].dictName+"</option>"
                    }
                    $("#type").append(typeStr);
                    var activity_status_es = map.activity_status_es;
                    var statusStr = "";
                    for(var k = 0;k < activity_status_es.length;k++) {
                        statusStr +="<option value='"+activity_status_es[k].dictCode+"' >"+activity_status_es[k].dictName+"</option>"
                    }
                    $("#status").append(statusStr);
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
    table.on('tool(esaleActivity)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            data.operationStatus = "1";
            addActivityInfo(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('是否确定删除该活动？',{icon:3, title:'提示信息'},function(index){
                $.get(projectName + '/esaleActivity/deleteActivity.do',{
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
            addActivityInfo(data);
        }else if(layEvent === 'data'){//资料
            data.operationStatus = "3";
            addActivityInfo(data);
        }else if(layEvent === 'share'){//分享
            // data.operationStatus = "4";
            var pcaddress = localhostPaht+'/web/#/activity/detail?id='+ data.id;
            var modaddress = localhostPaht+'/mobile/#/?activityId='+ data.id;
            layer.open({
                type: 1,
                title:'分享',
                resize:false,
                // skin: 'layui-layer-rim', //加上边框
                area: ['420px', '240px'], //宽高
                content: '<div style="margin-top: 20px;margin-left: 10px">'
                + '<span>PC端访问地址&nbsp;&nbsp;:</span>'
                + '<input type="text"  id="pcAddress" value='+ pcaddress +' class="layui-input layui-input-inline" style="width: 200px;margin-left: 10px" />'
                + '<button type="button" class="layui-btn layui-btn-primary btn" id="copy1">复制链接</button>'
                + '</div>'
                + '<div style="margin-top: 20px;margin-left: 10px">'
                + '<span>移动端访问地址:</span>'
                + '<input type="text"  id="mobAddress" value='+ modaddress +' class="layui-input layui-input-inline" style="width: 200px;margin-left: 10px" />'
                + '<button type="button" class="layui-btn layui-btn-primary btn" id="copy2">复制链接</button>'
                + '</div>',
                success: function(layero, index){
                  $(layero).on('click', '#copy1', function() {
                    copyPath1();
                  });
                  $(layero).on('click', '#copy2', function() {
                    copyPath2();
                  });
                }
            });
            function copyPath1() {
                $("#copyPath1").click();
            }
            $("#copyPath1").click(function() {
              var clipboard = new Clipboard('#copyPath1',{
                text: function() {
                  return pcaddress;
                }
              });
              clipboard.on('success', function(e){
                top.layer.msg("复制成功！");
              });
            })

          function copyPath2() {
            $("#copyPath2").click();
          }
          $("#copyPath2").click(function() {
            var clipboard = new Clipboard('#copyPath2',{
              text: function() {
                return modaddress;
              }
            });
            clipboard.on('success', function(e){
              top.layer.msg("复制成功！");
            });
          })




            return false;
        }
    });


    form.on('select(orderBy)',function(){
        reloadTable();
    });

//时间戳转时间格式
    function timestampToDateTime(obj) {
        obj = obj.replace(/-/g,"/");
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
        obj = obj.replace(/-/g,"/");
        obj = obj.split(" ")[0];
        var date =  new Date(obj);
        var y = 1900+date.getYear();
        var m = "0"+(date.getMonth()+1);
        var d = "0"+date.getDate();
        var h = date.getHours() + ':';
        var mi = date.getMinutes();
        var s = date.getSeconds();
        return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
    };
})


function getNowDate(str) { //解决new Date()IE10不支持参数
        //首先将日期分隔 ，获取到日期部分 和 时间部分
        var day = str.split(' ');
        //获取日期部分的年月日
        var days = day[0].split('-');
        //获取时间部分的 时分秒
        var mi = day[day.length - 1].split(':');
        //获取当前date类型日期
        var date = new Date();
        //给date赋值  年月日
        date.setUTCFullYear(days[0], days[1] - 1, days[2]);
        //给date赋值 时分秒  首先转换utc时区 ：+8
        date.setUTCHours(mi[0] - 8, mi[1], mi[2]);
        return date;
}

function timeSort(time1,time2) {
    return getNowDate(time1).getTime() - getNowDate(time2).getTime();
}
