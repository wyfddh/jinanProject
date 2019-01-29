//交叉表数据
var tableData = null;
//饼图数据
var pieData = null;
var lineData = null;
var viewPie = null;
var viewHistogram = null;
var collectPie = null;
var collectHistogram = null;
var loopData = null;
var subHistogram = null;
var nosubHistogram = null;
var dateHistogram = null;
var personHistogram = null;
var deptHistogram = null;
var choseFunc = null;
var main={
    init:function () {
        var startTime = new Date(new Date().getFullYear(), new Date().getMonth(), 1);
        var endTime = new Date();
        var dateParagraph = formatDateParagraph(startTime,endTime);
        $("#time1").val(dateParagraph);
        getReport1(dateParagraph);
        getReport2(dateParagraph);
        this.initTable();
        this.tabBind()
    },
    initTable:function(){
        layui.use('laydate', function(){
            var laydate = layui.laydate;

            //执行一个laydate实例
            laydate.render({
                elem: '#time1' //指定元素
                ,range: true
               ,done:function (value, date, endDate) {
                    getReport1(value);
                    choseFunc(value);
                }
            });
        });
        $("#last30_1").click(function () {
            var startTime = new Date(new Date().getFullYear(), new Date().getMonth(), 1);
            var endTime = new Date();
            var dateParagraph = formatDateParagraph(startTime,endTime);
            $("#time1").val(dateParagraph);
            getReport1(dateParagraph);
            choseFunc(dateParagraph);
        });
        $("#last90_1").click(function () {
            var startTime = lastMonthFirstDay();
            var endTime = lastMonthLastDay();
            var dateParagraph = formatDateParagraph(startTime,endTime);
            $("#time1").val(dateParagraph);
            getReport1(dateParagraph);
            choseFunc(dateParagraph);
        });
        $('#byDate').click(function () {
            getReport2($('#time1').val());
        });
        $('#byPerson').click(function () {
            getReport3($('#time1').val());
        })
        $('#byDept').click(function () {
            getReport4($('#time1').val());
        })
    },
    initDateHistogram:function(){
        var myChart = echarts.init(document.getElementById('mainLine'),"walden");

        // 指定图表的配置项和数据

        option = {
            color: ['#3398DB'],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: dateHistogram.legend
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : dateHistogram.xAxis,
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : dateHistogram.series
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        myChart.resize();
    },
    initPersonHistogram: function () {
        var myChart = echarts.init(document.getElementById('mainLine'),"walden");
        // 指定图表的配置项和数据

        option = {
            color: ['#3398DB'],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: dateHistogram.legend
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : personHistogram.xAxis,
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : personHistogram.series
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        myChart.resize();
    },
    nosubHistogram: function () {
        var myChart = echarts.init(document.getElementById('nosubHistogram'),"walden");
        var legend = [];
        var data = [];
        for (var i = 0; i < nosubHistogram.length; i++) {
            legend.push(nosubHistogram[i].name);
            data.push(nosubHistogram[i].value);
        }
        option = {
            color: ['#52D1D2'],
            title: {
                text: '未提交数top5',
                y:'top',
                textAlign:'left'
            },
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            yAxis : [
                {
                    type : 'category',
                    data : legend,
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            xAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'未提交',
                    type:'bar',
                    barWidth: '60%',
                    data:data
                }
            ]
        };
        myChart.setOption(option);
        myChart.resize();
    },
    subHistogram: function () {
        var myChart = echarts.init(document.getElementById('subHistogram'),"walden");
        var legend = [];
        var data = [];
        for (var i = 0; i < subHistogram.length; i++) {
            legend.push(subHistogram[i].name);
            data.push(subHistogram[i].value);
        }
        option = {
            color: ['#3398DB'],
            title: {
                text: '提交数top5',
                y:'top',
                textAlign:'left'
            },
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            yAxis : [
                {
                    type : 'category',
                    data : legend,
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            xAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'正常',
                    type:'bar',
                    barWidth: '60%',
                    data:data
                }
            ]
        };
        myChart.setOption(option);
        myChart.resize();
        main.nosubHistogram();
    },
    initDeptHistogram:function(){
        var myChart = echarts.init(document.getElementById('mainLine'),"walden");
        // 指定图表的配置项和数据

        option = {
            color: ['#3398DB'],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: dateHistogram.legend
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : deptHistogram.xAxis,
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : deptHistogram.series
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        myChart.resize();
    },
    initLoop: function() {
        var myChart = echarts.init(document.getElementById('loop'),"walden");
        var legend = [];
        for (var i = 0; i < loopData.length; i++) {
            legend.push(loopData[i].name);
        }
        option = {
            title: {
                text: '提交情况',
                x:'left',
                y:'top',
                textAlign:'left'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            legend: {
                data:legend
            },
            series: [
                {
                    name:'提交情况',
                    type:'pie',
                    radius: ['50%', '70%'],
                    avoidLabelOverlap: false,
                    data:loopData
                }
            ]
        };
        myChart.setOption(option);
        myChart.resize();
        main.subHistogram();
    },
    tabBind:function () {
        //导出函数
        $("#report2").on({
            'click':function () {
                var data = $("#time2").val();
                var startTime = data.split(" - ")[0];
                var endTime = data.split(" - ")[1];
                location.href = property.getProjectPath()+"esaleStatistics/exportLiteratureReport5.do?startTime="+startTime+
                    "&endTime="+endTime;
                return false;
            }
        })

        //时间切换
        $(".searchBtn").on({
            'click':function () {
                var index=$(this).index();
                if($(this).hasClass('active'))return false
                $(".searchBtn").removeClass("active");
                $(this).addClass("active");
                return false
            }
        })
        $(".searchBtn1").on({
            'click':function () {
                var index=$(this).index();
                if($(this).hasClass('active'))return false
                $(".searchBtn1").removeClass("active");
                $(this).addClass("active");
                return false
            }
        })

    }
}
main.init();
//日历切换
function cDayFunc() {
   main.initTable()
}

//加载图表1
function getReport1(data) {
    var startTime = data.split(" - ")[0];
    var endTime = data.split(" - ")[1];
    var json = {"startTime":startTime,"endTime":endTime};
    $.ajax({
        data: json,
        type:"post",
        async:false,
        url:property.getProjectPath()+"esaleStatistics/diaryStatistics.do",
        success:function(result) {
            if (result.success == 1) {
                var data = result.data;
                loopData = data.loopData;
                subHistogram = data.subHistogram;
                nosubHistogram = data.nosubHistogram;
                main.initLoop();
            } else {
                errorMsg(result.error.message);
            }
        },
        error:function(result) {
            errorMsg("系统异常");
        }
    });
}
function getReport2(data) {
    var startTime = data.split(" - ")[0];
    var endTime = data.split(" - ")[1];
    var json = {"startTime":startTime,"endTime":endTime};
    $.ajax({
        data: json,
        type: "post",
        async: false,
        url: property.getProjectPath()+"esaleStatistics/diaryStatisticsGroupByDate.do",
        success: function(result) {
            if (result.success == 1) {
                choseFunc = getReport2;
                dateHistogram = result.data;
                main.initDateHistogram();
            } else {
                errorMsg(result.error.message);
            }
        },
        error: function(result) {
            errorMsg("系统异常");
        }
    });
}

function getReport3(data) {
    var startTime = data.split(" - ")[0];
    var endTime = data.split(" - ")[1];
    var json = {"startTime":startTime,"endTime":endTime};
    $.ajax({
        data: json,
        type: "post",
        async: false,
        url: property.getProjectPath()+"esaleStatistics/diaryStatisticsGroupByPerson.do",
        success: function(result) {
            if (result.success == 1) {
                choseFunc = getReport3;
                personHistogram = result.data;
                main.initPersonHistogram();
            } else {
                errorMsg(result.error.message);
            }
        },
        error: function(result) {
            errorMsg("系统异常");
        }
    });
}

function getReport4(data) {
    var startTime = data.split(" - ")[0];
    var endTime = data.split(" - ")[1];
    var json = {"startTime":startTime,"endTime":endTime};
    $.ajax({
        data: json,
        type: "post",
        async: false,
        url: property.getProjectPath()+"esaleStatistics/diaryStatisticsGroupByDept.do",
        success: function(result) {
            if (result.success == 1) {
                choseFunc = getReport4;
                deptHistogram = result.data;
                main.initDeptHistogram();
            } else {
                errorMsg(result.error.message);
            }
        },
        error: function(result) {
            errorMsg("系统异常");
        }
    });
}

function lastMonthFirstDay() {
    return new Date(new Date().getFullYear(), new Date().getMonth()-1, 1);
}

function lastMonthLastDay() {
    var date = new Date();
    var day = new Date(date.getFullYear(), date.getMonth(), 0).getDate();
    return new Date(new Date().getFullYear(), new Date().getMonth()-1, day);
}