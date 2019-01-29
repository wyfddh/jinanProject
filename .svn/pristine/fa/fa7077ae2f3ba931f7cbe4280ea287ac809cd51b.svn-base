var videoSaveTypeList = null;
var videoSourceList = null;
var videoTypeList = null;
var authSettingList = null;
var tableId = '';
var attachmentsList = null;
var commentsList = new Array();
var videoMarkList = null;
var flag = "0";
var form1;
var userId;
var attCount = 0;
//页面属性
var pageType = "add";
var projectName = property.getProjectPath();
var collectArray = [];
var choseCollect = [];
var form = layui.form;

layui.use(['form','layer','laydate','table'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        table = layui.table;


    var pathName=window.document.location.pathname;
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

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

    parent.$(".layui-layer-title").text("查看用户");
    $("#activityStatus").text(activityStatus);

    $(function(){
        var id = localStorage.id;
        loadData(id);
    });



    var tableSign = table.render({
        elem: '#activity',
        url : projectName + '/esalePubUserManager/getActivityRecordByUserId.do',
        request:{
            pageName: 'currentPage',
            limitName: 'size'
        },
        where: {
            uid: localStorage.id
        },
        cellMinWidth : 70,
        /*        page : true,*/
        /*        limits : [10,15,20,25],
                limit : 10,*/
        id : "activity",
        cols : [[
            {field: 'id', title: '报名编号',width: '15%',  align:"center"},
            {field: 'enrollTimeStr', title: '报名时间',width: '15%',  align:'center'},
            {field: 'userName', title: '姓名',width: '15%',  align:'center'},
            {field: 'activityName', title: '活动名称',width: '15%',  align:'center'},
            {field: 'activityTime', title: '活动时间',  width: '15%', align:'center', templet:function(d){
                    if(d.activityTime == null){
                        return "-";
                    }else{
                        return timestampToDate(d.activityTime);
                    }
                }},
            {field: 'activityAddr', title: '活动地点',width: '15%',  align:'center'},
            {field: 'enrollType', title: '报名方式',width: '10%',  align:"center",templet:function(d){
                    if(d.enrollType == '0'){
                        return "PC端";
                    }else{
                        return "移动端";
                    }
            }},
            {field: 'dataState', title: '报名状态',width: '15%',  align:'center'},
            {field: 'userJoinStatus', title: '参加状态',width: '15%',  align:'center'}
            /*{field: col_filed, title: col_title, align:'center',width: '10%', templet:function(d) {
                alert(col_filed)
                    alert(d.dataState)
                    alert(d.joinState)
                    if(col_filed == 'dataState'){
                        if (d.dataState == '0') {
                            return "取消报名";
                        } else {
                            return "已报名";
                        }
                    }else if(col_filed == 'joinState'){
                        if (d.joinState == '0') {
                            return "未参加";
                        } else if (d.joinState == '1'){
                            return "已参加";
                        } else{
                            return "-";
                        }
                    }
            }}*/
        ]],
        done:function(d){
        }
    });

    //统计信息
    var tableSign = table.render({
        elem: '#activityCount',
        url : projectName + '/esalePubUserManager/countInfomation.do?uid='+localStorage.id,
       /* cellMinWidth : 70,
              page : true,
               limits : [10,15,20,25],
                limit : 10,*/
        id : "activityCount",
        cols : [[
            {field: 'collectionNum', title: '收藏藏品',width: '15%',  align:"center"},
            {field: 'activityNum', title: '报名次数',width: '15%',  align:'center'},
            {field: 'concenNum', title: '取消次数',width: '15%',  align:'center'},
            {field: 'notJoinStateNum', title: '缺席次数',width: '15%',  align:'center'},
            {field: 'activityComment', title: '活动评论',width: '15%',  align:'center'},
           /* {field: '', title: '收藏藏品',width: '15%',  align:'center'}*/
        ]],
        done:function(d){
        }
    });



    function loadData(id) {
        layui.use('form', function(){
            var form = layui.form;
            var index = parent.layer.getFrameIndex(window.name);
            var json = {"id":id};
            //加载数据
            $.ajax({
                type:"get",
                data:json,
                async:false,
                url:property.getProjectPath()+"/esalePubUserManager/getUserInfomation.do",
                success:function(result) {
                    /*  if (result.success == 1) {*/
                    setFormData(result.data);
                    form.render('select');
                },
                error:function(result) {
                    top.layer.msg("系统异常");
                }
            });
        });
    }

    function setFormData(data) {
        property.setForm($("#videoForm"),data);
        $("#realName").val(data.realName);
       $("#birthday").val(formatDate(data.birthday));
        $("#id").val(data.id);
        $("#userName").val(data.userName);
        $("#sex").val(data.sex);
        $("#phone").val(data.phone);
        if (isEmpty(data.avatarUrl)) {
          $("#avatarUrl").attr("src","../../statics/images/icon/head-pic.svg");
        } else {
          $("#avatarUrl").attr("src",data.avatarUrl);
        }

        var isyong = data.isYoung;
        if (isyong==1){
            $("#isyong").text("青少年用户");
        }else {
            $("#isyong").text("");
        }


        $("#email").val(data.email);
        $("#description").val(data.description);
        $("#realName").val(data.realName);
        $("#idCard").val(data.idCard);
        $("#schoolName").val(data.schoolName);
        $("#parentName").val(data.parentName);
        tableId = data.attachment;

    }



    //

    /*{field: 'enrollTime', title: '报名时间',  width: '15%', align:'center', templet:function(d){
            if(d.enrollTime == null){
                return "-";
            }else{
                return timestampToDate(d.enrollTime);
            }
    }},*/

    //评论列表
  /*  var tableAssess = table.render({
        elem: '#esaleAssess',
        url : projectName + '/esaleActivity/getAssessListById.do',
        request:{
            pageName: 'currentPage',
            limitName: 'size'
        },
        where: {
            uid: dataObject.id
        },
        cellMinWidth : 95,
        page : true,
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
*/


  /*  $("#addrepo").on("click",function(){
        return false;
    });
    $("#printrepo").on("click",function(){
        return false;
    });
*/

    function reloadTable(tablename) {
        table.reload(tablename,{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                id:localStorage.id
            }
        })
    }

//时间戳转时间格式
   /* function timestampToDateTime(obj) {
        var date =  new Date(obj);
        var y = 1900+date.getYear();
        var m = "0"+(date.getMonth()+1);
        var d = "0"+date.getDate();
        var h = date.getHours() + ':';
        var mi = date.getMinutes();
        var s = date.getSeconds();
        return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length)+" "+h+mi + ':'+s;
    };*/

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

    function formatDate(value, fmt) {
        if (value == null || value == "") {
            return null;
        }
        var date = new Date(value);

        if(!fmt) {
            fmt = 'yyyy-MM-dd';
        }

        var o = {
            "M+": date.getMonth() + 1, //月份
            "d+": date.getDate(), //日
            "H+": date.getHours(), //小时
            "m+": date.getMinutes(), //分
            "s+": date.getSeconds(), //秒
            "q+": Math.floor((date.getMonth() + 3) / 3), //季度
            "S": date.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
});

function isEmpty(obj) {
    if (typeof obj == "undefined" || obj == null || obj == "") {
        return true;
    } else {
        return false;
    }
}
