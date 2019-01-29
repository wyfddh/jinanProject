/**
 * Created by MyPC on 2018/10/10.
 */

/**
 * 判断ajax请求是否需要登陆
 */
var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
$.ajaxSetup( {
    //设置ajax请求结束后的执行动作
    complete : function(XMLHttpRequest) {
        if (XMLHttpRequest.status == 999) {
            var win = window;
            while (win != win.top){
                win = win.top;
            }
            //将后端重定向的地址取出来,使用win.location.href去实现重定向的要求
            win.location.href= projectName + "/login.html";
        }
    }
});

$('.shouqi').click(function () {
    if($('.top').hasClass("active")){
        $('.top').removeClass("active");
        $('.shouqi span').text("收起筛选");
    }else{
        $('.top').addClass("active");
        $('.shouqi span').text("打开筛选");
    }
})

//格式化时间(时间戳转换为 日期格式   年月日时分秒)
function formatDate(v) {
    if(/^(-)?\d{1,10}$/.test(v)) {
        v = v * 1000;
    } else if(/^(-)?\d{1,13}$/.test(v)) {
        v = v * 1;
    }
    v = v.replace(/-/g,"/");
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
