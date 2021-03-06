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

    //展览列表
    var tableIns = table.render({
        elem: '#showManager',
        url : projectName + '/esaleShow/getShowList.do',
        request:{
            pageName: 'currentPage',
            limitName: 'size'
        },
        cellMinWidth : 95,
        page : true,
        loading:false,
        limits : [10,15,20,25],
        limit : 10,
        id : "showManagerTable",
        cols : [[
            {type:"numbers",title: '序号', width:70, align:"center"},
            {field: 'createDate', title: '创建时间',  align:'center', templet:function(d){
                if(d.createDate == null){
                    return "-";
                }else{
                    return timestampToDateTime(d.createDate);
                }
            }},
            {field:'pageUrl', title: '封面图片',width:120, style:'height:88px;', align:'center',templet:"#pageUrl"},
            {field: 'showName', title: '展览名称', align:"center"},
            {field: 'museumName', title: '所属博物馆',  align:'center'},
            {field: 'showName', title: '展览地点', align:"center"},
            {field: 'showDate', title: '展览时间',  align:'center'},
            {field: 'type', title: '展览类型', align:"center",templet:function(d){
                    if(d.type == "1"){
                        return "基本陈列";
                    }else if(d.type == "2"){
                        return "专题展览";
                    }else{
                        return "流动展览";
                    }
                }},
            {title: '操作',width:170,  toolbar:'#showManagerBar',align:"left"}
        ]],
        done:function(d){
            $("[data-field = 'pageUrl']").children(".layui-table-cell").css({"height":"100%","max-width":"100%","position":"relative"});
            $("[data-field = 'pageUrl']").find("img").css({"max-width":"100%","max-height":"100%","position":"absolute","top":"50%","left":"50%","transform":"translate(-50%, -50%)"});
        }
    });


    $(".search_btn").on("click",function(){
        reloadTable();
    });
    $("#resetBtn").on("click",function(){
        table.reload("showManagerTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                type:null,
                createDate:null,
                orderBy:null
            }
        })
    })

    function reloadTable() {
        table.reload("showManagerTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                type:$("#type").val(),
                createDate:$("#createDate").val(),
                orderBy:$("#orderBy").val()
            }
        })
    }


    //添加
    function addShowInfo(edit){
        var title;
        if (edit) {
            if(edit.operationStatus == '1'){
                title = "修改展览";
            }else{
                title = "查看展览";
            }
            localStorage["showData"]=JSON.stringify(edit);
        } else {
            title = "添加展览";
            localStorage["showData"]=JSON.stringify("");
        }
        var index = layui.layer.open({
            title : title,
            type : 2,
            area: ['80%', '700px'],
            content : "../../page/esaleShow/esaleShowAdd.html",
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
        addShowInfo();
    })


    function getSelectData() {
        var data = {arr:['show_type_es','order_by']};
        $.ajax({
            type:"post",
            url:projectName + '/esaleMuseum/getSelectData.do',
            data:data,
            success:function(result) {
                if (result.success == 1) {
                    var map = result.data;
                    var show_type_es = map.show_type_es;
                    var typeStr = "";
                    for(var k = 0;k < show_type_es.length;k++) {
                        typeStr +="<option value='"+show_type_es[k].dictCode+"' >"+show_type_es[k].dictName+"</option>"
                    }
                    $("#type").append(typeStr);
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
    table.on('tool(showManager)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            data.operationStatus = "1";
            addShowInfo(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('是否确定删除该展览？',{icon:3, title:'提示信息'},function(index){
                $.get(projectName + '/esaleShow/deleteShow.do',{
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
            addShowInfo(data);
        }
    });

    form.on('select(orderBy)',function(){
        reloadTable();
    });

//时间戳转时间格式
    function timestampToDateTime(obj) {
        var date =  new Date(obj);
        var y = 1900+date.getYear();
        var m = "0"+(date.getMonth()+1);
        var d = "0"+date.getDate();
        var h = date.getHours() + ':';
        var mi = date.getMinutes();
        var s = date.getSeconds();
        if (s<=9){
            s = "0"+date.getSeconds();
        }
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
})
