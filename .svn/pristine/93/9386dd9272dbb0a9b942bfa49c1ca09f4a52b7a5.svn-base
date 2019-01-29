//交叉表数据
var tableData = null;
//饼图数据
var pieData = null;
var newTable = null;
var newPie= null;
var newLine= null;
var useHistogram = null;
var useHistogram2 = null;
var main={
    init:function () {
        var startTime = getDay(-30);
        var endTime = formatSimpleDate(new Date());
        var dateParagraph = formatDateParagraph(startTime,endTime);
        $("#time1").val(dateParagraph);
        $("#time2").val(dateParagraph);
        $("#time3").val(dateParagraph);
        getReport1(dateParagraph);
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
                elem: '#time1' //指定元素
                ,range: true
                ,done:function (value, date, endDate) {
                    getReport1(value);
                }
            });
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
        $("#last30_1").click(function () {

            var startTime = getDay(-30);
            var endTime = formatSimpleDate(new Date());
            var dateParagraph = formatDateParagraph(startTime,endTime);
            $("#time1").val(dateParagraph);
            getReport1(dateParagraph);
        })
        $("#last90_1").click(function () {

            var startTime = getDay(-90);
            var endTime = formatSimpleDate(new Date());
            var dateParagraph = formatDateParagraph(startTime,endTime);
            $("#time1").val(dateParagraph);
            getReport1(dateParagraph);
        })
        $("#last90_2").click(function () {

            var startTime = getDay(-90);
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
        $("#last90_3").click(function () {

            var startTime = getDay(-90);
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
        var myChart = echarts.init(document.getElementById('viewTypePie'),"walden");
        var legend = [];
        for (var i = 0; i < newPie.length; i++) {
            legend.push(newPie[i].name);
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
                    data:newPie,
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
        main.initLine();
    },
    initViewPie: function () {
        var myChart = echarts.init(document.getElementById('viewTypePie'),"walden");
        var legend = [];
        for (var i = 0; i < viewPie.length; i++) {
            legend.push(viewPie[i].name);
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
        var myChart = echarts.init(document.getElementById('collectTypePie'),"walden");
        option = {
            color: ['#3398DB'],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: useHistogram.legend
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
                    data : useHistogram.xAxis,
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
            series : useHistogram.data
        };
        myChart.setOption(option);
        myChart.resize();
        main.initViewHistogram2();
    },
    initViewHistogram2: function () {
        var myChart = echarts.init(document.getElementById('collectTopBye'),"walden");
        option = {
            color: ['#3398DB'],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: useHistogram2.legend
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
                    data : useHistogram2.yAxis,
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
            series : useHistogram2.data
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
    initLine:function(){
        var myChart = echarts.init(document.getElementById('viewTopBye'),"walden");
        var option = {

            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:newLine.legend,
                y:'bottom'
            },
            grid:{
                bottom:'20%',//距离下边距
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: newLine.xAxis
            },
            yAxis: {
                type: 'value'
            },
            series: newLine.data
        };
        myChart.setOption(option);
        myChart.resize();
    },
    tabBind:function () {
        //导出函数
        $("#report2").on({
            'click':function () {
                var data = $("#time1").val();
                var startTime = data.split(" - ")[0];
                var endTime = data.split(" - ")[1];
                location.href = property.getProjectPath()+"postVideoStatistics/exportLiteratureReport6.do?startTime="+startTime+
                    "&endTime="+endTime;
                return false;
            }
        });
        $("#report3").on({
            'click':function () {
                var data = $("#time1").val();
                var startTime = data.split(" - ")[0];
                var endTime = data.split(" - ")[1];
                location.href = property.getProjectPath()+"postVideoStatistics/exportLiteratureReport7.do?startTime="+startTime+
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
function getReport1(data) {
    var startTime = data.split(" - ")[0];
    var endTime = data.split(" - ")[1];
    var json = {"startTime":startTime,"endTime":endTime};
    $.ajax({
        data: json,
        type:"post",
        async:false,
        url:property.getProjectPath()+"postVideoStatistics/videoSummaryStatistics.do",
        success:function(result) {
            if (result.success == 1) {
                var data = result.data;
                $('#newVideoCount').html(data.newVideoCount);
                $('#videoTotle').html(data.videoTotle);
                $('#undisclosedCount').html(data.undisclosedCount);
                $('#canQueryCount').html(data.canQueryCount);
                $('#canDownCount').html(data.canDownCount);
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
        url: property.getProjectPath()+"postVideoStatistics/newVideoStatistics.do",
        success: function(result) {
            if (result.success == 1) {
                var data = result.data;
                newPie = data.pie;
                newTable = data.table;
                newLine = data.line;
                loadChart2();
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
        url: property.getProjectPath()+"postVideoStatistics/videoUseStatistics.do",
        success: function(result) {
            if (result.success == 1) {
                var data = result.data;
                useHistogram = data.histogram;
                useHistogram2 = data.histogram2;
                main.initViewHistogram();
            } else {
                errorMsg(result.error.message);
            }
        },
        error: function(result) {
            errorMsg("系统异常");
        }
    });
}

function loadChart2() {
    layui.use('table', function(){
        var table = layui.table;
        table.render({
            elem: '#test1'
            ,data:newTable
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,cols: [[
                {
                    field: 'title',
                    title: ''
                },{
                    field: 'new',
                    title: '新增'
                },{
                    field: 'count',
                    title: '累计'
                },{
                    field: 'undisclosed',
                    title: '未公开'
                },{
                    field: 'query',
                    title: '公开仅查询'
                },{
                    field: 'download',
                    title: '公开可下载'
                }]]
        });
    });
    main.initPie();
}
