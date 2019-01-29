//交叉表数据
var tableData = null;
//饼图数据
var pieData = null;
var viewPie = null;
var viewHistogram = null;
var collectPie = null;
var collectHistogram = null;
var main={
    init:function () {
        var startTime = getDay(-7);
        var endTime = formatSimpleDate(new Date());
        var dateParagraph = formatDateParagraph(startTime,endTime);
        $("#time2").val(dateParagraph);
        $("#time3").val(dateParagraph);
        getReport1();
        getReport2(dateParagraph);
        getReport3(dateParagraph);
        this.initTable();
        this.tabBind()
    },
    initTable:function(){
        layui.use('laydate', function(){
            var laydate = layui.laydate;

            //执行一个laydate实例
            laydate.render({
                elem: '#time2' //指定元素
                ,range: true
               ,done:function (value, date, endDate) {
                    getReport2(value);
                }
            });
            laydate.render({
                elem: '#time3' //指定元素
                ,range: true
                ,done:function (value, date, endDate) {
                    getReport3(value);
                }
            });
        });
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
        $("#last7_3").click(function () {

            var startTime = getDay(-7);
            var endTime = formatSimpleDate(new Date());
            var dateParagraph = formatDateParagraph(startTime,endTime);
            $("#time3").val(dateParagraph);
            getReport3(dateParagraph);
        })
        $("#last30_3").click(function () {

            var startTime = getDay(-30);
            var endTime = formatSimpleDate(new Date());
            var dateParagraph = formatDateParagraph(startTime,endTime);
            $("#time3").val(dateParagraph);
            getReport3(dateParagraph);
        })
    },
    initPie:function(){
        var myChart = echarts.init(document.getElementById('mainPie'),"walden");
        var legend = [];
        for (var i = 0; i < pieData.length; i++) {
            legend.push(pieData.name);
        }
        // 指定图表的配置项和数据

         var option = {

            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'horizontal',
                y:'bottom',
                data: legend
            },
             grid:{
                 bottom:'20%',//距离下边距
             },
            series : [
                {
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:pieData,
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
    initViewPie: function () {
        var myChart = echarts.init(document.getElementById('viewTypePie'),"walden");
        var legend = [];
        for (var i = 0; i < viewPie.length; i++) {
            legend.push(viewPie[i].name);
        }
        // 指定图表的配置项和数据

        var option = {
            title: {
                text: '浏览类别分布',
                x:'center',
                y:'top',
                textAlign:'left'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'horizontal',
                y:'bottom',
                data: legend
            },
            grid:{
                bottom:'20%',//距离下边距
            },
            series : [
                {
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:viewPie,
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
        main.initViewHistogram()
    },
    initViewHistogram: function () {
        var myChart = echarts.init(document.getElementById('viewTopBye'),"walden");
        var legend = [];
        var data = [];
        for (var i = 0; i < viewHistogram.length; i++) {
            legend.push(viewHistogram[i].name);
            data.push(viewHistogram[i].value);
        }
        option = {
            color: ['#3398DB'],
            title: {
                text: 'top10藏品',
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
                    name:'浏览',
                    type:'bar',
                    barWidth: '60%',
                    data:data
                }
            ]
        };
        myChart.setOption(option);
        myChart.resize();
    },
    initCollectPie: function () {
        var myChart = echarts.init(document.getElementById('collectTypePie'),"walden");
        var legend = [];
        for (var i = 0; i < collectPie.length; i++) {
            legend.push(collectPie[i].name);
        }
        // 指定图表的配置项和数据

        var option = {
            title: {
                text: '藏品收藏统计',
                x:'center',
                y:'top',
                textAlign:'left'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'horizontal',
                y:'bottom',
                data: legend
            },
            grid:{
                bottom:'20%',//距离下边距
            },
            series : [
                {
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:collectPie,
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
        main.initCollectHistogram()
    },
    initCollectHistogram: function () {
        var myChart = echarts.init(document.getElementById('collectTopBye'),"walden");
        var legend = [];
        var data = [];
        for (var i = 0; i < collectHistogram.length; i++) {
            legend.push(collectHistogram[i].name);
            data.push(collectHistogram[i].value);
        }
        option = {
            color: ['#3398DB'],
            title: {
                text: 'top10藏品',
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
                    name:'收藏',
                    type:'bar',
                    barWidth: '60%',
                    data:data
                }
            ]
        };
        myChart.setOption(option);
        myChart.resize();
    },
    tabBind:function () {
        //导出函数
        $("#report1").on({
            'click':function () {
                location.href = property.getProjectPath()+"esaleStatistics/exportLiteratureReport2.do";
                return false;
            }
        });
        $("#report2").on({
            'click':function () {
                var data = $("#time2").val();
                var startTime = data.split(" - ")[0];
                var endTime = data.split(" - ")[1];
                location.href = property.getProjectPath()+"esaleStatistics/exportLiteratureReport3.do?startTime="+startTime+
                    "&endTime="+endTime;
                return false;
            }
        });
        $("#report3").on({
            'click':function () {
                var data = $("#time3").val();
                var startTime = data.split(" - ")[0];
                var endTime = data.split(" - ")[1];
                location.href = property.getProjectPath()+"esaleStatistics/exportLiteratureReport4.do?startTime="+startTime+
                    "&endTime="+endTime;
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
        $(".searchBtn2").on({
            'click':function () {
                var index=$(this).index();
                if($(this).hasClass('active'))return false
                if(index==1){
                    $(".searchBtn2").removeClass("active");
                    $(".searchBtn2").eq(0).addClass("active");
                }else{
                    $(".searchBtn2").removeClass("active");
                    $(".searchBtn2").eq(1).addClass("active");
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
function getReport1() {
    $.ajax({
        type:"post",
        async:false,
        url:property.getProjectPath()+"esaleStatistics/colletStatistics.do",
        success:function(result) {
            if (result.success == 1) {
                var data = result.data;
                tableData = data.table;
                pieData = data.pie;
                loadChart1();
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
        url: property.getProjectPath()+"esaleStatistics/collectViewStatistics.do",
        success: function(result) {
            if (result.success == 1) {
                var data = result.data;
                viewPie = data.pie;
                viewHistogram = data.histogram;
                var temp = [];
                for (var i = viewHistogram.length - 1; i > -1; i--) {
                    temp.push(viewHistogram[i]);
                }
                viewHistogram = temp;
                main.initViewPie();
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
        url: property.getProjectPath()+"esaleStatistics/colectionCollectStatistics.do",
        success: function(result) {
            if (result.success == 1) {
                var data = result.data;
                collectPie = data.pie;
                collectHistogram = data.histogram;
                var temp = [];
                for (var i = collectHistogram.length - 1; i > -1; i--) {
                    temp.push(collectHistogram[i]);
                }
                collectHistogram = temp;
                main.initCollectPie();
            } else {
                errorMsg(result.error.message);
            }
        },
        error: function(result) {
            errorMsg("系统异常");
        }
    });
}

function loadChart1() {
    layui.use('table', function(){
        var cols = [];
        cols.push({
            field: 'title',
            title: ''
        })
        var head = tableData.head;
        for (var key in head) {
            cols.push({
                field: key,
                title: head[key]
            })
        }
        var table = layui.table;
        table.render({
            elem: '#test1'
            ,data:tableData.column
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,cols: [cols]
        });
    });
    main.initPie();
}
