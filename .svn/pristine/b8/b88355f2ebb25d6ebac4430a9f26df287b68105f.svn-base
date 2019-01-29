var pathName = window.document.location.pathname;
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
//待办
var undoTask;
//已办
var doneTask;
//已完结
var finishTask;
// var dataObject = JSON.parse(localStorage["userInfo"]);
var org = "";

var main = {
    option: {
        useEasing: true,
        useGrouping: true,
        separator: ',',
        decimal: '.',
        prefix: '',
        suffix: ''
    },
    init: function () {
        this.countUp();
        this.tabBind();
    },
    countUp: function () {

    },
    tabBind: function () {
        $(".layui-tab-pic li").click(function () {
            var index = $(this).index();
            $(".layui-tab-pic li").eq(index).addClass("active").siblings().removeClass('active');
            $(".layui-tab-item").eq(index).addClass("layui-show").siblings().removeClass('layui-show');
        });
    }
};

main.init();
var currentPage = 1;

layui.use(['form', 'layer', 'laydate', 'table'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        table = layui.table;



    $.ajax({
        type: "post",
        async:false,
        url: projectName + '/getLoginUserInfo.do',
        success: function (result) {
            var user = result.data;
            if (user == null) {
                window.location.href = "../../sso.jsp";
            }
            // alert(JSON.stringify(user));
            $("#userName").text(user.name);
            $(".username").text(user.name);
            $("#departInfo").text(user.orgName + '' + user.duty);
            // localStorage["userInfo"] = user;
            window.localStorage.setItem("userInfo", user);
        }
    });

    loadTask();
    showTaskList();
    var addLogUrl = "";
    var digitalAssetsUrl = "";

    $.ajax({
        type: "post",
        url: projectName + '/esaleWorkBenchData/getUrl.do',
        success: function (result) {
            var map = result.data;
            if (map != null) {
                var collectionUrl = map.collectionUrl;
                var syncJurisdictionUrl =  map.syncJurisdictionUrl;
                digitalAssetsUrl = map.digitalAssetsUrl;
                var publicServerUrl =  map.publicServerUrl;
                addLogUrl = map.addLogUrl;
                $("#collectionUrl").attr("href",collectionUrl);
                $("#syncJurisdictionUrl").attr("href",syncJurisdictionUrl);
                $("#digitalAssetsUrl").attr("href",digitalAssetsUrl);
                $("#publicServerUrl").attr("href",publicServerUrl);
            }
        }
    });

      getOrgList();
      function getOrgList() {
        var orgList = property.getAllOrgList();
        var html = '';

        for (var i = 0;i<orgList.length;i++){
          var element = '<option value='+orgList[i]["departmentId"]+'>'+orgList[i]["departmentName"]+'</option>';
          html = html + element;
        }
        $("#applyOrg").append(html);
        form.render();
      }

      form.on('select(applyOrg)',function(data){
        org = data.value;
        currentPage = 1;
        addNews(org);
      });


    var addNews = function (org) {
        $.ajax({
            type: "post",
            data: {
                departId: org,
                currentPage: currentPage
            },
            url: projectName + '/esaleNews/getNewsList.do',
            success: function (result) {
                if (result.success == 1) {
                    $("#tongzhiList").empty();
                    var news = result.data;
                    var newsStr = "";
                    if (news == null || news.length == 0) {
                        newsStr = "无数据";
                    } else {
                        for (var i = 0; i < news.length; i++) {
                            newsStr += '<li><div class="v1"><img src="../../statics/images/main/card_pic5.png" alt=""><span><p>' + news[i].userName + '|' + news[i].userDepartName + '</p><p>' + timestampToDateTime(news[i].createDate) + '</p></span>';
                            if (news[i].isGood == "1") {
                                newsStr += '<i class="fa fa-thumbs-up" id="' + news[i].id + '"></i>';
                            } else {
                                newsStr += '<i class="fa fa-thumbs-o-up" id="' + news[i].id + '"></i>';
                            }
                            newsStr += '</div><div class="v2">' + news[i].content + '</div><div class="v3">';
                            var picList = news[i].picList;
                            for (var j = 0; j < picList.length; j++) {
                                newsStr += '<img src="' + picList[j].attPath + '" alt="" >';
                            }
                            newsStr += '</div></li>';
                            var nameList = news[i].praiseNameList;
                            if (nameList != null && nameList.length != 0) {
                                newsStr += '<i class="fa fa-thumbs-up" style="margin-left: 5%;" id="' + news[i].id + '"></i>&nbsp;&nbsp;';
                                for (var k = 0; k < nameList.length; k++) {
                                    newsStr += nameList[k];
                                    if (k != nameList.length - 1) {
                                        newsStr += ',';
                                    }
                                }
                                newsStr += '&nbsp;&nbsp;觉得很赞';
                            }
                        }
                    }
                    $("#tongzhiList").append(newsStr);
                    form .render();
                    if (currentPage == result.page.totalPage || news == null || news.length == 0) {
                        $("#loadMany").hide();
                    } else {
                      $("#loadMany").show();
                        // currentPage++;
                    }
                } else {
                    layer.open({
                        type: 1,
                        title: false, //不显示标题
                        closeBtn: 0,
                        shadeClose: true,
                        skin: "msg",
                        content: "<div class='msg errorMsg'><div class='msg-icon'></div><div class='msg-title'>查询失败</div><div class='msg-txt'>" + result.data + "</div></div>",
                        end: function () {
                            $("#tongzhiList").append("数据错误");
                        }
                    });
                }
            }
        });
    }

    $("#tongzhiList").on("click", "i", function () {
        var id = $(this).attr("id");
        var clas = $(this).attr("class");
        var isGood;
        if (clas.indexOf("fa-thumbs-up") == -1) {
            isGood = "0";
        } else {
            isGood = "1";
        }
        $.ajax({
            type: "post",
            url: projectName + '/esaleNews/sayGood.do',
            data: {
                id: id,
                isGood: isGood
            },
            success: function (result) {
                if (result.success == 1) {
                    currentPage = 1;
                    $("#tongzhiList").empty();
                    addNews(org);
                } else {
                    layui.layer.open({
                        type: 1,
                        title: false, //不显示标题
                        closeBtn: 0,
                        shadeClose: true,
                        skin: "msg",
                        content: "<div class='msg errorMsg'><div class='msg-icon'></div><div class='msg-title'>取消失败</div><div class='msg-txt'>数据错误</div></div>",
                        end: function () {
                        }
                    });
                }
            }
        });
    });

    $("#upNews").click(function () {
        var index = layui.layer.open({
            title: "发动态",
            type: 2,
            offset: '100px',
            area: ['70%', '600px'],
            content: "../../page/workBench/esaleNewsAdd.html",
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
            },
            end: function () {
                currentPage = 1;
                $("#tongzhiList").empty();
                addNews(org);
            }
        });
        window.sessionStorage.setItem("index", index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize", function () {
            layui.layer.full(window.sessionStorage.getItem("index"));
        })
    });

    $("#loadMany").click(function () {
      currentPage++;
        addNews(org);
    });

    //统计动态
    var percent = function () {
        $(".totalClan").empty();
        $.ajax({
            type: "post",
            url: projectName + '/esaleNews/percent.do',
            success: function (result) {
                if (result.success == 1) {
                    var percent = result.data;
                    var percentStr = "";
                    var n = 0;
                    for (var key in percent) {
                        if (n % 3 == 0) {
                            percentStr += '<div class="n1">';
                        }
                        percentStr += '<span><p>' + key + '</p><p>' + percent[key] + '</p></span>';
                        if ((n + 1) % 3 == 0 || (n + 1) == percent.length) {
                            percentStr += '</div>';
                        }
                        n++;
                    }
                    $(".totalClan").append(percentStr);
                } else {
                    layer.open({
                        type: 1,
                        title: false, //不显示标题
                        closeBtn: 0,
                        shadeClose: true,
                        skin: "msg",
                        content: "<div class='msg errorMsg'><div class='msg-icon'></div><div class='msg-title'>查询失败</div><div class='msg-txt'>" + result.data + "</div></div>",
                        end: function () {
                        }
                    });
                }
            }
        });
    }

    //活动资料藏品统计
    var globalStatistics = function () {
        $.ajax({
            type: "post",
            url: projectName + '/esaleWorkBenchData/globalStatistics.do',
            success: function (result) {
                if (result.success == 1) {
                    var statistics = result.data;
                    $("#activityCount").text(statistics["activityCount"]);
                    $("#collectionCount").text(statistics["collectionCount"]);
                    $("#dataCount").text(statistics["dataCount"]);
                } else {
                    layer.open({
                        type: 1,
                        title: false, //不显示标题
                        closeBtn: 0,
                        shadeClose: true,
                        skin: "msg",
                        content: "<div class='msg errorMsg'><div class='msg-icon'></div><div class='msg-title'>查询失败</div><div class='msg-txt'>" + result.data + "</div></div>",
                        end: function () {
                            $("#tongzhiList").append("数据错误");
                        }
                    });
                }
            }
        });
    }

    //事项
    var getTasks = function () {
        $("#notDo").empty();
        $("#already").empty();
        $("#already1").empty();
        $.ajax({
            type: "post",
            url: projectName + '/esaleWorkBenchData/getTasks.do',
            success: function (result) {
                if (result.success == 1) {
                    var task = result.data;
                    var notDo = task.notDo;
                    var notDoStr = "";
                    var already = task.already;
                    // alert(already);
                    // alert(already.length);
                    // alert(JSON.stringify(already[0]));
                    // alert(already[0].taskname);
                    var alreadyStr = "";
                    var end = task.end;
                    var endStr = "";
                    for (var i = 0; i < notDo.length; i++) {
                        notDoStr += '<tr><td>' + notDo[i].actionType + '</td><td>' + notDo[i].actionName + '</td><td>' + notDo[i].actionTime.substr(0, 19) + '</td><td><a href="javascript:void(0);" class="checkDetail">查看</a></td> </tr>';
                    }
                    $("#notDo").append(notDoStr);
                    for (var j = 0; j < already.length; j++) {
                        alreadyStr += '<tr><td>' + already[j].actionType + '</td><td>' + already[j].actionName + '</td><td>' + already[j].actionTime.substr(0, 19) + '</td><td><a href="javascript:void(0);" class="checkDetail">查看</a></td> </tr>';
                    }
                    $("#already").append(alreadyStr);
                    for (var k = 0; k < end.length; k++) {
                        endStr += '<tr><td>' + end[k].actionType + '</td><td>' + end[k].actionName + '</td><td>' + end[k].actionTime.substr(0, 19) + '</td><td><a href="javascript:void(0);" class="checkDetail">查看</a></td> </tr>';
                    }
                    $("#end").append(endStr);
                } else {
                    layer.open({
                        type: 1,
                        title: false, //不显示标题
                        closeBtn: 0,
                        shadeClose: true,
                        skin: "msg",
                        content: "<div class='msg errorMsg'><div class='msg-icon'></div><div class='msg-title'>查询失败</div><div class='msg-txt'>" + result.data + "</div></div>",
                        end: function () {
                        }
                    });
                }
            }
        });
    }

    //快捷菜单
    var getMenus = function () {
        $(".layui-nav").empty();
        $.ajax({
            type: "post",
            url: projectName + '/esaleWorkBenchData/getMenu.do',
            success: function (result) {
                if (result.success == 1) {
                    var menu = result.data;
                    // alert(JSON.stringify(menu));
                    var menuStr = "";
                    for (var i = 0; i < menu.length; i++) {
                        menuStr += '<li class="layui-nav-item"><a class="cy-page" href="javascript:;" data-url="' + menu[i].href + '" data-pageType="' + menu[i].pageType + '"><img src="' + menu[i].quickIcon + '" alt="没有图片"><span>' + menu[i].title + '</span></a></li>';
                    }
                    $(".layui-nav").append(menuStr);
                    window.localStorage.setItem("menus", JSON.stringify(menu));
                } else {
                    layer.open({
                        type: 1,
                        title: false, //不显示标题
                        closeBtn: 0,
                        shadeClose: true,
                        skin: "msg",
                        content: "<div class='msg errorMsg'><div class='msg-icon'></div><div class='msg-title'>查询失败</div><div class='msg-txt'>" + result.data + "</div></div>",
                        end: function () {
                        }
                    });
                }
            }
        });
    }

    $("#writeLog").click(function () {
        var url = JSON.stringify(addLogUrl);
        window.localStorage.setItem("goalTab", url);
        window.open(digitalAssetsUrl);
    });

    $(".layui-nav").on("click", "a", function () {
        var menu = $(this);
        var url = JSON.stringify(menu.attr("data-url"));
        var pageType = menu.attr("data-pageType");
        // alert(url);
        window.localStorage.setItem("goalTab", url);
        // window.location.href = projectName+"/index.do?pageType="+pageType;
        window.open(projectName + "/index.do?pageType=" + pageType);
    });

    $("#setShortcutEntrance").click(function () {
        var index = layui.layer.open({
            title: '设置',
            type: 2,
            offset: '100px',
            area: ['40%', '400px'],
            content: "../../page/workBench/userMenu.html",
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
            },
            end: function () {
                getMenus();
            }
        });
        window.sessionStorage.setItem("index", index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        // $(window).on("resize",function(){
        //     layui.layer.full(window.sessionStorage.getItem("index"));
        // });
    });

    addNews(org);
    percent();
    globalStatistics();
    // getTasks();
    getMenus();
});

//时间戳转时间格式
function timestampToDateTime(obj) {
    var date = new Date(obj);
    var y = 1900 + date.getYear();
    var m = "0" + (date.getMonth() + 1);
    var d = "0" + date.getDate();
    var h = "0" + date.getHours() + ':';
    var mi = "0" + date.getMinutes();
    var s = "0" + date.getSeconds();
    return y + "-" + m.substring(m.length - 2, m.length) + "-" + d.substring(d.length - 2, d.length) + " " + h.substring(h.length - 3, h.length) + mi.substring(mi.length - 2, mi.length) + ':' + s.substring(s.length - 2, s.length);
};

function loadTask() {
    $.ajax({
        type: "post",
        async:false,
        url: projectName + '/esaleWorkBenchData/getTasks.do',
        success: function (result) {
            if (result.success == 1) {
                var data = result.data;
                undoTask = data.undoTask;
                doneTask = data.doneTask;
                finishTask = data.finishTask;
            } else {
                errorMsg("查询失败");
            }
        }
    });
}

function showTaskList(){
    //加载待办
    layui.use('table', function(){
        var table = layui.table;
        var cols = [
            {field:'actionType', title:'类型'}
            ,{field:'actionName', title:'事项名称'}
            ,{field:'actionTime', title:'创建时间',templet: function(res){
                    return formatDate(res.actionTime);
                }}
            ,{fixed: 'right', title:'操作', width:200,templet: function(res){
                    return '</span><a href="#" data-id="'+res.processInstId+'" data-type="'+res.taskType+'" data-url="'+res.url+'" class="tongzhiRight showTask"><span>查看</span>';
                }}
        ]
        // var noticeCols = [
        //     {field:'actionType', title:'类型'}
        //     ,{field:'actionName', title:'事项名称'}
        //     ,{field:'actionTime', title:'创建时间',templet: function(res){
        //             return formatDate(res.actionTime);
        //         }}
        //     ,{fixed: 'right', title:'操作', width:200,templet: function(res){
        //             return '</span><a href="#" data-id="'+res.processInstId+'" class="tongzhiRight showNotice"><span>查看</span>';
        //         }}
        // ]
        table.render({
            elem: '#undoTable'
            ,data:undoTask
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,cols: [cols]
        });
        table.render({
            elem: '#doneTable'
            ,data:doneTask
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,cols: [cols]
        });
        table.render({
            elem: '#finishTable'
            ,data:finishTask
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,cols: [cols]
        });

        $(".showTask").click(function () {
            var type = $(this).attr("data-type");
            if (type == "1"){
                var url = $(this).attr("data-url");
                window.open(url);
            } else {
                showTask($(this).attr("data-id"));
            }

        })
        // $(".showNotice").click(function () {
        //     showNotice($(this).attr("data-id"));
        // })
        // $("#setShortcutEntrance").click(function () {
        //     setShortcutEntrance();
        // })
    });
}

function showTask(processInstId) {
    var json = {"processInstId":processInstId};
    $.ajax({
        data:json,
        type:"post",
        async:false,
        url:projectName+"/PostVideo/getWfAction.do",
        success:function(result) {
            if (result.success == 1) {
                var data = result.data;
                goPage(data.partyType,data.partyId,data.xid,data.status);
            } else {
                errorMsg(result.error.message);
            }
        },
        error:function(result) {
            errorMsg("系统异常");
        }
    });
}
function goPage(type,id,processInstId,status) {
    debugger
    localStorage.videoId = id;
    //上传审批
    if (type == '1'){
        localStorage.pageType="detail";
        localStorage.processInstId = processInstId;
        var index = layui.layer.open({
            title : "查看",
            type : 2,
            area: ['80%', '700px'],
            content : "../../page/video/videoShow.html",
            success : function(layero, index){
            },
            end :function() {
                // tableIns.reload();
            }
        })
        layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })
    }
    //查询审批
    else if(type == '2'){
        localStorage.pageType="detail";
        localStorage.processInstId = processInstId;
        var index = layui.layer.open({
            title : "查看",
            type : 2,
            area: ['80%', '700px'],
            content : "../../page/video/videoQueryShow.html",
            success : function(layero, index){
            },
            end :function() {
                // tableIns.reload();
            }
        })
        layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })

}else{
        errorMsg("未找到对应的任务信息!");
    }
}

//格式化时间(时间戳转换为 日期格式   年月日时分秒)
function formatDate(v) {
    if(/^(-)?\d{1,10}$/.test(v)) {
        v = v * 1000;
    } else if(/^(-)?\d{1,13}$/.test(v)) {
        v = v * 1;
    }
    var dateObj = new Date(v);
    var month = dateObj.getMonth() + 1;
    var day = dateObj.getDate();
    var hours = dateObj.getHours();
    var minutes = dateObj.getMinutes();
    var seconds = dateObj.getSeconds();
    if(month < 10) {
        month = "0" + month;
    }
    if(day < 10) {
        day = "0" + day;
    }
    if(hours < 10) {
        hours = "0" + hours;
    }
    if(minutes < 10) {
        minutes = "0" + minutes;
    }
    if(seconds < 10) {
        seconds = "0" + seconds;
    }
    var UnixTimeToDate = dateObj.getFullYear() + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;
    return UnixTimeToDate;
}