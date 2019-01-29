var form1;
var currentPage1 = 0;
var layedit1;
var layedit_index;
var main={
    init:function () {
        this.initTable();
        this.tabBind();
    },
    initTable:function(){
        var _this=this;
        currentPage1 ++;
        loadTable();
    },
    tabBind:function () {
        layui.use(['form','layedit'], function () {
            var form = layui.form;
            form1 = form;
            //监听查询
            form.on('submit(formDemo)', function(data){
                currentPage1 = 1;
                loadTable();
                return false;
            });
            //监听查询
            form.on('submit(publish)', function(data){
                layedit1.sync(layedit_index);
                save();
                $("#content").val("");
                layedit_index = layedit1.build('content',{
                    height: 120, //设置编辑器高度
                    tool:[]
                }); //建立编辑器
                return false;
            });
            //监听重置
            $("[type='reset']").click(function () {
                $(this).parents(".searchPan").find("input").val("");
                currentPage1 = 1;
                loadTable();
            });
            //监听重置
            form.on('submit(loadMore)', function(data){
                currentPage1 ++;
                loadTable();
                return false;
            });

            $(".edit").on('click',function () {
                var id = $(this).attr("dataid");
                addMuseumInfo(id, content);
            });

        });

        layui.use('laydate', function(){
            var laydate = layui.laydate;
            laydate.render({
                elem: '#showDate'
                ,range: true //或 range: '~' 来自定义分割字符
            });
        });

        layui.use('layedit', function(){
            layedit1 = layui.layedit;
            layedit_index = layedit1.build('content',{
                height: 120, //设置编辑器高度
                tool:[]
            }); //建立编辑器
        });
    }
}
main.init();

/**
 * 加载表格数据
 */
function loadTable() {
    var pageSize = 10;
    $.ajax({
        type:"post",
        async:false,
        data:{size: pageSize, currentPage: currentPage1, content:$("#search_content").val(), showDate:$("#showDate").val()},
        url:property.getProjectPath()+"personDiary/getPersonDiaryList.do",
        success:function(result) {
            if (result.success == 1) {
                var data = result.data;
                if(currentPage1 == 1){
                    $("#timeline1").html("");
                }else{
                    $("#timeline-end").remove();
                }
                if(data.length == 0){
                    var end = "";
                    end += '<li class="layui-timeline-item">'+
                        '<i class="layui-icon layui-timeline-axis">&#xe63f;</i>'+
                        '<div class="layui-timeline-content layui-text">'+
                        '<div class="layui-timeline-title">'+
                        '<a class="myBtn2">暂无任何日报...</a>'+
                        '</div>'+
                        '</div>'+
                        '</li>';
                    $("#timeline1").append(end);
                    return false;
                }

                for(var i=0; i<data.length; i++){
                    var currentMap = data[i];
                    var html = '<li class="layui-timeline-item">'+
                        '<i class="layui-icon layui-timeline-axis">&#xe63f;</i>'+
                        '<div class="layui-timeline-content layui-text">'+
                        '<h3 class="layui-timeline-title">'+timestampToTime(currentMap.creattime)+'&nbsp;&nbsp;<a class="layui-btn layui-btn-xs cy-page edit" dataid="'+currentMap.id+'">编辑</a></h3>'+
                        '<p>'+currentMap.content+'</p>'+
                        '</div>'+
                        '</li>';
                    $("#timeline1").append(html);
                }
                var shengyu = result.page.allRow - pageSize*currentPage1;
                if(data.length == pageSize && shengyu > 0){
                    var end = "";
                    end += '<li class="layui-timeline-item" id="timeline-end">'+
                        '<i class="layui-icon layui-timeline-axis">&#xe63f;</i>'+
                        '<div class="layui-timeline-content layui-text">'+
                        '<div class="layui-timeline-title">'+
                        '<a class="myBtn2" lay-submit lay-filter="loadMore">加载更多...</a>'+
                        '</div>'+
                        '</div>'+
                        '</li>';
                    $("#timeline1").append(end);
                }
                if(currentPage1 > 1){
                    main.tabBind();
                }
            } else {
                top.layer.msg(result.data);
            }
        },
        error:function(result) {
            top.layer.msg("系统异常");
        }
    });
}

function save(){
    var value = layedit1.getText(layedit_index);
    if(value == ''){
        top.layer.msg("日志内容不能为空");
        return false;
    }
    $.ajax({
        type:"post",
        async:false,
        data:{content: $("#content").val()},
        url:property.getProjectPath()+"personDiary/addOrUpdate.do",
        success:function(result) {
            if (result.success == 1) {
                top.layer.msg(result.data);
                location.reload();
            } else {
                top.layer.msg(result.data);
            }
        },
        error:function(result) {
            top.layer.msg("系统异常");
        }
    });
}

function timestampToTime(timestamp) {
    var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '年';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '月';
    var D = date.getDate() + '日';
    var h = date.getHours() + ':';
    var m = date.getMinutes() + ':';
    var s = date.getSeconds()<10?'0'+date.getSeconds():date.getSeconds();
    return Y+M+D+h+m+s;
}

function addMuseumInfo(id, content){
    localStorage.diaryId = id;
    localStorage.diaryContent = content;
    //0:添加 1：查看 2：修改
    var title;
    var url;
    title = "修改日报";
    url = "../../page/esalePersonDiary/diaryEdit.html";
    var index = layui.layer.open({
        title : title,
        type : 2,
        area: ['80%', '500px'],
        content : url,
        end :function() {
            location.reload();
        }
    })
    layui.layer.full(index);
    window.sessionStorage.setItem("index",index);
    //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
    $(window).on("resize",function(){
        layui.layer.full(window.sessionStorage.getItem("index"));
    })
}

function checkMyOrg() {
    checkOrg(userInfo.userId);
}




