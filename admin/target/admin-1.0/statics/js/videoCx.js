//交叉表数据
var tableData = null;
//饼图数据
var pieData = null;
//折线图
var lineData = null;
//饼图
var barData = null;
//top10
var top10Data = null;
var main={
    init:function () {
        var startTime = getDay(-7);
        var endTime = formatSimpleDate(new Date());
        var dateParagraph = formatDateParagraph(startTime,endTime);
        $("#time1").val(dateParagraph);
        $("#time2").val(dateParagraph);
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
                }
            });
            //执行一个laydate实例
            laydate.render({
                elem: '#time2' //指定元素
                ,range: true
                ,done:function (value, date, endDate) {
                    getReport2(value);
                }
            });
        });
        $("#last7_1").click(function () {

            var startTime = getDay(-7);
            var endTime = formatSimpleDate(new Date());
            var dateParagraph = formatDateParagraph(startTime,endTime);
            $("#time1").val(dateParagraph);
            getReport1(dateParagraph);
        })
        $("#last30_1").click(function () {

            var startTime = getDay(-30);
            var endTime = formatSimpleDate(new Date());
            var dateParagraph = formatDateParagraph(startTime,endTime);
            $("#time1").val(dateParagraph);
            getReport1(dateParagraph);
        })
        $("#last7_2").click(function () {
            var startTime = getDay(-7);
            var endTime = formatSimpleDate(new Date());
            var dateParagraph = formatDateParagraph(startTime,endTime);
            $("#time2").val(dateParagraph);
            getReport2(dateParagraph);
        })
        $("#last30_2").click(function () {

            var startTime = getDay(-30);
            var endTime = formatSimpleDate(new Date());
            var dateParagraph = formatDateParagraph(startTime,endTime);
            $("#time2").val(dateParagraph);
            getReport2(dateParagraph);
        })

    },
    initPie:function(){
        var myChart = echarts.init(document.getElementById('mainPie'),"walden");

        // 指定图表的配置项和数据

         var option = {

            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'horizontal',
                y:'bottom',
                data: pieData.legend
            },
             grid:{
                 bottom:'20%',//距离下边距
             },
            series : [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:pieData.data,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        myChart.resize();
    },
    initLine:function(){
        var myChart = echarts.init(document.getElementById('mainLine'),"walden");
        var option = {

            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:lineData.legend,
                y:'bottom'
            },
                grid:{
                    bottom:'20%',//距离下边距
                },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: lineData.xAxis
            },
            yAxis: {
                type: 'value'
            },
            series: lineData.data
        };
        myChart.setOption(option);
        myChart.resize();
    },
    initBye:function(){
        var myChart = echarts.init(document.getElementById('mainBye'),"walden");
        var option = {
            color: ['#003366', '#006699', '#4cabce', '#e5323e'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            legend: {
                data:barData.legend,
                y:'bottom'
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    axisTick: {show: false},
                    data: barData.xAxis
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: barData.data
        };
        myChart.setOption(option);
        myChart.resize();
    },
    initTop10:function(){
    var myChart = echarts.init(document.getElementById('top10'),"walden");
    var option = {
        color: ['#003366', '#006699', '#4cabce', '#e5323e'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data:top10Data.legend,
            y:'bottom'
        },
        grid:{
            left:'20%',//距离下边距
        },
        calculable: true,
        yAxis: [
            {
                inverse: true,
                data: top10Data.xAxis
            }
        ],
        xAxis:{

        },
        series: top10Data.data
    };
    myChart.setOption(option);
    myChart.resize();
    },
    tabBind:function () {
        //导出函数
        $("#report1").on({
            'click':function () {
                var data = $("#time1").val();
                var startTime = data.split(" - ")[0];
                var endTime = data.split(" - ")[1];
                var type = "2";
                location.href = property.getProjectPath()+"PostVideo/exportVideoCjReport1.do?startTime="+startTime+
                    "&endTime="+endTime+"&type="+type;
                return false;
            }
        })
        //导出函数
        $("#report2").on({
            'click':function () {
                var data = $("#time2").val();
                var startTime = data.split(" - ")[0];
                var endTime = data.split(" - ")[1];
                var type = "2";
                location.href = property.getProjectPath()+"PostVideo/exportVideoCjReport2.do?startTime="+startTime+
                    "&endTime="+endTime+"&type="+type;
                return false;
            }
        })
        //时间切换
        $(".searchBtn").on({
            'click':function () {
                var index=$(this).index();
                if($(this).hasClass('active'))return false
                if(index==1){
                    $(".searchBtn").removeClass("active");
                    $(".searchBtn").eq(0).addClass("active");
                }else{
                    $(".searchBtn").removeClass("active");
                    $(".searchBtn").eq(1).addClass("active");
                }

                return false
            }
        })
        $(".searchBtn1").on({
            'click':function () {
                var index=$(this).index();
                if($(this).hasClass('active'))return false
                if(index==1){
                    $(".searchBtn1").removeClass("active");
                    $(".searchBtn1").eq(0).addClass("active");
                }else{
                    $(".searchBtn1").removeClass("active");
                    $(".searchBtn1").eq(1).addClass("active");
                }

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
    var json = {"status":null,"startTime":startTime,"endTime":endTime,type:"2"};
    $.ajax({
        data:json,
        type:"post",
        async:false,
        url:property.getProjectPath()+"PostVideo/getVideoReport1.do",
        success:function(result) {
            if (result.success == 1) {
                var data = result.data;
                tableData = data.table;
                pieData = data.pie;
                lineData = data.line;
                loadChart1();
            } else {
                top.layer.msg(result.error.message);
            }
        },
        error:function(result) {
            top.layer.msg("系统异常");
        }
    });

}
//加载图表2
function getReport2(data) {
    var startTime = data.split(" - ")[0];
    var endTime = data.split(" - ")[1];
    var json = {"status":null,"startTime":startTime,"endTime":endTime,type:"2"};
    $.ajax({
        data:json,
        type:"post",
        async:false,
        url:property.getProjectPath()+"PostVideo/getVideoReport2.do",
        success:function(result) {
            if (result.success == 1) {
                var data = result.data;
                barData = data.bar;
                top10Data = data.top10;
                loadChart2();
            } else {
                top.layer.msg(result.error.message);
            }
        },
        error:function(result) {
            top.layer.msg("系统异常");
        }
    });

}



function loadChart1() {
    layui.use('table', function(){

        var table = layui.table;
        table.render({
            elem: '#test1'
            ,data:tableData
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,cols: [[
                {field:'type', title:'操作类型'}
                ,{field:'zc', title:'展陈'}
                ,{field:'sj', title:'社教'}
                ,{field:'yj', title:'研究'}
                ,{field:'xwfb', title:'新闻发布'}
                ,{field:'qt', title:'其他'}
            ]]
        });
    });
    main.initPie();
    main.initLine();
}
function loadChart2() {
    main.initBye();
    main.initTop10();
}
